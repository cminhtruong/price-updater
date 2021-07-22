package app.el_even.priceupdater.models

data class Product(
    val id: Int,
    val name: String,
    val url: String,
    val image: List<String>,
    val price: Float,
    val brand: Brand
)

enum class Brand {
    NOCIBE,
    SEPHORA,
    MARIONNAUD
}