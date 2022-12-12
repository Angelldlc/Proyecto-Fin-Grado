package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.login.LoginViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.BottomNavState
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@ExperimentalMaterialApi
@Composable
fun TripListScreen(parentViewModel: LoginViewModel = hiltViewModel(),
    viewModel: LoginViewModel = hiltViewModel(),
    viewModelTripList: TripListViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToNewTripPlanningScreen: () -> Unit,
    navigateToTripPlanningScreen: (TripPlanning) -> Unit
) {
    val textState = remember(
        navigateToTripPlanningScreen,
        navigateToNewTripPlanningScreen,
        navigateToLoginScreen
    ) { mutableStateOf(TextFieldValue("")) }
    val bottomNavState = remember(
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
        floatingActionButton = { TripListFloatingActionButton(navigateToNewTripPlanningScreen = navigateToNewTripPlanningScreen) }
    ) { padding ->
        Column() {
            TripList(
                padding = padding,
                textState,
                navigateToTripPlanningScreen = navigateToTripPlanningScreen,
                bottomNavState = bottomNavState
            )
        }
    }
}

