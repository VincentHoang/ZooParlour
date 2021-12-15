package domain

import java.math.BigDecimal

data class Discount(
    val description: String,
    val amount: BigDecimal
)
