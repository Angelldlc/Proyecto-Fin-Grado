package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DestinationTopBar(
    name: String,
    navigateToTripPlanningScreen: () -> Unit,
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
        })
}