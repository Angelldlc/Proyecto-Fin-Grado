package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.domain.repository.Destinations
import es.iesnervion.alopez.ourtravel.ui.login.composables.ProgressBar
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel

@Composable
fun Destinations(
    viewModel: DestinationViewModel = hiltViewModel(),
    destinationsContent: @Composable (destinations: Destinations) -> Unit
){
    when(val destinationsResponse = viewModel.destinationsResponse){
        is Loading -> ProgressBar()
        is Success -> destinationsResponse.data?.let { destinationsContent(it) }
        is Failure -> print(destinationsResponse.e)
        else -> {}
    }
}