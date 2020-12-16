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
                val feeVk = payFee(lastTransferSum = 34_999.10, currentTransferSum = 14_999.00)
                assertTrue(feeVk == 0.0)
            }
        }

        @Nested
        inner class NegativeTests {

            @Test
            fun `If we broke limit function should throw IllegalArgumentException`() {
                val breakLimit = assertThrows(
                    IllegalArgumentException::class.java
                ) {
                    payFee(lastTransferSum = 16_000.99, currentTransferSum = 40_000.099)
                }
                assertTrue(breakLimit.message.equals("Вы превысили лимит! Ваш перевод = 40000.099, ваши переводы в этом месяце = 56001.089"))
            }
        }
    }

    @Nested
    inner class MasterAndMaestroTests {

        @Nested
        inner class PositiveTests {

            @Test
            fun `MasterCard If current sum is less than 75k then payFee 0`() {
                assertTrue(payFee("MasterCard", 55_111.2222, 49_999.0) == 0.0)
            }

            @Test
            fun `If current sum is greater than 75k then payFee is not 0`() {
                assertTrue(payFee("MasterCard", 55_111.2222, 99_999.0) == 619.994)
            }

            @Test
            fun `MasterCard If prev sum is less than 600k then payFee 0`() {
                assertTrue(payFee("MasterCard", 55_111.2222, 74_999.0) == 0.0)
            }
        }

        @Nested
        inner class NegativeTests {

            @Test
            fun `If current sum is greater than 150 then it should throw IllegalArgumentException`() {
                val exception = assertThrows(IllegalArgumentException::class.java) {
                    payFee("MasterCard", 55_111.2222, 199_999.0)
                }
                assertTrue(exception.message.equals("Вы превысили лимит! Ваш перевод = 199999.0, ваши переводы в этом месяце = 255110.2222"))
            }

            @Test
            fun `If prev sum is greater than 600k then it should throw IllegalArgumentException`() {
                val exception = assertThrows(IllegalArgumentException::class.java) {
                    payFee("MasterCard", 600_000.2222, 9_999.0)
                }
                println(exception.message)
                assertTrue(exception.message.equals("Вы превысили лимит! Ваш перевод = 9999.0, ваши переводы в этом месяце = 609999.2222"))
            }
        }
    }

    @Nested
    inner class VisaAndMirTests {

        @Nested
        inner class PositiveTests {

            @Test
            fun `If current sum is greater than 75k then payFee is not 0`() {
                assertTrue(payFee("Visa", 55_111.2222, 99_999.0) == 749.993)
            }

            @Test
            fun `If fee less or equal than 35 then fee is  35`() {
                println(payFee("Visa", 5_111.2222, 999.0))
                assertTrue(payFee("Visa", 5_111.2222, 999.0) == 35.0)
            }
        }

        @Nested
        inner class NegativeTests {

            @Test
            fun `If prev sum is greater than 600k then it should throw IllegalArgumentException`() {
                val exception = assertThrows(IllegalArgumentException::class.java) {
                    payFee("Мир", 600_000.2222, 9_999.0)
                }
                println(exception.message)
                assertTrue(exception.message.equals("Вы превысили лимит! Ваш перевод = 9999.0, ваши переводы в этом месяце = 609999.2222"))
            }
        }
    }

    @Nested
    inner class GeneralNegativeTests {
        @Test
        fun `Wrong card provided should throw the IllegalArgumentException`() {
            val wrongCardException = assertThrows(IllegalArgumentException::class.java) {
                payFee("", 0.111, 0.3333)
            }
            assertTrue(wrongCardException.localizedMessage.equals("Карта  не поддерживается!"))
        }
    }
}