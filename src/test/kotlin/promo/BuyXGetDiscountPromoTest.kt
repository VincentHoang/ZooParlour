package promo

import domain.Discount
import domain.cart.Item
import domain.product.Product
import domain.promo.BuyXGetDiscountPromo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class BuyXGetDiscountPromoTest {

    @Test
    fun `should not return discount if item amount is less than quantityToQualify`() {
        val product1 = Product("p123", "Product 123", BigDecimal("8"))
        val promo = BuyXGetDiscountPromo(2, BigDecimal("1"), "This promo")


        assertThat(promo.getDiscounts(Item(product1, 1)))
            .isEqualTo(listOf<Discount>())
    }

    @Test
    fun `should return the correct discount`() {
        val product1 = Product("p123", "Product 123", BigDecimal("4.5"))
        val promo = BuyXGetDiscountPromo(2, BigDecimal("1"), "This promo")


        assertThat(promo.getDiscounts(Item(product1, 2)))
            .isEqualTo(listOf(
                Discount("This promo", BigDecimal("-4.50"))
            ))
    }

    @Test
    fun `should return the correct discount if half off`() {
        val product1 = Product("p123", "Product 123", BigDecimal("4.50"))
        val promo = BuyXGetDiscountPromo(2, BigDecimal("0.50"), "This promo")


        assertThat(promo.getDiscounts(Item(product1, 2)))
            .isEqualTo(listOf(
                Discount("This promo", BigDecimal("-2.25"))
            ))
    }

    @Test
    fun `should return 2 discounts if item quantity is multiples of promo quantity`() {
        val product1 = Product("p123", "Product 123", BigDecimal(5))
        val promo = BuyXGetDiscountPromo(2, BigDecimal(1), "This promo")


        assertThat(promo.getDiscounts(Item(product1, 4)))
            .isEqualTo(listOf(
                Discount("This promo", BigDecimal("-5.00")),
                Discount("This promo", BigDecimal("-5.00"))
            ))
    }

    @Test
    fun `should return multiple discounts if item quantity is multiples of promo quantity`() {
        val product1 = Product("p123", "Product 123", BigDecimal("5.00"))
        val promo = BuyXGetDiscountPromo(2, BigDecimal(1), "This promo")


        assertThat(promo.getDiscounts(Item(product1, 7)))
            .isEqualTo(listOf(
                Discount("This promo", BigDecimal("-5.00")),
                Discount("This promo", BigDecimal("-5.00")),
                Discount("This promo", BigDecimal("-5.00"))
            ))
    }
}
