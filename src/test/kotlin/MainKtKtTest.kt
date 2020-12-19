import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class MainKtKtTest {

    @Nested
    inner class VkTests {

        @Nested
        inner class PositiveTests {

            @Test
            fun testZeroPayFee4VkPlay() {
                val feeVk = payFee(lastTransferSum = 34_999, currentTransferSum = 14_999)
                assertTrue(feeVk == 0)
            }
        }

        @Nested
        inner class NegativeTests {

            @Test
            fun `If we broke limit function should throw IllegalArgumentException`() {
                val breakLimit = assertThrows(
                    IllegalArgumentException::class.java
                ) {
                    payFee(lastTransferSum = 16_000, currentTransferSum = 40_000)
                }
                println(breakLimit)
                assertTrue(breakLimit.message.equals("Вы превысили лимит! Ваш перевод = 40000, ваши переводы в этом месяце = 56000"))
            }
        }
    }

    @Nested
    inner class MasterAndMaestroTests {

        @Nested
        inner class PositiveTests {

            @Test
            fun `MasterCard If current sum is less than 75k then payFee 0`() {
                assertTrue(payFee("MasterCard", 55_111, 49_999) == 0)
            }

            @Test
            fun `If current sum is greater than 75k then payFee is not 0`() {
                assertTrue(payFee("MasterCard", 55_111, 99_999) == 61900)
            }

            @Test
            fun `MasterCard If prev sum is less than 600k then payFee 0`() {
                assertTrue(payFee("MasterCard", 55_111, 74_999) == 0)
            }
        }

        @Nested
        inner class NegativeTests {

            @Test
            fun `If current sum is greater than 150 then it should throw IllegalArgumentException`() {
                val exception = assertThrows(IllegalArgumentException::class.java) {
                    payFee("MasterCard", 55_111, 199_999)
                }
                assertTrue(exception.message.equals("Вы превысили лимит! Ваш перевод = 199999, ваши переводы в этом месяце = 255110"))
            }

            @Test
            fun `If prev sum is greater than 600k then it should throw IllegalArgumentException`() {
                val exception = assertThrows(IllegalArgumentException::class.java) {
                    payFee("MasterCard", 600_000, 9_999)
                }
                assertTrue(exception.message.equals("Вы превысили лимит! Ваш перевод = 9999, ваши переводы в этом месяце = 609999"))
            }
        }
    }

    @Nested
    inner class VisaAndMirTests {

        @Nested
        inner class PositiveTests {

            @Test
            fun `If current sum is greater than 75k then payFee is not 0`() {
                assertTrue(payFee("Visa", 55_111, 99_999) == 74900)
            }

            @Test
            fun `If fee less or equal than 35 then fee is  35`() {
                println(payFee("Visa", 5_111, 999))
                assertTrue(payFee("Visa", 5_111, 999) == 3500)
            }
        }

        @Nested
        inner class NegativeTests {

            @Test
            fun `If prev sum is greater than 600k then it should throw IllegalArgumentException`() {
                val exception = assertThrows(IllegalArgumentException::class.java) {
                    payFee("Мир", 600_000, 9_999)
                }
                println(exception.message)
                assertTrue(exception.message.equals("Вы превысили лимит! Ваш перевод = 9999, ваши переводы в этом месяце = 609999"))
            }
        }
    }

    @Nested
    inner class GeneralNegativeTests {
        @Test
        fun `Wrong card provided should throw the IllegalArgumentException`() {
            val wrongCardException = assertThrows(IllegalArgumentException::class.java) {
                payFee("", 0, 0)
            }
            assertTrue(wrongCardException.localizedMessage.equals("Карта  не поддерживается!"))
        }
    }
}