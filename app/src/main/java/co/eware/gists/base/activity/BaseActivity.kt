package co.eware.gists.base.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Created by Ahmed Ibrahim on 22,December,2020
 */
abstract class BaseActivity : AppCompatActivity() {

    fun showAlertDialog(title: String?, message: String?, positiveActionText: String?,
                        negativeText: String?, positiveAction: () -> Unit = {}, negativeAction: () -> Unit = {}) {
        MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveActionText) { _, _ -> positiveAction() }
                .setNegativeButton(negativeText) { _, _ -> negativeAction() }
                .show()
    }
}