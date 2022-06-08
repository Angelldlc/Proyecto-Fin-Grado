package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.TripPlanningViewModel

@ExperimentalMaterialApi
@Composable
fun TripPlanningDestinationsList(
    paddingValues: PaddingValues,
    viewModel: TripPlanningViewModel,
    navigateToDestinationScreen: (Destination) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val destinationsResponse = viewModel.destinationsState.value) {
            is Response.Loading -> {}
            is Response.Success -> {
                for (destination in destinationsResponse.data!!) {
                    TripPlanningDestinationCard(
                        destination = destination,
                        navigateToDestinationScreen = navigateToDestinationScreen
                    )
                }
            }
            is Response.Error -> Log.d("OurTravel", destinationsResponse.message)
        }
    }
}