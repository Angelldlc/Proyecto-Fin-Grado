package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@ExperimentalMaterialApi
@Composable
fun TripListScreen(/*viewModel: TripListViewModel = hiltViewModel()*/) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Scaffold(
        bottomBar = { TripListBottomNavBar() },
        drawerContent = { },
        floatingActionButton = { TripListFloatingActionButton() }
    ) { padding ->
        Column() {
            TripListSearchView(textState)
            TripList(padding = padding, textState)
        }
    }
}

