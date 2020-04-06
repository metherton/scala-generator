package com.martinetherton

trait Generator[+T] {

  self =>

  def generate: T

  def map[S](f : T => S): Generator[S] = new Generator[S] {
    override def generate: S = f(self.generate)
  }

}
