package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TripPlanningFloatingActionButton(
    navigateToSearchCityScreen: () -> Unit
){
    FloatingActionButton(onClick = { navigateToSearchCityScreen() }) { //TODO cambiar Dialog por Navigation a vista TripPlanningScreen edit true
        Icon(imageVector = Icons.Filled.Add, tint = Color.White , contentDescription = "Add Trip Planning")
    }
}