package co.eware.gists.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.eware.gists.databinding.FragmentHomeBinding

/**
 * Created by Ahmed Ibrahim on 21,December,2020
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            btnExample.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Hello World!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return binding.root
    }

    // TODO: create base activity and fragment with utilities

}