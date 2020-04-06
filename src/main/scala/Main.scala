import com.martinetherton.Generator

object Main extends App {

  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt
  }

  val booleans = new Generator[Boolean] {
    def generate = integers.generate > 0
  }

  val pairs = new Generator[(Int, Int)] {
    def generate = (integers.generate, integers.generate)
  }
  println(integers.generate)
  println(booleans.generate)
}
