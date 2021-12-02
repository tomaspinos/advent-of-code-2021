package day01

import common.Common

object SonarSweep1 {

  def main(args: Array[String]): Unit = {
    val values = Common.readFile("/day01/input.txt").map { value => value.toInt }

    val increases = values.zip(values.drop(1))
      .count { case (u, v) => u < v }

    println(increases)
  }
}
