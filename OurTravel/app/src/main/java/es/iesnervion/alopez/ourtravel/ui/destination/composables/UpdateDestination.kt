package es.iesnervion.alopez.ourtravel.ui.login.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel

@Composable
fun UpdateDestination(
    viewModel: DestinationViewModel = hiltViewModel()
) {
    when(val updateDestinationResponse = viewModel.updateDestinationResponse){
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> print(updateDestinationResponse.e)
        else -> {
            print("Error")}
    }
}