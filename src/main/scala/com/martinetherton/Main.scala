package com.martinetherton

object Main extends App {

  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt
  }

  val booleans = for (x <- integers) yield x > 0

  val s = single(5)
//  println(s.generate)

  val ch = integers.choose(1, 5)
//  println(ch.generate)

  val one = integers.oneOf(5, 10, 15)
//  println(one.generate)


//  println(integers.generate)
//  println(booleans.generate)


  def leafs: Generator[Leaf] = for {
    x <- integers
  } yield Leaf(x)

  def trees: Generator[Tree] = for {
    isLeaf <- booleans
    tree <- if (isLeaf) leafs else inners
  } yield tree

  def inners: Generator[Inner] = for {
    l <- trees
    r <- trees
  } yield Inner(l, r)

  println(trees.generate)

  def test[T](g: Generator[T], numTimes: Int = 100)
             (test: T => Boolean): Unit = {
    for (i <- 0 until numTimes) {
      val value = g.generate
      assert(test(value), "test failed for value" + value)
    }
    println("passed" + numTimes + " tests")
  }

  def single[T](x: T): Generator[T] = new Generator[T] {
    override def generate: T = x
  }


  def emptyLists = single(Nil)

  def nonEmptyLists = for {
    head <- integers
    tail <- lists
  } yield head :: tail

  def lists: Generator[List[Int]] = for {
    isEmpty <- booleans
    list <- if (isEmpty) emptyLists else nonEmptyLists
  } yield list

  def pairs[T, U](t: Generator[T], u: Generator[U])= t flatMap {
    x => u map { y => (x, y)}
  }
  test(pairs(lists, lists)) {
    case (xs, ys) => (xs ++ ys).length > xs.length
  }

}
