package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.repository.Destinations
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.TripPlanningDestinationCard

@ExperimentalMaterialApi
@Composable
fun DestinationsContent(
    padding: PaddingValues,
    destinations: Destinations,
    tripId: String,
    navigateToDestinationScreen: (Destination, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .height((destinations.size * 200).dp)
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(destinations.size) { destination ->
            TripPlanningDestinationCard(
                destination = destinations[destination],
                tripId = tripId,
                navigateToDestinationScreen = navigateToDestinationScreen
            )
        }
    }
}