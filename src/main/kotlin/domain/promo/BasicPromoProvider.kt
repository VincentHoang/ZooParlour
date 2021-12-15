package domain.promo

class BasicPromoProvider(private val promos: Map<String, Promo>): PromoProvider {
    // Promos can be retrieved from other sources e.g. configs
    override fun getPromo(productCode: String): Promo? = promos[productCode]
}
