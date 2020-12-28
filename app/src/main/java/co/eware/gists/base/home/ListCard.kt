package co.eware.gists.base.home

import androidx.navigation.NavDirections
import co.eware.gists.utils.BaseDataModel

/**
 * Created by Ahmed Ibrahim on 28,December,2020
 */
data class ListCard(
        override val id: String,
        val title: String,
        val clickedDestination: NavDirections?
) : BaseDataModel

val featuresList = listOf(
        ListCard("1", "Design Patterns", HomeFragmentDirections.actionHomeFragmentToPermissionFragment())
)