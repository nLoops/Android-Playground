package co.eware.gists

import android.os.Bundle
import co.eware.gists.base.activity.TopAppActivity

class MainActivity : TopAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}