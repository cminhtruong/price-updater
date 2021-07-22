package app.el_even.priceupdater

import android.app.Application
import timber.log.Timber

/**
 * @author el_even
 * @version 1.0
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}