package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DestinationFloatingActionButton(icon: ImageVector, edit: MutableState<Boolean>) {
    FloatingActionButton(onClick = {edit.value = !edit.value}) {
        Icon(icon, contentDescription = "", tint = Color.White)
    }
}