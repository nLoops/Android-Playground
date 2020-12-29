package co.eware.gists.categories.utils.livedataprefs.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import co.eware.gists.base.activity.PermissionActivity
import co.eware.gists.base.fragment.BaseBindingFragment
import co.eware.gists.categories.utils.livedataprefs.booleanLiveData
import co.eware.gists.categories.utils.livedataprefs.intLiveData
import co.eware.gists.databinding.FragmentLiveprefsLayoutBinding
import kotlinx.coroutines.launch

/**
 * Created by Ahmed Ibrahim on 29,December,2020
 */
class LivePrefsFragment : BaseBindingFragment<FragmentLiveprefsLayoutBinding>() {

    private val preference: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
                PREFS_KEY,
                Context.MODE_PRIVATE
        )
    }


    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentLiveprefsLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            val intValue = preference.intLiveData(KEY_INT, 0)
            intValue.observe(viewLifecycleOwner) {
                tvData.text = "$it"
            }

            btnAdd.setOnClickListener {
                val prev = intValue.value
                prev?.let { v -> lifecycleScope.launch { preference.edit().putInt(KEY_INT, v.inc()).apply() } }
            }

            btnMinus.setOnClickListener {
                val prev = intValue.value
                prev?.let { v -> lifecycleScope.launch { preference.edit().putInt(KEY_INT, v.dec()).apply() } }
            }

            btnThemeToggle.setOnClickListener {
                val currentTheme = preference.booleanLiveData(KEY_THEME, false)
                val value = currentTheme.value
                value?.let { v ->
                    lifecycleScope.launch {
                        preference.edit().putBoolean(KEY_THEME, !v).apply()
                    }
                }
            }
        }
    }


    companion object {
        const val PREFS_KEY = "LIVEDATA"
        const val KEY_INT = "int_value"
        const val KEY_THEME = "app_theme"
    }
}