import java.lang.IllegalArgumentException
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`).useLocale(Locale.US)
    println("Введите тип карты (VkPlay, MasterCard, Maestro, Visa, Мир):")
    val cardIssuer = scanner.next()
    println("Введите cумму предыдущих переводов в этом месяце:")
    val lastTransferSum = scanner.nextInt()
    println("Введите cумму для перевода:")
    val currentTransferSum = scanner.nextInt()
    println("Ваша комиссия = ${payFee(cardIssuer, lastTransferSum, currentTransferSum)} копейек")

}

fun payFee(cardIssuer: String = "VkPlay", lastTransferSum: Int, currentTransferSum: Int):  Double {
    println("Расчет для $cardIssuer")
    val userTransfersInMonth = lastTransferSum + currentTransferSum
    val fee = when {
        cardIssuer.equals("VkPlay") -> {
            if (currentTransferSum >= 15_000 && (userTransfersInMonth <= 40_0000))
                throw IllegalArgumentException("Вы превысили лимит! Ваш перевод = $currentTransferSum, ваши переводы в этом месяце = $userTransfersInMonth")
            else 0
        }
        cardIssuer in listOf("MasterCard", "Maestro") -> {
            if (currentTransferSum >= 150_000 || userTransfersInMonth >= 600_000)
                throw IllegalArgumentException("Вы превысили лимит! Ваш перевод = $currentTransferSum, ваши переводы в этом месяце = $userTransfersInMonth")
            else if (currentTransferSum <= 75_000) {
                0
            } else {
                currentTransferSum * (0.6 / 100) + 20
            }
        }
        cardIssuer in listOf("Visa", "Мир") -> {
            if (currentTransferSum >= 150_000 || userTransfersInMonth >= 600_000)
                throw IllegalArgumentException("Вы превысили лимит! Ваш перевод = $currentTransferSum, ваши переводы в этом месяце = $userTransfersInMonth")
            val percent2Decimal = 0.75 / 100
            val fee = currentTransferSum * percent2Decimal
            if (fee <= 35) {
                35.00
            } else {
                fee
            }
        }
        else -> throw IllegalArgumentException("Карта $cardIssuer не поддерживается!")
    }
    return fee.toDouble()
}