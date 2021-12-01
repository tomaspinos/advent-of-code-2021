package day1

object SonarSweep2 {

  def main(args: Array[String]): Unit = {
    val values = readFile().to(LazyList)

    val values1 = values.slice(0, values.size - 3)
    val values2 = values.slice(1, values.size - 2)
    val values3 = values.slice(2, values.size - 1)
    val values4 = values.slice(3, values.size)
    val slidingWindows1 = (values1, values2, values3).zipped
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
