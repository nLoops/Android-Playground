package co.eware.gists.base.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import co.eware.gists.utils.Event

/**
 * Created by Ahmed Ibrahim on 28,December,2020
 */
class HomeViewModel : ViewModel() {

    private val _navTo = MutableLiveData<Event<NavDirections>>()
    val navTo: LiveData<Event<NavDirections>> = _navTo


    fun onItemClicked(directions: NavDirections) {
        _navTo.value = Event(directions)
    }

}