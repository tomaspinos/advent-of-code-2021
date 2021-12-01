package day01

object SonarSweep2 {

  def main(args: Array[String]): Unit = {
    val values = readFile().to(LazyList)

    val values2 = values.drop(1)
    val values3 = values.drop(2)
    val values4 = values.drop(3)
    val slidingWindows1 = (values, values2, values3).zipped
    val slidingWindows2 = (values2, values3, values4).zipped
    val increases = slidingWindows1.zip(slidingWindows2)
      .count { case ((a, b, c), (u, v, x)) => a + b + c < u + v + x }

    println(increases)
  }

  private def readFile(): List[Int] = {
    val path = SonarSweep2.getClass.getResource("input.txt").getPath
    val bufferedSource = io.Source.fromFile(path)
    val lines = (for (line <- bufferedSource.getLines()) yield line)
      .map(s => s.toInt)
      .toList
    bufferedSource.close
    lines
  }
}
