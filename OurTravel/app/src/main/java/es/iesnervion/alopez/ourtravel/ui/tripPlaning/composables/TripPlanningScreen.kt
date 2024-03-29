package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.R
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel
import kotlin.math.max
import kotlin.math.min

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TripPlanningScreen(
    parentViewModel: TripListViewModel = hiltViewModel(),
    tripId: String?, trip: TripPlanning, city: City?,
    viewModel: DestinationViewModel = hiltViewModel(),
    navigateToTripListScreen: () -> Unit,
    navigateToSearchCityScreen: () -> Unit,
    navigateToDestinationScreen: (Destination, String) -> Unit
) {
    var idTrip = trip.id
    BackHandler(onBack = navigateToTripListScreen)
    if(idTrip.isNullOrEmpty() || idTrip.isNullOrBlank()) {
        parentViewModel.addTrip(
            trip.name ?: "",
            Timestamp.now(),
            Timestamp.now(),
            0,
            trip.photo ?: "",
            Timestamp.now()
        )
        idTrip = /*parentViewModel.isTripAddedState.value.toString()*/if (parentViewModel.isTripAddedState.value is Response.Success) {
            (parentViewModel.lastTripInsertedId.value as Response.Success<Boolean>).id
        } else { "" }
       /* idTrip = parentViewModel.getLastTripInsertedId()*/ //TODO intentar recoger el valor aqui para abajo no trabajar con el estado
        navigateToDestinationScreen(
            Destination(cityPhoto = city?.photo, cityName = city?.name), idTrip ?: "" /*if (parentViewModel.lastTripInsertedId.value is Response.Success) {
                (parentViewModel.lastTripInsertedId.value as Response.Success<String>).data.toString()
            } else {
                ""
            }*/
        )
    } else {
        viewModel.getDestinations(idTrip)
    }
    val openDialog = remember { mutableStateOf(false) }
    val destinationsResponse = viewModel.destinationsState.value
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(
        model = (if (trip.photo.isNullOrEmpty() || trip.photo!!.isBlank()) {
            NotImage()
        } else trip.photo)
    )
    Scaffold(
        topBar = { TripPlanningTopBar(trip.name ?: "", navigateToTripListScreen, openDialog) },
        floatingActionButton = { TripPlanningFloatingActionButton(navigateToSearchCityScreen) }

    ) { padding ->
        if(openDialog.value){
            DeleteAlertDialog(idTrip.toString(), openDialog, parentViewModel, navigateToTripListScreen)
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            val height = 220.dp
            Image(path,
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
                    TripPlanningPieChart(destinationsResponse, idTrip.toString(), parentViewModel)
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Destinations:",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 24.sp,
                        color = Navy
                    )

                    TripPlanningDestinationsList(
                        idTrip.toString(),
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
            .clickable {  }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_image_24),
            contentDescription = "",
            modifier = Modifier.align(Alignment.TopCenter)

        )
    }
}

@Composable
fun DeleteAlertDialog(tripId: String,
                      openDialog: MutableState<Boolean>,
                      viewModelTripList: TripListViewModel = hiltViewModel(),
                      navigateToTripListScreen: () -> Unit) {
    if(openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Are you sure you want to delete this trip?") },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = { openDialog.value = false }
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        modifier = Modifier.padding(16.dp).clickable(enabled = tripId.isNotEmpty()){},
                        onClick = {
                            viewModelTripList.deleteTrip(tripId)
                            if(viewModelTripList.isTripDeletedState.value is Response.Success || viewModelTripList.isTripDeletedState.value is Response.Loading){
                                navigateToTripListScreen()
                            }else{
                                openDialog.value = false
                            }
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        )
    }
}