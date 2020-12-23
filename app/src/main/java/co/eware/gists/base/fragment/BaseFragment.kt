package co.eware.gists.base.fragment

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Ahmed Ibrahim on 22,December,2020
 */
abstract class BaseFragment : Fragment() {

    private var progressDialog: ProgressDialog? = null
    private var errorDialog: ErrorDialog? = null

    fun applicationContext(): Context = requireActivity().applicationContext

    fun showAlertDialog(title: String?, message: String?, positiveActionText: String?,
                        negativeText: String?, positiveAction: () -> Unit = {}, negativeAction: () -> Unit = {}) {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveActionText) { _, _ -> positiveAction() }
                .setNegativeButton(negativeText) { _, _ -> negativeAction() }
                .show()
    }

    fun showSnackBar(message: String, anchor: View? = null) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).apply {
                anchorView = anchor
                show()
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog()
        }
        progressDialog?.let {
            if (!it.isVisible) {
                it.show(requireActivity().supportFragmentManager, TAG_PROGRESS_DIALOG)
            }
        }
    }

    fun hideProgressDialog() = progressDialog?.dismiss()

    fun showErrorDialog(title: String, message: String) {
        if (errorDialog == null) errorDialog = ErrorDialog()

        errorDialog?.apply {
            this.title = title
            this.message = message
        }

        errorDialog?.let {
            if (!it.isVisible) {
                it.show(requireActivity().supportFragmentManager, TAG_ERROR_DIALOG)
            }
        }
    }

    protected fun destroy() {
        progressDialog = null
        errorDialog = null
    }

    fun toggleKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInputFromWindow(view?.windowToken, InputMethodManager.SHOW_FORCED, 0)
    }

    companion object {
        private const val TAG_PROGRESS_DIALOG = "progress_dialog"
        private const val TAG_ERROR_DIALOG = "error_dialog"
    }

}