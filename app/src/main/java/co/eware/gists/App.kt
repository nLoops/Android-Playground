package co.eware.gists

import android.app.Application
import timber.log.Timber

/**
 * Created by Ahmed Ibrahim on 23,December,2020
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}