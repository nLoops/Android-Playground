package co.eware.gists.base.fragment

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Created by Ahmed Ibrahim on 23,December,2020
 */
abstract class BaseViewModelFragment<VB : ViewBinding, VM : ViewModel> : BaseBindingFragment<VB>() {
    protected abstract val viewModel: VM
}