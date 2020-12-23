package co.eware.gists.categories.core.patterns.observer

import timber.log.Timber

/**
 * Created by Ahmed Ibrahim on 23,December,2020
 */
class Weather {

    var currentWeather: WeatherType = WeatherType.SUNNY
    var observers: MutableList<WeatherObserver> = ArrayList()


    fun addObserver(obs: WeatherObserver) = observers.add(obs)

    fun removeObserver(obs: WeatherObserver) = observers.remove(obs)

    fun timePasses() {
        val enumValue = WeatherType.values()
        currentWeather = enumValue[(currentWeather.ordinal + 1) % enumValue.size]
        Timber.i("The weather changed to [$currentWeather]")
        notifyObservers()
    }

    private fun notifyObservers() {
        observers.forEach { it.update(currentWeather) }
    }

}