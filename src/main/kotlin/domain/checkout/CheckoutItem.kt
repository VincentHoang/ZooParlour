package domain.checkout

import domain.product.Product
import java.math.BigDecimal

data class CheckoutItem(
    val product: Product,
    val quantity: Int,
    val total: BigDecimal,
    val totalDiscount: BigDecimal

) {
    val price by lazy {
        product.price
    }

    val productCode by lazy {
        product.code
    }
}
