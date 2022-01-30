package day20

import Common

fun main() {
    println(TrenchMap1().go(Common.readInput("/day20/input.txt")))
}

class TrenchMap1 {

    fun go(input: List<String>): Int {
        val (algorithm, image) = readInput(input)

        var extendedImage = image.extend()
        for (i in 0 until 2) {
            extendedImage = algorithm.apply(extendedImage).extend()
        }

        return extendedImage.countLightPixels()
    }

    fun readInput(input: List<String>): Pair<Algorithm, Image> {
        val algorithm = Algorithm(input.first().toCharArray().map { ch -> if (ch == '#') 1 else 0 }.toIntArray())

        val imageLines = input.subList(2, input.size)
        val pixels = Array(imageLines.first().length) { IntArray(imageLines.size) }
        for (x in imageLines.first().indices) {
            for (y in imageLines.indices) {
                pixels[x][y] = if (imageLines[y][x] == '#') 1 else 0
            }
        }
        val image = Image(pixels)

        return Pair(algorithm, image)
    }

    class Algorithm(val bits: IntArray) {

        fun apply(image: Image): Image {
            val newPixels = Array(image.width) { IntArray(image.height) }
            for (x in 0 until image.width) {
                for (y in 0 until image.height) {
                    val pixelNumber = image.pixelNumber(x, y)
                    newPixels[x][y] = bits[pixelNumber]
                }
            }
            return Image(newPixels)
        }
    }

    class Image(val pixels: Array<IntArray>) {

        val width = pixels.size

        val height = pixels[0].size

        fun extend(): Image {
            val newPixels = Array(width + 2) { IntArray(height + 2) }
            for (x in pixels.indices) {
                for (y in pixels[x].indices) {
                    newPixels[x + 1][y + 1] = pixels[x][y]
                }
            }
            return Image(newPixels)
        }

        fun pixelNumber(x: Int, y: Int): Int {
            return listOf(
                Pair(-1, -1), Pair(0, -1), Pair(1, -1),
                Pair(-1, 0), Pair(0, 0), Pair(1, 0),
                Pair(-1, 1), Pair(0, 1), Pair(1, 1)
            )
                .map { (deltaX, deltaY) -> pixel(x + deltaX, y + deltaY) }
                .joinToString("")
                .toInt(2)
        }

        fun countLightPixels(): Int {
            return pixels.sumOf { column -> column.sumOf { pixel -> pixel } }
        }

        private fun pixel(x: Int, y: Int): Int {
            return if (x !in pixels.indices || y !in pixels[0].indices) {
                0
            } else {
                pixels[x][y]
            }
        }
    }
}