package es.iesnervion.alopez.ourtravel.ui.login.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel

@Composable
fun DeleteDestination(
    viewModel: DestinationViewModel = hiltViewModel()
) {
    when(val deleteDestinationResponse = viewModel.deleteDestinationResponse){
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> print(deleteDestinationResponse.e)
        else -> {
            print("Error")}
    }
}