package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import kotlinx.coroutines.Job
import kotlin.reflect.KFunction2

@Composable
fun DestinationTopBar(
    name: String,
    tripId: String,
    navigateToTripPlanningScreen: (TripPlanning) -> Unit,
    getTrip: KFunction2< String, (TripPlanning?) -> Unit, Job>,
    openDialog: () -> Unit
) {
    var trip by remember { mutableStateOf(TripPlanning("")) }

    TopAppBar(
        title = { Text(text = name) },
        navigationIcon = {
            IconButton(onClick = {
                getTrip(tripId){ trip = it!! }
            })
            {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = { DestinationTopBarActions(openDialog) },

        )

    LaunchedEffect(trip){
        if (!trip.id.isNullOrEmpty()) {
            navigateToTripPlanningScreen(trip)
        }

    }
}

@Composable
fun DestinationTopBarActions(openDialog: () -> Unit){
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        IconButton(onClick = { openDialog() })
        {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier.size(24.dp))
        }
    }
}