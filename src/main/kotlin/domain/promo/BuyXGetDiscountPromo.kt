package domain.promo

import domain.Discount
import domain.cart.Item
import domain.toCurrency
import java.math.BigDecimal

data class BuyXGetDiscountPromo(
    val quantityToQualify: Int,
    val discountMultiplier: BigDecimal,
    val description: String
): Promo {
    override fun getDiscounts(item: Item): List<Discount> {
        val numberOfDiscounts = item.quantity / quantityToQualify

        return buildList {
            repeat(numberOfDiscounts) {
                add(Discount(description, (-item.price * discountMultiplier).toCurrency()))
            }
        }
    }
}
