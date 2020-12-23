package co.eware.gists.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.eware.gists.base.fragment.BaseBindingFragment
import co.eware.gists.databinding.FragmentHomeBinding

/**
 * Created by Ahmed Ibrahim on 21,December,2020
 */
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnExample.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToObserverFragment()
                findNavController().navigate(action)
            }
        }
    }
}