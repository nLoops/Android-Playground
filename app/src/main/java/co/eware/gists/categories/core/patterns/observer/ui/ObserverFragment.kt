package co.eware.gists.categories.core.patterns.observer.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import co.eware.gists.R
import co.eware.gists.base.fragment.BaseBindingFragment
import co.eware.gists.categories.core.patterns.observer.generic.GHobbits
import co.eware.gists.categories.core.patterns.observer.generic.GOrcs
import co.eware.gists.categories.core.patterns.observer.generic.GWeather
import co.eware.gists.databinding.FragmentObserverBinding

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

        // Generic example
        val genericWeather = GWeather()
        genericWeather.addObserver(GOrcs())
        genericWeather.addObserver(GHobbits())

        binding.run {
            tvPerson.setFactory {
                TextView(requireContext()).apply {
                    layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    gravity = Gravity.CENTER_HORIZONTAL
                    textSize = 14f
                    setTextColor(resources.getColor(R.color.teal_200))
                }
            }

            // Default state
            tvPerson.setCurrentText(genericWeather.currentWeather.name)

            btnEmit.setOnClickListener {
                tvPerson.setText(genericWeather.currentWeather.name)
                genericWeather.timePasses()
            }
        }
    }
}