import java.io.File

class Common {

    companion object {

        fun readInput(name: String): List<String> {
            return File(Common::class.java.getResource(name).path)
                .readLines()
        }
    }
}