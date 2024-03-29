package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@Composable
fun TripListFloatingActionButton(
    navigateToNewTripPlanningScreen: () -> Unit
){
    FloatingActionButton(onClick = { navigateToNewTripPlanningScreen() }) {
        Icon(imageVector = Icons.Filled.Add, tint = Color.White , contentDescription = "Add Trip Planning")
    }
}