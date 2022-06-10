package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel

@ExperimentalMaterialApi
@Composable
fun TripPlanningDestinationsList(
    paddingValues: PaddingValues,
    viewModel: DestinationViewModel,
    navigateToDestinationScreen: (Destination) -> Unit,
    destinationsResponse: Response.Success<List<Destination>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (destination in destinationsResponse.data!!) {
            TripPlanningDestinationCard(
                destination = destination,
                navigateToDestinationScreen = navigateToDestinationScreen
            )
        }
    }
}