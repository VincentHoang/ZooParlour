package domain

import domain.cart.Order
import domain.checkout.CheckoutBill
import domain.checkout.CheckoutItem
import domain.promo.PromoProvider
import java.math.BigDecimal

// A different set of promos can be injected
class Calculator(private val promoProvider: PromoProvider) {

    fun getPrice(order: Order): CheckoutBill {
        val items = order.items

        // Calculate discounts for each item
        val checkoutItems = items.map { item ->
            val discounts = promoProvider.getPromo(item.productCode)?.getDiscounts(item)?: listOf()
            val totalDiscount =  discounts.sumOf { discount -> discount.amount }
            CheckoutItem(
                product = item.product,
                quantity = item.quantity,
                total = (item.product.price * BigDecimal(item.quantity.toString())),
                totalDiscount = totalDiscount
            )
        }
        val total = checkoutItems.sumOf { it.total }
        val totalPromos = checkoutItems.sumOf { it.totalDiscount }

        return CheckoutBill(
            items = checkoutItems,
            total = total.toCurrency(),
            totalPromos = totalPromos.toCurrency(),
            totalToPay = (total + totalPromos).toCurrency()
        )
    }

}

