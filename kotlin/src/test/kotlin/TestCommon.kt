import java.io.File

class TestCommon {

    companion object {

        fun readInput(name: String): List<String> {
            return File(TestCommon::class.java.getResource(name).path)
                .readLines()
        }
    }
}