package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.R
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.domain.repository.Destinations
import es.iesnervion.alopez.ourtravel.ui.destination.composables.AddDestination
import es.iesnervion.alopez.ourtravel.ui.destination.composables.Destinations
import es.iesnervion.alopez.ourtravel.ui.destination.composables.DestinationsContent
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel
import kotlinx.coroutines.Job
import kotlin.math.max
import kotlin.math.min

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TripPlanningScreen(
    parentViewModel: TripListViewModel = hiltViewModel(),
    tripId: String?, trip: TripPlanning,
    viewModel: DestinationViewModel = hiltViewModel(),
    navigateToTripListScreen: () -> Unit,
    navigateToSearchCityScreen: () -> Unit,
    navigateToDestinationScreen: (Destination, String) -> Unit
) {
    val tripPlanning by remember() { mutableStateOf(trip) }

    val nameUpdated: MutableState<Boolean> = remember { mutableStateOf(false) }
    val photoUpdated: MutableState<Boolean> = remember { mutableStateOf(false) }
    val idTrip = trip.id
    BackHandler(onBack = navigateToTripListScreen)
    if (idTrip != null) {
        val destinations  = viewModel.getDestinations(idTrip)
    }
    val destinationsResponse = viewModel.destinationsResponse
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Destinations {
                TripPlanningTopBar(trip, navigateToTripListScreen,
                    { parentViewModel.openDialog() }, parentViewModel::updateTrip, nameUpdated, it
                )
            }
        },
        floatingActionButton = { TripPlanningFloatingActionButton(navigateToSearchCityScreen) }

    ) { padding ->
        if (parentViewModel.openDialog) {
            DeleteAlertDialog(
                tripId = idTrip.toString(),
                openDialog = parentViewModel.openDialog,
                closeDialog = { parentViewModel.closeDialog() },
                deleteTrip = { tripId -> parentViewModel.deleteTrip(tripId) },
                navigateToTripListScreen = navigateToTripListScreen
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        )
        {
            Destinations {
                NotImage(
                    scrollState,
                    it,
                    trip,
                    parentViewModel::updateTrip
                )
                if (it.isNotEmpty()) {
                    photoUpdated.value = true
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Costes:",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                color = Navy
            )

            Destinations {
                TripPlanningPieChart(
                    destinationsResponse = it,
                    tripId = idTrip.toString(),
                    tripName = trip.name.toString(),
                    tripPhoto = trip.photo,
                    viewModel = parentViewModel,
                    nameUpdated
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Destinos:",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                color = Navy
            )

            Destinations(
                destinationsContent = { destinations ->
                    DestinationsContent(
                        padding = padding,
                        destinations = destinations,
                        tripId = idTrip.toString(),
                        navigateToDestinationScreen = navigateToDestinationScreen
                    )
                }
            )
        }
    }
    AddDestination()
    UpdateTrip()
    DeleteTrip()
}

@Composable
fun NotImage(
    scrollState: ScrollState,
    destinations: Destinations,
    trip: TripPlanning,
    updateTrip: (String, String, Timestamp, Timestamp, Long, String?) -> Job,
) {

    val painterModel = try {
        trip.photo = destinations.firstOrNull()?.cityPhoto
        trip.photo
    } catch (e: Exception) {
        ""
    }

    val painter = rememberAsyncImagePainter(model = painterModel)
    val height = 220.dp
    BoxWithConstraints(
        modifier = Modifier
            .height(height)
            .fillMaxSize()
            .background(if (destinations.isEmpty()) Color.LightGray else Color.Transparent)
    ) {
        if (destinations.isEmpty()) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_image_24),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(75.dp)

            )
        } else {
            Image(painter = painter,
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
        }
    }
    LaunchedEffect(painterModel) {
        if (!trip.photo.isNullOrEmpty()) {
            updateTrip(
                trip.id!!,
                trip.name!!,
                trip.startDate!!,
                trip.endDate!!,
                trip.totalCost!!,
                trip.photo
            )
        }
    }
}

@Composable
fun DeleteAlertDialog(
    tripId: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    deleteTrip: (tripId: String) -> Unit,
    navigateToTripListScreen: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { closeDialog() },
            title = { Text(text = "¿Está seguro de que desea eliminar este viaje?") },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = { closeDialog() }
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(enabled = tripId.isNotEmpty()) {},
                        onClick = {
                            deleteTrip(tripId)
                            closeDialog()
                            navigateToTripListScreen()
                        }
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        )
    }
}