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
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DestinationTopBar(
    name: String,
    navigateToTripPlanningScreen: () -> Unit,
    openDialog: MutableState<Boolean>
) {
    TopAppBar(
        title = { Text(text = name) },
        navigationIcon = {
            IconButton(onClick = { navigateToTripPlanningScreen() })
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
}

@Composable
fun DestinationTopBarActions(openDialog: MutableState<Boolean>){
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        IconButton(onClick = { openDialog.value = true})
        {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier.size(24.dp))
        }
    }
}