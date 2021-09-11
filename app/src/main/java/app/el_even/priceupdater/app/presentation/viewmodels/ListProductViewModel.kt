package app.el_even.priceupdater.app.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.el_even.priceupdater.utils.getValueByClass
import app.el_even.priceupdater.app.framework.databases.ProductDatabase
import app.el_even.priceupdater.core.data.ProductRepository
import app.el_even.priceupdater.models.local.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import timber.log.Timber

/**
 * @author el_even
 * @version 1.0
 */
class ListProductViewModel(application: Application) : ViewModel() {
    private val _isFabClicked = MutableStateFlow(false)
    val isFabClicked: StateFlow<Boolean> = _isFabClicked

    private val _isProgressBarDisplayed = MutableStateFlow(false)
    val isProgressBarDisplayed: StateFlow<Boolean> = _isProgressBarDisplayed

    private val repository = ProductRepository(
        ProductDatabase.getDatabase(
            application.applicationContext,
            viewModelScope
        )
    )

    val products: StateFlow<List<Product>> = repository.products.stateIn(
        viewModelScope, SharingStarted.Lazily, listOf()
    )

    init {
        Timber.d("init")
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
            _isProgressBarDisplayed.value = true

            delay(5000)
            _isProgressBarDisplayed.value = false
        }
    }

    private fun extractProduct(url: String) {
        Timber.d("extractProduct with $url")
        when {
            "nocibe" in url -> extractNocibeProduct(url)
            "sephora" in url -> extractSephoraProduct(url)
            "marionnaud" in url -> extractMarionnaudProduct(url)
        }
    }

    private fun extractNocibeProduct(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val doc = Jsoup.connect(url).get()
            val brand = doc.getValueByClass(".prdct__name", "prdct__name")
        }
    }

    private fun extractSephoraProduct(url: String) {

    }

    private fun extractMarionnaudProduct(url: String) {

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