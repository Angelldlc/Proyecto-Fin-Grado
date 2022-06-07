package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@Preview
@Composable
fun TripListBottomNavBar(
//    viewModel: TripListViewModel = hiltViewModel()
){
    BottomNavigation() {
        BottomNavigationItem(icon = { Icon(Icons.Filled.Event, contentDescription = "") }, selected = true, onClick = { /*TODO*/ }, label = { Text(text = "Próximos Viajes")})
        BottomNavigationItem(icon = { Icon(Icons.Filled.History, contentDescription = "") },selected = false, onClick = { /*TODO*/ }, label = { Text(text = "Viajes Finalizados")})
    }
}