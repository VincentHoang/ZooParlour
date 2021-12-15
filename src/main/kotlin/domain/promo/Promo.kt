package domain.promo

import domain.Discount
import domain.cart.Item

interface Promo{
    fun getDiscounts(item: Item): List<Discount>
}
