package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import kotlinx.coroutines.Job
import kotlin.reflect.KFunction6

@Composable
fun TripPlanningTopBar(
    trip: TripPlanning,
    navigateToTripListScreen: () -> Unit,
    openDialog: () -> Unit,
    updateTripPlanning: KFunction6<String, String, Timestamp, Timestamp, Long, String?, Job>,
    nameUpdated: MutableState<Boolean>,

    ){
    var nameState by remember { mutableStateOf(trip.name) }
    TopAppBar(
        title = { /*Text(text = name)*/
                TextField(
                    value = nameState!!,
                    onValueChange = { nameState = it },
                    singleLine = true,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                },
        navigationIcon = {
            IconButton(onClick = { navigateToTripListScreen() })
            {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = { TripPlanningTopBarActions(openDialog) },

        )

    LaunchedEffect(nameState){
        if(nameState != trip.name){
            updateTripPlanning(trip.id.toString(), nameState.toString(), trip.startDate!!, trip.endDate!!, trip.totalCost ?: 0, trip.photo)
            nameUpdated.value = true
        }
    }
}

@Composable
fun TripPlanningTopBarActions(openDialog: () -> Unit){
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        IconButton(onClick = {}, enabled = false)
        {
            Icon(
                Icons.Filled.Share,
                contentDescription = "Share",
                modifier = Modifier.size(24.dp)
            )
        }

        IconButton(onClick = { openDialog() })
        {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier.size(24.dp))
        }
    }
}
