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
import es.iesnervion.alopez.ourtravel.ui.login.composables.AddDestination
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
    tripId: String?, trip: TripPlanning,/* city: City?,*/
    viewModel: DestinationViewModel = hiltViewModel(),
    /*getDestinations: (String) -> Unit,*/ //TODO Mirar getDestinations() y viewmodel.destinationState.value
    navigateToTripListScreen: () -> Unit,
    navigateToSearchCityScreen: () -> Unit,
    navigateToDestinationScreen: (Destination, String) -> Unit
) {
    val tripPlanning by remember() { mutableStateOf(trip) }

    val nameUpdated: MutableState<Boolean> = remember { mutableStateOf(false) }
    val photoUpdated: MutableState<Boolean> = remember { mutableStateOf(false) }
    val idTrip = /*if (trip == null) tripId else trip.id*/ trip.id ////////////////////////
    BackHandler(onBack = navigateToTripListScreen)
    /*if(idTrip.isNullOrEmpty() || idTrip.isNullOrBlank()) {
        parentViewModel.addTrip(
            trip.name ?: "",
            Timestamp.now(),
            Timestamp.now(),
            0,
            trip.photo ?: "",
            Timestamp.now()
        )
        idTrip = *//*parentViewModel.isTripAddedState.value.toString()*//*if (parentViewModel.isTripAddedState.value is Response.Success) {
            (parentViewModel.isTripAddedState.value as Response.Success<Boolean>).id
        } else { "" }
       *//* idTrip = parentViewModel.getLastTripInsertedId()*//* //TODO intentar recoger el valor aqui para abajo no trabajar con el estado
        navigateToDestinationScreen(
            Destination(cityPhoto = city?.photo, cityName = city?.name), idTrip ?: "" *//*if (parentViewModel.lastTripInsertedId.value is Response.Success) {
                (parentViewModel.lastTripInsertedId.value as Response.Success<String>).data.toString()
            } else {
                ""
            }*//*
        )
    } else {
        viewModel.getDestinations(idTrip)
    }*/
    /*val openDialog = remember { mutableStateOf(false) }*/
    if (idTrip != null) {
        val destinations  = viewModel.getDestinations(idTrip) //TODO getDestinations: (tripId: String) -> Unit
    }
    val destinationsResponse = viewModel.destinationsResponse //TODO cambiar

    val imageUri = remember { mutableStateOf/*<Uri?>*/(trip.photo?.toUri()) }//TODO He cambiado remember por saveable
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(
        model = (/*if (trip.photo.isNullOrEmpty() || trip.photo!!.isBlank()) { ///////////////
            NotImage(onImageSelected = { parentViewModel.updateTrip(trip.id!!, trip.name!!, trip.startDate!!, trip.endDate!!, trip.totalCost!!, it.toString())})
        } else */trip.photo)
    )
    Scaffold(
        topBar = { Destinations { TripPlanningTopBar(trip/*.name ?: ""*/, navigateToTripListScreen,
            { parentViewModel.openDialog() }, parentViewModel::updateTrip, nameUpdated, it) }  }, /////////
        floatingActionButton = { TripPlanningFloatingActionButton(navigateToSearchCityScreen) }

    ) { padding ->
        if(parentViewModel.openDialog){
            DeleteAlertDialog(
                tripId = idTrip.toString(),
                openDialog = parentViewModel.openDialog,
                closeDialog = { parentViewModel.closeDialog() },
                deleteTrip = { tripId -> parentViewModel.deleteTrip(tripId) },
                navigateToTripListScreen = navigateToTripListScreen
            )
        }
        Column (
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize())
        {
            val height = 220.dp
            /*if (trip.photo.isNullOrEmpty() || trip.photo!!.isBlank()) { ///////////////
                NotImage(onImageSelected = { parentViewModel.updateTrip(trip.id!!, trip.name!!, trip.startDate!!, trip.endDate!!, trip.totalCost!!, it.toString())}, trip, path)
            } else {
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
            }*/


            Destinations {
                NotImage(
                    /*onImageSelected = {
                        imageUri.value = it
                        trip.photo = it.toString()
                        parentViewModel.updateTrip(
                            trip.id!!,
                            trip.name!!,
                            trip.startDate!!,
                            trip.endDate!!,
                            trip.totalCost!!,
                            it.toString()
                        )
                    }, *//*trip, path,*//*
                    imageUri,*/
                    scrollState,
                    it,
                    trip,
                    parentViewModel::updateTrip
                )
                if(it.isNotEmpty()){
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
                    nameUpdated)
            }
            /*TripPlanningPieChart(destinations, idTrip.toString(), parentViewModel)*/

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Destinos:",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                color = Navy
            )

            Destinations (
                destinationsContent = { destinations ->
                    DestinationsContent(
                        padding = padding,
                        destinations = destinations,
                        tripId = idTrip.toString(),
                        navigateToDestinationScreen = navigateToDestinationScreen
                    )
                }
            )



            /*TripPlanningDestinationsList(
                idTrip.toString(),
                paddingValues = padding,
                viewModel = viewModel,
                navigateToDestinationScreen = navigateToDestinationScreen,
                destinationsResponse

            )*/
        }


    }

        /*TripPlannings( tripPlanningsContent = { trips ->
            TripPlanningContent(
                padding = padding,
                scrollState = scrollState,
                path = path,
                tripPlannings = trips
            )
            DeleteAlertDialog(
                tripId = tripId.toString(),
                openDialog = parentViewModel.openDialog,
                closeDialog = { parentViewModel.closeDialog() },
                deleteTrip = { tripId -> parentViewModel.deleteTrip(tripId) },
                navigateToTripListScreen = navigateToTripListScreen
            )
            }

        )*/

        /*TripPlanningPieChart(destinationsResponse, idTrip.toString(), parentViewModel)*/
//        Spacer(modifier = Modifier.height(272.dp))
//        Spacer(modifier = Modifier.height(30.dp))
//        Text(
//            text = "Destinations:",
//            modifier = Modifier.padding(16.dp),
//            fontSize = 24.sp,
//            color = Navy
//        )

        /*Destinations(
        TripPlanningDestinationsList(
            idTrip.toString(),
            paddingValues = padding,
            *//*viewModel = viewModel,*//*
            navigateToDestinationScreen = navigateToDestinationScreen,
            ,
            viewModel.destinationsResponse//TODO arreglar

        )
        )*/


    /*
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
        }*/
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
){

    val painterModel = try {
        trip.photo = destinations.firstOrNull()?.cityPhoto
        trip.photo
    } catch (e: Exception) {""}

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
    LaunchedEffect(painterModel){
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

//@Composable
//fun NotImage(
//    onImageSelected: (Uri) -> Unit,
//    /*trip: TripPlanning,
//    path: AsyncImagePainter,*/
//    imageUri: MutableState<Uri?>,
//    scrollState: ScrollState
//) {
//    val context = LocalContext.current
//    val height = 220.dp
//    val painter = rememberAsyncImagePainter(imageUri.value)//TODO Probar sin el Async
////    val painter = remember(imageUri.value){ rememberAsyncImagePainter(imageUri.value) }
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        if (uri != null) {
//            imageUri.value = uri
//            onImageSelected(uri)
//        }
//    }
//    BoxWithConstraints(
//        modifier = Modifier
//            .height(height)
//            .fillMaxSize()
//            .background(if (imageUri.value == null) Color.LightGray else Color.Transparent)
//            .clickable(onClick = { launcher.launch("image/*") })
//    ) {
//        if (imageUri.value == null) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_baseline_image_24),
//                contentDescription = "",
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .size(75.dp)
//
//            )
//        } else {
//
//            Image(painter = painter,
//                contentDescription = "Banner Image",
//                contentScale = ContentScale.FillWidth,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(height)
//                    .graphicsLayer {
//                        alpha = min(
//                            1f,
//                            max(
//                                0.0f,
//                                1 - (scrollState.value / ((height.value * 2) + (height.value / 1.5f)))
//                            )
//                        )
//                    }
//            )
//        }
//    }
//}

@Composable
fun DeleteAlertDialog(
    tripId: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    deleteTrip: (tripId: String) -> Unit,
    /*viewModelTripList: TripListViewModel = hiltViewModel(),*/
    navigateToTripListScreen: () -> Unit
) {
    if(openDialog) {
        AlertDialog(
            onDismissRequest = { closeDialog() },
            title = { Text(text = "¿Está seguro de que desea eliminar este viaje?"/*"Are you sure you want to delete this trip?"*/) },
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
                            /*if(viewModelTripList.isTripDeletedState.value is Response.Success || viewModelTripList.isTripDeletedState.value is Response.Loading){
                                navigateToTripListScreen()
                            }else{
                                openDialog.value = false
                            }*/
                        }
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        )
    }
}