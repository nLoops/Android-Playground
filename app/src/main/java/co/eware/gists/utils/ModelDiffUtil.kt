package co.eware.gists.utils

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Ahmed Ibrahim on 28,December,2020
 */
class ModelDiffCallback<T : BaseDataModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

}