package co.eware.gists

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import co.eware.gists.base.activity.TopAppActivity
import co.eware.gists.categories.utils.livedataprefs.booleanLiveData
import co.eware.gists.categories.utils.livedataprefs.ui.LivePrefsFragment

class MainActivity : TopAppActivity() {

    private val preference: SharedPreferences by lazy {
        getSharedPreferences(
                LivePrefsFragment.PREFS_KEY,
                Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeRecentThemeMode()
    }

    private fun observeRecentThemeMode() {
        val currentTheme = preference.booleanLiveData(LivePrefsFragment.KEY_THEME, false)
        currentTheme.observe(this) {
            it?.let { v ->
                val uiMode = when (v) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }

                AppCompatDelegate.setDefaultNightMode(uiMode)
            }
        }
    }
}