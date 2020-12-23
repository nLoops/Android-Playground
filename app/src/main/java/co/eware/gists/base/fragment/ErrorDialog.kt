package co.eware.gists.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import co.eware.gists.databinding.FragmentErrorDialogBinding

/**
 * Created by Ahmed Ibrahim on 22,December,2020
 */
class ErrorDialog(var title: String = "", var message: String = "") : DialogFragment() {

    private var _binding: FragmentErrorDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentErrorDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            tvDialogTitle.text = title
            tvDialogMessage.text = message
            bntDialogOk.setOnClickListener { dialog?.dismiss() }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}