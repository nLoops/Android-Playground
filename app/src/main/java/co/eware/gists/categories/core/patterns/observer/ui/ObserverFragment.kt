package co.eware.gists.categories.core.patterns.observer.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import co.eware.gists.base.fragment.BaseBindingFragment
import co.eware.gists.categories.core.patterns.observer.Person
import co.eware.gists.categories.core.patterns.observer.Pressing
import co.eware.gists.categories.core.patterns.observer.Weather
import co.eware.gists.databinding.FragmentObserverBinding
import timber.log.Timber

/**
 * Created by Ahmed Ibrahim on 23,December,2020
 */
class ObserverFragment : BaseBindingFragment<FragmentObserverBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentObserverBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = Weather()
        weather.addObserver(Person())
        weather.addObserver(Pressing())

        binding.run {
            tvPerson.setFactory {
                TextView(requireContext()).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    gravity = Gravity.CENTER_HORIZONTAL
                    textSize = 14f
                    setTextColor(Color.WHITE)
                }
            }

            // Default state
            tvPerson.setCurrentText(weather.currentWeather.name)

            btnEmit.setOnClickListener {
                weather.timePasses()
                tvPerson.setText(weather.currentWeather.name)
            }
        }
    }
}