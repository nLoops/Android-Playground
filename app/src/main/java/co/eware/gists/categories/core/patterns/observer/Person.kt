package co.eware.gists.categories.core.patterns.observer

import timber.log.Timber

/**
 * Created by Ahmed Ibrahim on 23,December,2020
 */
class Person : WeatherObserver {

    override fun update(weatherType: WeatherType) {
        Timber.i("The person is facing ${weatherType.name} right now")
    }
}