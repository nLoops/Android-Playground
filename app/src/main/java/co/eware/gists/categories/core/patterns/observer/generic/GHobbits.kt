package co.eware.gists.categories.core.patterns.observer.generic

import android.util.Log
import co.eware.gists.categories.core.patterns.observer.WeatherType

/**
 * Created by Ahmed Ibrahim on 24,December,2020
 */
class GHobbits : Race {
    override fun update(subject: GWeather, argument: WeatherType) {
        Log.d("Observer", "The hobbits are facing ${argument.name} weather now")
    }
}