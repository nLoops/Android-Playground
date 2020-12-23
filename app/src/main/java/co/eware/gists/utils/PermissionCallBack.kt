package co.eware.gists.utils

/**
 * Created by Ahmed Ibrahim on 22,December,2020
 */
interface PermissionCallBack {
    fun onPermissionGranted()

    fun onResultContainsDenied()
}