package co.eware.gists.categories.core.patterns.observer.generic

import co.eware.gists.categories.core.patterns.observer.WeatherType
import timber.log.Timber

/**
 * Created by Ahmed Ibrahim on 24,December,2020
 */
class GWeather : Observable<GWeather, Race, WeatherType>() {

    var currentWeather = WeatherType.SUNNY

    fun timePasses() {
        val enumValue = WeatherType.values()
        currentWeather = enumValue[(currentWeather.ordinal + 1) % enumValue.size]
        Timber.i("The weather changed to [$currentWeather]")
        notifyObservers(currentWeather)
    }

}