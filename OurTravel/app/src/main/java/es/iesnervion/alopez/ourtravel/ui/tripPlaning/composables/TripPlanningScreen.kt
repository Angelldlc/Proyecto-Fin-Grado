package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.R
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel
import kotlin.math.max
import kotlin.math.min

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Preview
@Composable
fun TripPlanningScreen(
    tripId: String, name: String, photo: String,
    viewModel: DestinationViewModel = hiltViewModel(),
    navigateToTripListScreen: () -> Unit,
    navigateToSearchCityScreen: () -> Unit,
    navigateToDestinationScreen: (Destination) -> Unit
) {
    viewModel.getDestinations(tripId)
    val destinationsResponse = viewModel.destinationsState.value
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(
        model = (if (photo.isEmpty() || photo.isBlank()) {
            NotImage()
        } else photo)
    ) //TODO Cambiar por llamada a API
    Scaffold(
        topBar = { TripPlanningTopBar(name, navigateToTripListScreen) },
        drawerContent = { TripPlanningDrawer() },
        floatingActionButton = { TripPlanningFloatingActionButton(navigateToSearchCityScreen) }

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
                    Text(
                        text = "Costs:",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 24.sp,
                        color = Navy
                    )
                    TripPlanningPieChart(destinationsResponse)
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Destinations:",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 24.sp,
                        color = Navy
                    )

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

@Composable
fun NotImage() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .clickable { TODO("Insertar Foto") }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_image_24),
            contentDescription = "",
            modifier = Modifier.align(Alignment.TopCenter)

        )
    }
}
