package es.iesnervion.alopez.ourtravel.ui.login.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.repository.Destinations
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.TripPlanningDestinationCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DestinationsContent(
    padding: PaddingValues,
    destinations: Destinations,
    tripId: String,
    navigateToDestinationScreen: (Destination, String) -> Unit
    ) {
    LazyColumn(modifier = Modifier.padding(padding)) {
        items(destinations.size) { destination ->
            TripPlanningDestinationCard(
                destination = destinations[destination],
                tripId = tripId,
                navigateToDestinationScreen = navigateToDestinationScreen
            )
        }
    }
}