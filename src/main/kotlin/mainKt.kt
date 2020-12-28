import java.lang.IllegalArgumentException
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`).useLocale(Locale.US)
    println("Введите тип карты (VkPlay, MasterCard, Maestro, Visa, Мир):")
    val cardIssuer = scanner.next()
    println("Введите cумму предыдущих переводов в этом месяце:")
    val lastTransferSum = scanner.nextInt() * 100
    println("Введите cумму для перевода:")
    val currentTransferSum = scanner.nextInt() * 100
    println("Ваша комиссия = ${payFee(cardIssuer, lastTransferSum, currentTransferSum)} копеек")
}

fun payFee(cardIssuer: String = "VkPlay", lastTransferSum: Int, currentTransferSum: Int):  Int {
    println("Расчет для $cardIssuer, сумма предыдущих переводов $lastTransferSum копеек, сумма для перевода $currentTransferSum копеек")
    val userTransfersInMonth = lastTransferSum + currentTransferSum
    val fee = when {
        cardIssuer.equals("VkPlay") -> {
            if (currentTransferSum >= 15_000_00 && (userTransfersInMonth <= 40_000_00))
                throw IllegalArgumentException("Вы превысили лимит! Ваш перевод = $currentTransferSum, ваши переводы в этом месяце = $userTransfersInMonth")
            else 0
        }
        cardIssuer in listOf("MasterCard", "Maestro") -> {
            if (currentTransferSum >= 150_000_00 || userTransfersInMonth >= 600_000_00)
                throw IllegalArgumentException("Вы превысили лимит! Ваш перевод = $currentTransferSum, ваши переводы в этом месяце = $userTransfersInMonth")
            else if (currentTransferSum <= 75_000_00) {
                0
            } else {
                currentTransferSum * (0.6 / 100) + 20_00
            }
        }
        cardIssuer in listOf("Visa", "Мир") -> {
            if (currentTransferSum >= 150_000_00 || userTransfersInMonth >= 600_000_00)
                throw IllegalArgumentException("Вы превысили лимит! Ваш перевод = $currentTransferSum, ваши переводы в этом месяце = $userTransfersInMonth")
            val percent2Decimal = 0.75 / 100
            val fee = currentTransferSum * percent2Decimal
            if (fee <= 35_00) {
                35_00
            } else {
                fee
            }
        }
        else -> throw IllegalArgumentException("Карта $cardIssuer не поддерживается!")
    }
    return fee.toInt()
}