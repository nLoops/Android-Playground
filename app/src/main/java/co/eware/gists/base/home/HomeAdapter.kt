package co.eware.gists.base.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.eware.gists.databinding.HomeListItemBinding
import co.eware.gists.utils.ModelDiffCallback

/**
 * Created by Ahmed Ibrahim on 28,December,2020
 */
class HomeAdapter(private val viewModel: HomeViewModel) :
        ListAdapter<ListCard, HomeAdapter.ViewHolder>(ModelDiffCallback<ListCard>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    class ViewHolder private constructor(val binding: HomeListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: HomeViewModel, item: ListCard) {
            binding.viewModel = viewModel
            binding.card = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }
}

