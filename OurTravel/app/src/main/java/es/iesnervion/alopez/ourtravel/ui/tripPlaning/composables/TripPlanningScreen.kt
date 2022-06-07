package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.R
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.TripPlanningPieChart
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.TripPlanningViewModel
import kotlin.math.max
import kotlin.math.min

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Preview
@Composable
fun TripPlanningScreen(
    tripId: String,
    viewModel: TripPlanningViewModel = hiltViewModel(),
    navigateToTripListScreen: () -> Unit,
    navigateToDestinationScreen: (Destination) -> Unit
) {
    viewModel.getDestinations(tripId)
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS66MKD0mUL1dtAsmJRtbiDj3rd-kDR9acoNA&usqp=CAU") //TODO Cambiar por llamada a API
    Scaffold(
        topBar = { TripPlanningTopBar() },
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
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Costs:", modifier = Modifier.padding(16.dp))
            TripPlanningPieChart()
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Destinations:", modifier = Modifier.padding(16.dp))
            TripPlanningDestinationsList(paddingValues = padding, navigateToDestinationScreen = navigateToDestinationScreen)
        }

    }
}