package co.eware.gists.base.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.eware.gists.base.fragment.BaseBindingFragment
import co.eware.gists.base.fragment.BaseViewModelFragment
import co.eware.gists.databinding.FragmentHomeBinding
import co.eware.gists.utils.EventObserver

/**
 * Created by Ahmed Ibrahim on 21,December,2020
 */
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: HomeAdapter

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUI()
        binding.run {
            adapter = HomeAdapter(viewModel)
            adapter.submitList(featuresList)
            homeRecyclerView.adapter = adapter
        }
    }

    private fun observeUI() {
        viewModel.navTo.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(it)
        })
    }


}