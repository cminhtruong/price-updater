package app.el_even.priceupdater.views.viewmodels

import android.app.Application
import androidx.lifecycle.*
import app.el_even.priceupdater.databases.ProductDatabase
import app.el_even.priceupdater.databases.ProductRepository
import app.el_even.priceupdater.models.local.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import timber.log.Timber
import java.lang.IllegalArgumentException

/**
 * @author el_even
 * @version 1.0
 */
class ListProductViewModel(application: Application) : ViewModel() {
    private val _isFabClicked = MutableLiveData<Boolean>()
    val isFabClicked: LiveData<Boolean> = _isFabClicked

    private val _isProgressBarDisplayed = MutableLiveData<Boolean>()
    val isProgressBarDisplayed: LiveData<Boolean> = _isProgressBarDisplayed

    private val repository = ProductRepository(
        ProductDatabase.getDatabase(
            application.applicationContext,
            viewModelScope
        )
    )

    val products: LiveData<List<Product>> = repository.products.asLiveData()

    init {
        _isFabClicked.value = false
        _isProgressBarDisplayed.value = false
        extractProduct("https://www.nocibe.fr/gucci-bloom-acqua-di-fiori-eau-de-toilette-50-ml-s226033")
    }

    fun addNewUrl() {
        _isFabClicked.value = true
    }

    fun addNewUrlDone() {
        _isFabClicked.value = false
    }

    fun addNewProduct(url: String) {
        Timber.d("url: $url")
        viewModelScope.launch(Dispatchers.IO) {
            _isProgressBarDisplayed.postValue(true)

            delay(5000)
            _isProgressBarDisplayed.postValue(false)
        }
    }

    fun extractProduct(url: String){
        Timber.d("extractProduct with $url")
        val doc = Jsoup.connect(url).get()
        doc.select(".prdct__name")
            .map {
                Timber.d("it $it")
            }.parallelStream()
            .forEach {
                Timber.d("element: $it")
            }
    }
}

class ListProductFragmentsViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListProductViewModel::class.java)) return ListProductViewModel(
            application
        ) as T
        throw IllegalArgumentException("Unable to construct this ViewModel")
    }

}