package com.martinetherton

trait Generator[+T] {
  def generate: T
}
