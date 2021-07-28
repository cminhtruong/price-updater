package app.el_even.priceupdater.databases

import androidx.room.*
import app.el_even.priceupdater.models.local.Product
import kotlinx.coroutines.flow.Flow

/**
 * @author el_even
 * @version 1.0
 */
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewProduct(vararg product: Product)

    @Update
    fun updateProduct(vararg product: Product)

    @Delete
    fun deleteProduct(vararg product: Product)

    @Query("SELECT * FROM product")
    fun loadProducts(): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE id = :id")
    fun loadProductById(id: Int): Flow<Product>

    @Query("DELETE FROM product")
    fun deleteOldDatabase()
}
