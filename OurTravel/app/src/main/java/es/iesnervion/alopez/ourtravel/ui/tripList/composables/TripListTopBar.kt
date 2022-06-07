package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.iesnervion.alopez.ourtravel.R

//TODO BORRAR ESTO YA NO SE USA, EN SU LUGAR USAR SEARCHVIEW
@Composable
fun TripListTopBar(){
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = {})
            {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(24.dp))
            }
        },
        actions = { TripListTopBarActions() },

        )
}

@Composable
fun TripListTopBarActions(){
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        IconButton(onClick = {})
        {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Edit",
                modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = {})
        {
            Icon(
                Icons.Filled.Logout,
                contentDescription = "Share",
                modifier = Modifier.size(24.dp))
        }
    }
}