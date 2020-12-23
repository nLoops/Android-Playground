package co.eware.gists.base.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import co.eware.gists.R
import co.eware.gists.utils.PermissionCallBack
import kotlinx.coroutines.launch

/**
 * Created by Ahmed Ibrahim on 21,December,2020
 */
abstract class PermissionActivity : BaseActivity() {
    /**
     * We have four cases
     *
     * User grant part of permissions
     * User denied all permissions
     * User mark part or all permissions to not ask again
     */
    private var callBack: PermissionCallBack? = null
    private var message: Int? = null

    data class Permission(val value: String, val state: PermissionState)

    enum class PermissionState(val access: Boolean) {
        DENIED(false),
        GRANTED(true),
        UNKNOWN(false)
    }

    private val preference: SharedPreferences by lazy { getSharedPreferences(PREFS_PERMISSIONS, Context.MODE_PRIVATE) }

    fun checkPermission(permissions: Array<String>, permissionsCallBack: PermissionCallBack, rationaleMessage: Int): Boolean {
        callBack = permissionsCallBack
        message = rationaleMessage

        return checkGranted(permissions)
    }

    private fun checkGranted(permissions: Array<String>): Boolean {
        // Check if there's any left permission need to be grant
        val permissionList = permissions.map { p -> Permission(p, getPermissionState(p)) }.filter { s -> !s.state.access }

        // if we don't have any permission need to be grant then return true else ask for permission
        // and return false
        return if (permissionList.isEmpty()) {
            true
        } else {
            ask(permissionList.toTypedArray())
            false
        }
    }

    private fun getPermissionState(permission: String) = convertToPermissionState(
            code = enumValueOf(preference.getString(permission, PermissionState.UNKNOWN.name)!!),
            access = ContextCompat.checkSelfPermission(applicationContext,
                    permission) == PackageManager.PERMISSION_GRANTED)

    private fun convertToPermissionState(code: PermissionState, access: Boolean): PermissionState {
        if (access) return PermissionState.GRANTED
        return when (code) {
            PermissionState.UNKNOWN -> PermissionState.UNKNOWN
            else -> PermissionState.DENIED
        }
    }

    private fun ask(permissions: Array<Permission>) {
        // Check if any of permissions should to show rationale
        val shouldShowRationale = permissions.any { it.state == PermissionState.UNKNOWN && ActivityCompat.shouldShowRequestPermissionRationale(this, it.value) }
        val shouldGoToAppSettings = permissions.any { it.state == PermissionState.DENIED && !ActivityCompat.shouldShowRequestPermissionRationale(this, it.value) }

        when {
            shouldShowRationale -> showPermissionRationaleDialog(permissions)
            shouldGoToAppSettings -> openAppSettings()
            else -> requestPermissions(permissions)
        }
    }

    private fun showPermissionRationaleDialog(permissions: Array<Permission>) {
        message?.let {
            showAlertDialog(title = getString(R.string.permission_title),
                    message = getString(it), positiveActionText = getString(R.string.button_ok),
                    negativeText = getString(android.R.string.cancel),
                    positiveAction = { requestPermissions(permissions) },
                    negativeAction = { lifecycleScope.launch { permissions.forEach { p -> preference.edit().putString(p.value, PermissionState.DENIED.name).apply() } } })
        }
    }

    private fun openAppSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", packageName, null)
            startActivity(this)
        }
    }

    private fun requestPermissions(permissions: Array<Permission>) {
        ActivityCompat.requestPermissions(this, permissions.map { it.value }.toTypedArray(), PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults.all { s -> s == PackageManager.PERMISSION_GRANTED })) {
                    callBack?.onPermissionGranted()
                    lifecycleScope.launch { preference.edit().clear().apply() }
                } else {
                    callBack?.onResultContainsDenied()
                }
                return
            }
        }
    }

    companion object {
        private const val PREFS_PERMISSIONS = "Permissions"
        private const val PERMISSIONS_REQUEST_CODE = 777
        private const val TAG = "PermissionActivity"
    }
}