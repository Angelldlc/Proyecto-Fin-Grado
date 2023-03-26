package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.ui.login.composables.ProgressBar
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel

@Composable
fun AddDestination(
    viewModel: DestinationViewModel = hiltViewModel()
) {
    when(val addDestinationResponse = viewModel.addDestinationResponse){
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> print(addDestinationResponse.e)
        else -> {
            print("Error")}
    }
}