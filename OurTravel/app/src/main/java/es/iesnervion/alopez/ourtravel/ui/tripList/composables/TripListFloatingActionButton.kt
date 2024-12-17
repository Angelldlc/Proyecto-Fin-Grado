package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import kotlinx.coroutines.Job
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

@Composable
fun TripListFloatingActionButton(
    addTrip: (name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String, creationDate: Timestamp) -> Unit,
    navigateToTripPlanningScreen: (newTrip: TripPlanning) -> Unit,
    getTrip: KFunction2<String, (TripPlanning?) -> Unit, Job>,
    getLastTripInsertedId: KFunction1<(String) -> Unit, Unit>,
) {
    var lastTripInsertedId by remember { mutableStateOf("") }
    var lastTripInserted by remember { mutableStateOf(TripPlanning("")) }
    FloatingActionButton(onClick = {
        addTrip("New Trip", Timestamp.now(), Timestamp.now(), 0, "", Timestamp.now())
        getLastTripInsertedId { lastTripInsertedId = it }
    }) {
        Icon(
            imageVector = Icons.Filled.Add,
            tint = Color.White,
            contentDescription = "Add Trip Planning"
        )
    }

    LaunchedEffect(lastTripInsertedId) {
        if (lastTripInsertedId.isNotEmpty()) {
            getTrip(lastTripInsertedId) {
                lastTripInserted = it!!
            }
        }
    }

    LaunchedEffect(lastTripInserted) {
        if (!lastTripInserted.id.isNullOrEmpty()) {
            navigateToTripPlanningScreen(lastTripInserted)
        }
    }
}