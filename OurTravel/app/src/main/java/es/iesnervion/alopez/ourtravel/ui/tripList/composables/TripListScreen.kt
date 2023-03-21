package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.login.LoginViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.BottomNavState
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.AddTrip

@ExperimentalMaterialApi
@Composable
fun TripListScreen(parentViewModel: LoginViewModel = hiltViewModel(),
    viewModel: LoginViewModel = hiltViewModel(),
    viewModelTripList: TripListViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToNewTripPlanningScreen: () -> Unit,
    navigateToTripPlanningScreen: (TripPlanning) -> Unit
) {
//    val lastTripInsertedId = remember() { mutableStateOf(viewModelTripList.lastTripInsertedId) }
    /*val lastTripId = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModelTripList.getLastTripInsertedId { tripId ->
            lastTripId.value = tripId
        }
    }*/

    val textState = remember(
        navigateToTripPlanningScreen,
        navigateToNewTripPlanningScreen,
        navigateToLoginScreen
    ) { mutableStateOf(TextFieldValue("")) }
    val addTripResponse = remember() { mutableStateOf(viewModelTripList.addTripResponse) }
    /*val bottomNavState = rememberSaveable(
        navigateToTripPlanningScreen,
        navigateToNewTripPlanningScreen,
        navigateToLoginScreen
    ) { mutableStateOf(BottomNavState.PENDING) }*/
    val navController = rememberNavController()
    var bottomNavState = remember(
        navigateToTripPlanningScreen,
        navigateToNewTripPlanningScreen,
        navigateToLoginScreen
    ) { mutableStateOf(BottomNavState.PENDING) }


    val state = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = state,
        topBar = { TripListSearchView(textState = textState, scope, state) },
        bottomBar = { TripListBottomNavBar(bottomNavState) },
        drawerContent = { TripListDrawer(viewModel, navigateToLoginScreen) },
        floatingActionButton = { TripListFloatingActionButton(
            addTrip = { name, startDate, endDate, totalCost, photo, creationDate -> viewModelTripList.addTrip(name, startDate, endDate, totalCost, photo, creationDate) },
            navigateToTripPlanningScreen = { newTrip -> navigateToTripPlanningScreen(newTrip) },
            getLastTripInsertedId = viewModelTripList::getLastTripInsertedId,
            getTrip = viewModelTripList::getTrip
             ) }
    ) { padding ->
        Column() {
            TripList(
                padding = padding,
                textState,
                navigateToTripPlanningScreen = navigateToTripPlanningScreen,
                bottomNavState = bottomNavState
            )
        }
        AddTrip()
    }
}

