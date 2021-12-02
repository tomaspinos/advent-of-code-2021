package day01

import common.Common

object SonarSweep2 {

  def main(args: Array[String]): Unit = {
    val values = Common.readFile("/day01/input.txt").map { value => value.toInt }

    val values2 = values.drop(1)
    val values3 = values.drop(2)
    val values4 = values.drop(3)
    val slidingWindows1 = (values, values2, values3).zipped
    val slidingWindows2 = (values2, values3, values4).zipped
    val increases = slidingWindows1.zip(slidingWindows2)
      .count { case ((a, b, c), (u, v, x)) => a + b + c < u + v + x }

    println(increases)
  }
}
