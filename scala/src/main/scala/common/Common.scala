package common

object Common {

  def readFile(name: String): LazyList[String] = {
    val path = Common.getClass.getResource(name).getPath
    val bufferedSource = io.Source.fromFile(path)
    val lines = (for (line <- bufferedSource.getLines()) yield line)
      .toList
      .to(LazyList)
    bufferedSource.close
    lines
  }
}
