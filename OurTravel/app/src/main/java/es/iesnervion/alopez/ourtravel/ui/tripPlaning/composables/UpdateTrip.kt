package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.ui.login.composables.ProgressBar
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@Composable
fun UpdateTrip(
    viewModel: TripListViewModel = hiltViewModel()
) {
    when(val updateTripResponse = viewModel.updateTripResponse){
        is Response.Loading -> ProgressBar()
        is Response.Success -> Unit
        is Response.Failure -> print(updateTripResponse.e)
        else -> {
            print("Error")}
    }
}