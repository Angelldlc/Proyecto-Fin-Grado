package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TripPlanningTopBar(
    name: String,
    navigateToTripListScreen: () -> Unit,
    ){
    TopAppBar(
        title = { Text(text = name) },
        navigationIcon = {
            IconButton(onClick = { navigateToTripListScreen() })
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
                Icons.Filled.Share,
                contentDescription = "Share",
                modifier = Modifier.size(24.dp))
        }
    }
}