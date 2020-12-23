package co.eware.gists.base

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.eware.gists.R
import co.eware.gists.base.activity.TopAppActivity
import co.eware.gists.base.fragment.BaseBindingFragment
import co.eware.gists.base.fragment.BaseViewModelFragment
import co.eware.gists.databinding.FragmentHomeBinding
import co.eware.gists.utils.PermissionCallBack
import java.util.*

/**
 * Created by Ahmed Ibrahim on 21,December,2020
 */
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnExample.setOnClickListener {
                val callback = object : PermissionCallBack {
                    override fun onPermissionGranted() {
                        showSnackBar("Granted!")
                    }

                    override fun onResultContainsDenied() {
                        showSnackBar("Denied!")
                    }
                }
                val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)

                if ((activity as TopAppActivity).checkPermission(permissions, callback, R.string.permission_required)) {
                    showSnackBar("Already Granted!")
                }
            }
        }
    }
}