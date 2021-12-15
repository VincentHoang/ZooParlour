package domain.checkout

import domain.checkout.CheckoutItem
import java.math.BigDecimal

data class CheckoutBill(
    val items: List<CheckoutItem>,
    val total: BigDecimal,
    val totalPromos: BigDecimal,
    val totalToPay: BigDecimal
)
