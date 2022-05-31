package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@Composable
fun TripListBottomNavBar(
    viewModel: TripListViewModel = hiltViewModel()
){
    BottomNavigation() {
        BottomNavigationItem(selected = true, onClick = { /*TODO*/ }, icon = { Unit }, label = { Text(text = "Prueba")})
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = { Unit }, label = { Text(text = "Prueba2")})
    }
}