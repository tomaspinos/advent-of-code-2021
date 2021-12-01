package day01

object SonarSweep1 {

  def main(args: Array[String]): Unit = {
    val values = readFile().to(LazyList)

    val increases = values.zip(values.drop(1))
      .count { case (u, v) => u < v }

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
