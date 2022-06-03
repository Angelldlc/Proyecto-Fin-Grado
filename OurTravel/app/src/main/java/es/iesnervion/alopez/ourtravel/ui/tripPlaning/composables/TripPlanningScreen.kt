package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.TripPlanningViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TripPlanningScreen(
    viewModel: TripPlanningViewModel = hiltViewModel(),
    navigateToTripListScreen: () -> Unit,
    navigateToDestinationScreen: () -> Unit
) {
    Scaffold(
        topBar = {},
        drawerContent = { TripPlanningDrawer() },
        floatingActionButton = { TripPlanningFloatingActionButton() }

    ) { padding ->

    }
}