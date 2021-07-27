package app.el_even.priceupdater.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val url: String,
    val image: List<String>,
    val oldPrice: Float,
    val newPrice: Float? = 0.0f,
    val date: String,
    val brand: Brand
)

enum class Brand {
    NOCIBE,
    SEPHORA,
    MARIONNAUD
}