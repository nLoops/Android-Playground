package co.eware.gists.categories.core.patterns.observer

/**
 * Created by Ahmed Ibrahim on 23,December,2020
 */
interface WeatherObserver {
    fun update(weatherType: WeatherType)
}