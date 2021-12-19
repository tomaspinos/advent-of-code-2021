package day18

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SnailfishNumberReaderTest {

    val reader = SnailfishNumberReader()

    @Test
    fun read() {
        assertEquals(
            SnailfishNumberPair(SnailfishNumberRegular(1), SnailfishNumberRegular(2)),
            reader.read("[1,2]")
        )
        assertEquals(
            SnailfishNumberPair(SnailfishNumberPair(SnailfishNumberRegular(1), SnailfishNumberRegular(2)), SnailfishNumberRegular(3)),
            reader.read("[[1,2],3]")
        )
        assertEquals(
            SnailfishNumberPair(
                SnailfishNumberPair(
                    SnailfishNumberPair(
                        SnailfishNumberPair(
                            SnailfishNumberRegular(1),
                            SnailfishNumberRegular(2)
                        ),
                        SnailfishNumberPair(
                            SnailfishNumberRegular(3),
                            SnailfishNumberRegular(4)
                        )
                    ),
                    SnailfishNumberPair(
                        SnailfishNumberPair(
                            SnailfishNumberRegular(5),
                            SnailfishNumberRegular(6)
                        ),
                        SnailfishNumberPair(
                            SnailfishNumberRegular(7),
                            SnailfishNumberRegular(8)
                        )
                    )
                ),
                SnailfishNumberRegular(9)
            ),
            reader.read("[[[[1,2],[3,4]],[[5,6],[7,8]]],9]")
        )
    }
}