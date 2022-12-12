package es.iesnervion.alopez.ourtravel.ui.destination.composables

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.DeleteAlertDialog
import kotlinx.coroutines.delay
import okhttp3.internal.notify
import okhttp3.internal.wait
import java.time.Instant
import java.util.*
import kotlin.math.max
import kotlin.math.min

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DestinationScreen(
    parentViewModel: TripListViewModel = hiltViewModel(),
    destination: Destination?, tripId: String,
    navigateToTripPlanningScreen: () -> Unit,
    viewModel: DestinationViewModel = hiltViewModel(),
) {
    BackHandler(onBack = navigateToTripPlanningScreen)
    val openDialog = remember { mutableStateOf(false) }
    val edit = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(model = destination?.cityPhoto ?: "")
    val dest = remember { mutableStateOf(destination) }

    Scaffold(
        topBar = {
            destination?.cityName?.let {
                DestinationTopBar(
                    it,
                    navigateToTripPlanningScreen,
                    openDialog
                )
            }
        },
        floatingActionButton = {
            DestinationFloatingActionButton(
                if (edit.value) {
                    Icons.Filled.Save
                } else {
                    Icons.Filled.Edit
                }, edit, tripId, dest.value
            )

        }
    ) { padding ->
        if(openDialog.value){
            DeleteDestinationAlertDialog(tripId, destination?.id.toString() , openDialog, viewModel, navigateToTripPlanningScreen)
        }
        Column(
            Modifier
                .verticalScroll(state = scrollState)
                .padding(padding)
        ) {
            val height = 220.dp
            Image(
                path,
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

            CustomizedText(text = "Estimated Costs:")
            DestinationPieChart(
                destination?.accomodationCosts ?: 0,
                destination?.transportationCosts ?: 0,
                destination?.foodCosts ?: 0,
                destination?.tourismCosts ?: 0
            )
            Spacer(modifier = Modifier.height(30.dp))
            CostsFields(
                destination?.accomodationCosts ?: 0,
                destination?.transportationCosts ?: 0,
                destination?.foodCosts ?: 0,
                destination?.tourismCosts ?: 0,
                edit.value, dest
            )
            CustomizedText(text = "Travel Stay:")
            TravelStay(edit = edit.value, destination?.travelStay, destination?.startDate, destination?.endDate, dest)
            CustomizedText(text = "Description:")
            Description(edit = edit.value, destination?.description, dest)
            CustomizedText(text = "Interesting Places:")
            InterestingPlaces(edit = edit.value, destination?.tourismAttractions, dest)


        }
    }
    if (tripId.isNotEmpty() && tripId.isNotBlank() && destination?.id.isNullOrEmpty() && destination?.id.isNullOrBlank()) {
        viewModel.addDestination(
            tripId, destination?.id.toString(),
            City(destination?.cityName, destination?.cityPhoto),
            destination?.description.toString(),
            destination?.accomodationCosts ?: 0,
            destination?.transportationCosts ?: 0,
            destination?.foodCosts ?: 0,
            destination?.tourismCosts ?: 0,
            destination?.startDate ?: Date(),
            destination?.endDate ?: Date(),
            destination?.travelStay.toString(),
            (destination?.tourismAttractions ?: emptyList<String>()) as List<String>
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TravelStay(edit: Boolean, travelStay: String?, startDate: Date?, endDate: Date?, dest: MutableState<Destination?>) {

    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue(travelStay ?: "")) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                dest.value?.travelStay = value.toString()
            },
            enabled = edit,
            placeholder = { Text("Estancia", color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray
            )
        )
        IconButton(onClick = { /*TODO*/ }, enabled = edit) {
            Icon(Icons.Filled.LocationOn, contentDescription = "", tint = Navy)
        }
    }
    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue(startDate.toString())) }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                dest.value?.startDate = Date.from(Instant.parse(value.text))
            },
            enabled = edit,
            placeholder = { Text("Fecha Inicio", color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray
            )
        )
        IconButton(onClick = { /*TODO*/ }, enabled = edit) {
            Icon(Icons.Filled.CalendarMonth, contentDescription = "", tint = Navy)
        }
    }
    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue(endDate.toString())) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                dest.value?.endDate = Date.from(Instant.parse(value.text))
            },
            enabled = edit,
            placeholder = { Text("Fecha Fin", color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray
            )
        )
        IconButton(onClick = { /*TODO*/ }, enabled = edit) {
            Icon(Icons.Filled.CalendarMonth, contentDescription = "", tint = Navy)
        }
    }
}

@Composable
fun Description(edit: Boolean, description: String?, dest: MutableState<Destination?>) {
    val textState = remember() { mutableStateOf(TextFieldValue(description ?: "")) }

    TextField(
        value = textState.value,
        onValueChange = { value ->
            textState.value = value
            dest.value?.description = value.toString()
        },
        enabled = edit,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),

        colors = TextFieldDefaults.textFieldColors(
            textColor = Navy,
            cursorColor = Navy,
            leadingIconColor = Navy,
            trailingIconColor = Navy,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.LightGray,
            disabledIndicatorColor = Color.Transparent,
            placeholderColor = Color.Gray
        )
    )
}

@Composable
fun InterestingPlaces(edit: Boolean, tourismAttractions: List<String?>?, dest: MutableState<Destination?>) {
    val textState = remember() { mutableStateOf(TextFieldValue(tourismAttractions.toString())) }

    TextField(
        value = textState.value,
        onValueChange = { value ->
            textState.value = value
            dest.value?.tourismAttractions = listOf(value.text)
        },
        enabled = edit,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),

        colors = TextFieldDefaults.textFieldColors(
            textColor = Navy,
            cursorColor = Navy,
            leadingIconColor = Navy,
            trailingIconColor = Navy,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.LightGray,
            disabledIndicatorColor = Color.Transparent,
            placeholderColor = Color.Gray
        )
    )
}

@Composable
fun CustomizedText(text: String) {
    Spacer(modifier = Modifier.height(30.dp))
    Text(
        text = text,
        modifier = Modifier.padding(16.dp),
        fontSize = 24.sp,
        color = Navy
    )
}

@Composable
fun CostsFields(estimatedAccomodationCost: Long, estimatedTransportationCost: Long, estimatedFoodCost: Long, estimatedTourismCost: Long, edit: Boolean, dest: MutableState<Destination?>) {
    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue(estimatedAccomodationCost.toString())) }

        TextField(
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                dest.value?.accomodationCosts = value.text.toLong()
            },
            enabled = edit,
            placeholder = { Text("Gastos de estancia", color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),

            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray

            )
        )
    }
    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue(estimatedFoodCost.toString())) }

        TextField(
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                dest.value?.foodCosts = value.text.toLong()
            },
            enabled = edit,
            placeholder = { Text("Gastos en comida", color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray

            )
        )
    }
    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue(estimatedTransportationCost.toString())) }

        TextField(
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                dest.value?.transportationCosts = value.text.toLong()
            },
            enabled = edit,
            placeholder = { Text("Gastos en transporte", color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray

            )
        )
    }
    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue(estimatedTourismCost.toString())) }

        TextField(
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                dest.value?.tourismCosts = value.text.toLong()
            },
            enabled = edit,
            placeholder = { Text("Gastos en turismo", color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray

            )
        )
    }
}

@Composable
fun DeleteDestinationAlertDialog(
    tripId: String,
    id: String,
    openDialog: MutableState<Boolean>,
    viewModel: DestinationViewModel = hiltViewModel(),
    navigateToTripPlanningScreen: () -> Unit
) {
    if(openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Are you sure you want to delete this destination?") },
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
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(enabled = id.isNotEmpty()) {},
                        onClick = {
                            viewModel.deleteDestination(tripId, id)
                            if(viewModel.isDestinationDeletedState.value is Response.Success || viewModel.isDestinationDeletedState.value is Response.Loading){
                                navigateToTripPlanningScreen()
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