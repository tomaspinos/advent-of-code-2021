package day1

object SonarSweep1 {

  def main(args: Array[String]): Unit = {
    val values = readFile().to(LazyList)

    val values1 = values.slice(0, values.size - 1)
    val values2 = values.slice(1, values.size)
    val increases = values1.zip(values2).count { case (u, v) => u < v }

    println(increases)
  }

  private def readFile(): List[Int] = {
    val path = SonarSweep1.getClass.getResource("input.txt").getPath
    val bufferedSource = io.Source.fromFile(path)
    val lines = (for (line <- bufferedSource.getLines()) yield line)
      .map(s => s.toInt)
      .toList
    bufferedSource.close
    lines
  }
}
