package app.el_even.priceupdater.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import app.el_even.priceupdater.configs.DATABASE_NAME
import app.el_even.priceupdater.configs.DATABASE_VERSION
import app.el_even.priceupdater.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author el_even
 * @version 1.0
 */
@Database(entities = [Product::class], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(ProductConverters::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .addCallback(ProductDatabaseCallBack(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ProductDatabaseCallBack(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    database.deleteOldDatabase(database.productsDao())
                }
            }
        }
    }

    suspend fun deleteOldDatabase(productsDao: ProductDao) {
        productsDao.deleteOldDatabase()
    }
}