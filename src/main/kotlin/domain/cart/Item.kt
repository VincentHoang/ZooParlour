package domain.cart

import domain.product.Product

data class Item(
    val product: Product,
    val quantity: Int
) {
    val price by lazy {
        product.price
    }

    val productCode by lazy {
        product.code
    }
}
