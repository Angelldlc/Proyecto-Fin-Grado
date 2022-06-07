package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.tripList.BottomNavState
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@ExperimentalMaterialApi
@Composable
fun TripListScreen(viewModel: TripListViewModel = hiltViewModel(), navigateToLoginScreen: () -> Unit, navigateToNewTripPlanningScreen: () -> Unit, navigateToTripPlanningScreen: (String) -> Unit ) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val bottomNavState = remember { mutableStateOf(BottomNavState.PENDANT)}
    val state = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = state,
        topBar = { TripListSearchView(textState = textState, scope, state) },
        bottomBar = { TripListBottomNavBar(bottomNavState) },
        drawerContent = { TripListDrawer(Modifier.padding(16.dp), scope, state) },
        floatingActionButton = { TripListFloatingActionButton(navigateToNewTripPlanningScreen = navigateToNewTripPlanningScreen) }
    ) { padding ->
        Column() {
//            TripListSearchView(textState)
            TripList(padding = padding, textState, navigateToTripPlanningScreen = navigateToTripPlanningScreen, bottomNavState = bottomNavState)
        }
    }
}

