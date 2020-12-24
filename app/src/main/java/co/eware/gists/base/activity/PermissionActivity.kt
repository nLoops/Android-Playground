package co.eware.gists.base.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import co.eware.gists.R
import co.eware.gists.utils.PermissionCallBack
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Ahmed Ibrahim on 21,December,2020
 *
 * A Utility helps to ask for permissions and handling it's lifecycle and different scenarios
 *
 * By using [checkPermission] which it returns [Boolean]
 *
 * if(true) then you fire your actions
 * else the utility will let System ask the user to grant permissions and show PopUps if needed
 * and the callbacks will handle the responses
 *
 */
abstract class PermissionActivity : BaseActivity() {

    private var callBack: PermissionCallBack? = null

    // Holds title and message of popup dialog
    // First Title which could be nullable
    // Second will be the message
    private var dialogComponents: Pair<Int?, Int>? = null

    data class Permission(val value: String, val state: PermissionState)

    enum class PermissionState(val access: Boolean) {
        DENIED(false),
        GRANTED(true),
        UNKNOWN(false)
    }

    private val preference: SharedPreferences by lazy {
        getSharedPreferences(
                PREFS_PERMISSIONS,
                Context.MODE_PRIVATE
        )
    }


    /**
     * @param permissions arrayOf [Manifest.permission]
     * @param permissionsCallBack implementation of [PermissionCallBack]
     * @param dialogTitleAndMessage [Pair] of two int resources first for popUp TITLE and the second
     * to MESSAGE, the first one could be null or you can add default general one and modify it as
     * needed.
     *
     * @return [Boolean] if true you already granted the permissions else it will start the cycle
     * and callbacks will hold the responses.
     */
    fun checkPermission(
            permissions: Array<String>,
            permissionsCallBack: PermissionCallBack,
            dialogTitleAndMessage: Pair<Int?, Int>
    ): Boolean {
        callBack = permissionsCallBack
        dialogComponents = dialogTitleAndMessage

        return checkGranted(permissions)
    }

    private fun checkGranted(permissions: Array<String>): Boolean {
        // Check if there's any left permission need to be grant
        val permissionList = permissions.map { p -> Permission(p, getPermissionState(p)) }
                .filter { s -> !s.state.access }

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
            access = ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
            ) == PackageManager.PERMISSION_GRANTED
    )

    private fun convertToPermissionState(code: PermissionState, access: Boolean): PermissionState {
        if (access) return PermissionState.GRANTED
        return when (code) {
            PermissionState.UNKNOWN -> PermissionState.UNKNOWN
            else -> PermissionState.DENIED
        }
    }

    private fun ask(permissions: Array<Permission>) {
        // Check if any of permissions should to show rationale
        val shouldShowRationale = permissions.any {
            it.state == PermissionState.UNKNOWN && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    it.value
            )
        }
        val shouldGoToAppSettings = permissions.any {
            it.state == PermissionState.DENIED && !ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    it.value
            )
        }
        val secondTryAfterDenied = permissions.any {
            it.state == PermissionState.DENIED && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    it.value
            )
        }

        permissions.forEach {
            Timber.i(
                    "State: ${it.state} and shouldShowRationale: ${
                        ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                it.value
                        )
                    }"
            )
        }

        when {
            shouldShowRationale || secondTryAfterDenied -> showPermissionRationaleDialog(permissions)
            shouldGoToAppSettings -> showPermissionRationaleDialog(launchSettings = true)
            else -> requestPermissions(permissions)
        }
    }

    private fun showPermissionRationaleDialog(
            permissions: Array<Permission> = emptyArray(),
            launchSettings: Boolean = false
    ) {
        dialogComponents?.let {
            showAlertDialog(title = it.first?.let { title -> getString(title) },
                    message = getString(it.second), positiveActionText = getString(R.string.button_ok),
                    negativeText = getString(android.R.string.cancel),
                    positiveAction = {
                        if (launchSettings) openAppSettings() else requestPermissions(
                                permissions
                        )
                    },
                    negativeAction = {
                        lifecycleScope.launch {
                            permissions.forEach { p ->
                                preference.edit().putString(p.value, PermissionState.DENIED.name)
                                        .apply()
                            }
                        }
                    })
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
        ActivityCompat.requestPermissions(
                this,
                permissions.map { it.value }.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults.all { s -> s == PackageManager.PERMISSION_GRANTED })) {
                    callBack?.onPermissionGranted()
                    lifecycleScope.launch { preference.edit().clear().apply() }
                } else {
                    callBack?.onResultContainsDenied()
                    permissions.forEach { s ->
                        lifecycleScope.launch {
                            preference.edit().putString(s, PermissionState.DENIED.name).apply()
                        }
                    }
                }
                return
            }
        }
    }

    companion object {
        private const val PREFS_PERMISSIONS = "Permissions"
        private const val PERMISSIONS_REQUEST_CODE = 777
    }
}