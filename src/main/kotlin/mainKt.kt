import java.util.*

fun main(): Unit {
    println("Enter amount you would like to transfer:")
    val scanner = Scanner(System.`in`).useLocale(Locale.US)
    val amount : Double = scanner.nextDouble() // in rubles
    println("You entered : $amount rub")
    val finalFee = calculateFee(amount)
    println("Fee for money transfer : $finalFee rub")
}


fun calculateFee(amount : Double) : Double {
    val percent2Decimal = 0.75 / 100
    val fee = amount * percent2Decimal
    println("Fee : $fee")
    return if (fee <= 35) {
        35.00
    } else {
        fee
    }
}