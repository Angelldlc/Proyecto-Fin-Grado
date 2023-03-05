package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TripPlanningTopBar(
    name: String,
    navigateToTripListScreen: () -> Unit,
    openDialog: () -> Unit
    ){
    TopAppBar(
        title = { Text(text = name) },
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
