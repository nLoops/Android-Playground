package co.eware.gists.categories.ui.permissions

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.eware.gists.R
import co.eware.gists.base.activity.TopAppActivity
import co.eware.gists.base.fragment.BaseBindingFragment
import co.eware.gists.databinding.FragmentPermissionsBinding
import co.eware.gists.utils.PermissionCallBack

/**
 * Created by Ahmed Ibrahim on 23,December,2020
 */
class PermissionFragment : BaseBindingFragment<FragmentPermissionsBinding>() {

    override fun getViewBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentPermissionsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnRequestPermission.setOnClickListener {
                val callbacks = object : PermissionCallBack {
                    override fun onPermissionGranted() {
                        showSnackBar("Callback -> Permissions Granted!!")
                    }

                    override fun onResultContainsDenied() {
                        showSnackBar("Callback -> Permissions Denied")
                    }

                }
                val permissions =
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                val dialog = Pair(null, R.string.permission_message)

                if ((activity as TopAppActivity).checkPermission(
                                permissions,
                                callbacks,
                                dialog
                        )
                ) {
                    showSnackBar("App Already Grant Permissions.")
                }
            }
        }
    }

}