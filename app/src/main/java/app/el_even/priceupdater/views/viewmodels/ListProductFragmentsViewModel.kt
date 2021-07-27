package app.el_even.priceupdater.views.viewmodels

import android.app.Application
import androidx.lifecycle.*
import app.el_even.priceupdater.databases.ProductDatabase
import app.el_even.priceupdater.databases.ProductRepository
import app.el_even.priceupdater.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.IllegalArgumentException

/**
 * @author el_even
 * @version 1.0
 */
class ListProductFragmentsViewModel(application: Application) : ViewModel() {
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
}

class ListProductFragmentsViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListProductFragmentsViewModel::class.java)) return ListProductFragmentsViewModel(
            application
        ) as T
        throw IllegalArgumentException("Unable to construct this ViewModel")
    }

}