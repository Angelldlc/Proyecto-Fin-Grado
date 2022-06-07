package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TripPlanningTopBar(){
    TopAppBar(
        title = { Text(text = "Viaje 1") },
        navigationIcon = {
            IconButton(onClick = {})
            {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp))
            }
        },
        actions = { TripPlanningTopBarActions() },

        )
}

@Composable
fun TripPlanningTopBarActions(){
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        IconButton(onClick = {})
        {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Edit",
                modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = {})
        {
            Icon(
                Icons.Filled.PersonAdd,
                contentDescription = "Share",
                modifier = Modifier.size(24.dp))
        }
    }
}