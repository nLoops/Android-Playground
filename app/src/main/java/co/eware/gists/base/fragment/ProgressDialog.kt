package co.eware.gists.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import co.eware.gists.databinding.FragmentLoadingDialogBinding

/**
 * Created by Ahmed Ibrahim on 22,December,2020
 */
class ProgressDialog : DialogFragment() {
    private var _binding: FragmentLoadingDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoadingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}