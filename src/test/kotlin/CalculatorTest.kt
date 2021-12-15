import domain.checkout.CheckoutBill
import domain.Calculator
import domain.cart.Item
import domain.cart.Order
import domain.product.Product
import domain.checkout.CheckoutItem
import domain.promo.BuyXGetDiscountPromo
import domain.promo.BasicPromoProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CalculatorTest {
    private val product1 = Product("p1", "Product One", BigDecimal("5.50"))
    private val product2 = Product("p2", "Product Two", BigDecimal("2.30"))

    @Test
    fun `should return bill with total cost`() {
        val calculator = Calculator(BasicPromoProvider(mapOf()))

        val items = listOf(
            Item(product1, 5),
            Item(product2, 10)
        )

        assertThat(calculator.getPrice(Order(items))).isEqualTo(
            CheckoutBill(
                items = listOf(
                    CheckoutItem(product1, 5, BigDecimal("27.50"), BigDecimal("0")),
                    CheckoutItem(product2, 10, BigDecimal("23.00"), BigDecimal("0"))
                ),
                total = BigDecimal("50.50"),
                totalPromos = BigDecimal("0.00"),
                totalToPay = BigDecimal("50.50")
            )
        )
    }

    @Test
    fun `should return bill with discounts`() {
        val rockyRoad = Product("p1", "Rock Road", BigDecimal("8.00"))
        val cookiesAndCream = Product("p2", "Cookies & Cream", BigDecimal("10.00"))
        val netflixAndChill = Product("p3", "Netflix & Chill", BigDecimal("12.00"))

        val rockRoadPromo = BuyXGetDiscountPromo(2, BigDecimal("1.00"), "Buy 2 Get 1 Free")
        val cookiesAndCreamPromo = BuyXGetDiscountPromo(2, BigDecimal("0.50"), "Buy 2 Get 1 Half Price")
        val calculator = Calculator(
            BasicPromoProvider(
                mapOf(
                    rockyRoad.code to rockRoadPromo,
                    cookiesAndCream.code to cookiesAndCreamPromo
                )
            )
        )

        val items = listOf(
            Item(rockyRoad, 1),
            Item(cookiesAndCream, 3),
            Item(netflixAndChill, 2)
        )

        val result = calculator.getPrice(Order(items))


        assertThat(result).isEqualTo(
            CheckoutBill(
                items = listOf(
                    CheckoutItem(rockyRoad, 1, BigDecimal("8.00"), BigDecimal("0")),
                    CheckoutItem(cookiesAndCream, 3, BigDecimal("30.00"), BigDecimal("-5.00")),
                    CheckoutItem(netflixAndChill, 2, BigDecimal("24.00"), BigDecimal("0"))
                ),

                total = BigDecimal("62.00"),
                totalPromos = BigDecimal("-5.00"),
                totalToPay = BigDecimal("57.00")
            )
        )
    }
}
