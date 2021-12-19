package day18

class SnailfishNumberReader {

    fun read(input: List<String>): List<SnailfishNumber> {
        return input.map(this::read)
    }

    fun read(input: String): SnailfishNumber {
        return read(input.toCharArray().toMutableList())
    }

    private fun read(characters: MutableList<Char>): SnailfishNumber {
        val ch = characters.removeFirst()
        if (ch == '[') {
            val left = read(characters)
            if (characters.first() != ',') {
                throw IllegalStateException("Unexpected character: ${characters.first()}")
            }
            characters.removeFirst()
            val right = read(characters)
            if (characters.first() != ']') {
                throw IllegalStateException("Unexpected character: ${characters.first()}")
            }
            characters.removeFirst()
            return SnailfishNumberPair(left, right)
        } else if (ch.isDigit()) {
            var value = ch.toString()
            while (characters.first().isDigit()) {
                value += characters.removeFirst()
            }
            return SnailfishNumberRegular(value.toLong())
        } else {
            throw IllegalStateException("Unexpected character: $ch")
        }
    }
}