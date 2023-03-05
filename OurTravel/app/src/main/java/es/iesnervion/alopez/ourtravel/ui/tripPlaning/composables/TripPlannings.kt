package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.domain.repository.TripPlannings
import es.iesnervion.alopez.ourtravel.ui.login.composables.ProgressBar
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@Composable
fun TripPlannings(
    viewModel: TripListViewModel = hiltViewModel(),
    tripPlanningsContent: @Composable (tripPlannings: TripPlannings) -> Unit
) {
    when(val tripPlanningsResponse = viewModel.tripsResponse){
        is Loading -> ProgressBar()
        is Success -> tripPlanningsResponse.data?.let { tripPlanningsContent(it) }
        is Failure -> print(tripPlanningsResponse.e)
        else -> {
            print("Error")}
    }
}