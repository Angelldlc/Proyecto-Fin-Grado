package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.TripPlanningViewModel
import kotlin.math.max
import kotlin.math.min

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Preview
@Composable
fun TripPlanningScreen(
    tripId: String, name: String, photo: String,
    viewModel: TripPlanningViewModel = hiltViewModel(),
    navigateToTripListScreen: () -> Unit,
    navigateToDestinationScreen: (Destination) -> Unit
) {
    viewModel.getDestinations(tripId)
    val destinationsResponse = viewModel.destinationsState.value
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(model = { photo.ifEmpty { "" } }) //TODO Cambiar por llamada a API
    Scaffold(
        topBar = { TripPlanningTopBar(name, navigateToTripListScreen) },
        drawerContent = { TripPlanningDrawer() },
        floatingActionButton = { TripPlanningFloatingActionButton() }

    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            val height = 220.dp
            Image(path,
//                painter = painterResource( /*viewmodel.photo*/),
                contentDescription = "Banner Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .graphicsLayer {
                        alpha = min(
                            1f,
                            max(
                                0.0f,
                                1 - (scrollState.value / ((height.value * 2) + (height.value / 1.5f)))
                            )
                        )
                    }
            )
            when (destinationsResponse) {
                is Response.Loading -> {}
                is Response.Success -> {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = "Costs:", modifier = Modifier.padding(16.dp))
                    TripPlanningPieChart(destinationsResponse)
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = "Destinations:", modifier = Modifier.padding(16.dp))

                    TripPlanningDestinationsList(
                        paddingValues = padding,
                        viewModel = viewModel,
                        navigateToDestinationScreen = navigateToDestinationScreen,
                        destinationsResponse
                    )
                }
                is Response.Error -> Log.d("OurTravel", destinationsResponse.message)
            }
        }
    }
}