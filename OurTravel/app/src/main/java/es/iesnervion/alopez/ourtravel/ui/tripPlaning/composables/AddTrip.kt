package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.ui.login.composables.ProgressBar

@Composable
fun AddTrip(
    viewModel: TripListViewModel = hiltViewModel()
) {
    when(val addTripResponse = viewModel.addTripResponse){
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> print(addTripResponse.e)
        else -> {
            print("Error")}
    }
}