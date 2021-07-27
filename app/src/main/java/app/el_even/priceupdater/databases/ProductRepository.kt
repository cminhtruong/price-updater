package app.el_even.priceupdater.databases

import androidx.annotation.WorkerThread
import app.el_even.priceupdater.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * @author el_even
 * @version 1.0
 */
class ProductRepository(private val database: ProductDatabase) {

    val products: Flow<List<Product>> = database.productsDao().loadProducts()

    @WorkerThread
    suspend fun addNewProduct(url: String) {
        withContext(Dispatchers.IO) {

            database.productsDao().insertNewProduct()
        }
    }

    @WorkerThread
    suspend fun updateProductPrices(product: Product) {
        withContext(Dispatchers.IO) {
            database.productsDao().updateProduct(product)
        }
    }

    @WorkerThread
    suspend fun getProductById(id: Int): Flow<Product> = withContext(Dispatchers.IO) {
        database.productsDao().loadProductById(id)
    }
}