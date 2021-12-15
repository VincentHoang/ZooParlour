package domain

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.toCurrency() = setScale(2, RoundingMode.HALF_EVEN)
