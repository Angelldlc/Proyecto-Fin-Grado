package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.repository.Destinations
import es.iesnervion.alopez.ourtravel.domain.repository.DestinationsResponse
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel

@ExperimentalMaterialApi
@Composable
fun TripPlanningDestinationsList(
    tripId: String,
    paddingValues: PaddingValues,
    /*viewModel: DestinationViewModel,*/
    navigateToDestinationScreen: (Destination, String) -> Unit,
    destinations: Destinations,
    destinationsResponse: DestinationsResponse /*Response.Success<List<Destination>>*/
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = destinations.size
        ) { destination ->
            TripPlanningDestinationCard(
                destination = destinations[destination],
                tripId = tripId,
                navigateToDestinationScreen = navigateToDestinationScreen
            )
        }

        /*for (destination in destinationsResponse.data!!) { //TODO Mirar que pasa
            TripPlanningDestinationCard(
                destination = destination, tripId,
                navigateToDestinationScreen = navigateToDestinationScreen
            )
        }*/
    }
}