package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@Composable
fun TripListFloatingActionButton(
    viewmodel: TripListViewModel = hiltViewModel()
){
    FloatingActionButton(onClick = { viewmodel.openDialogState.value = true }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Trip Planning")
    }
}