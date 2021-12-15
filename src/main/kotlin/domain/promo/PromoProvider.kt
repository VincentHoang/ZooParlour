package domain.promo

interface PromoProvider {
    fun getPromo(productCode: String): Promo?
}
