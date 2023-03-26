package es.iesnervion.alopez.ourtravel.ui.destination.composables

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.KFunction2

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DestinationScreen(
    parentViewModel: TripListViewModel = hiltViewModel(),
    destination: Destination?, tripId: String,
    navigateToTripPlanningScreen: (TripPlanning) -> Unit,
    viewModel: DestinationViewModel = hiltViewModel(),
) {
    BackHandler(onBack = {
        parentViewModel.getTrip(tripId) { it?.let { it1 -> navigateToTripPlanningScreen(it1) } }
    })
    val edit = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(model = destination?.cityPhoto ?: "")
    val dest = remember { mutableStateOf(destination) }

    val accomodationCostState = remember { mutableStateOf(destination?.accomodationCosts ?: 0) }
    val transportationCostState = remember { mutableStateOf(destination?.transportationCosts ?: 0) }
    val foodCostState = remember { mutableStateOf(destination?.foodCosts ?: 0) }
    val tourismCostState = remember { mutableStateOf(destination?.tourismCosts ?: 0) }

    val tourismAttractionsState =
        remember { mutableStateOf(destination?.tourismAttractions ?: listOf()) }

    val onAddAtracction = { updatedList: List<String?>? ->
        tourismAttractionsState.value = updatedList ?: listOf()
    }

    Scaffold(
        topBar = {
            destination?.cityName?.let {
                DestinationTopBar(
                    it,
                    tripId,
                    getTrip = parentViewModel::getTrip,
                    navigateToTripPlanningScreen = navigateToTripPlanningScreen
                ) { viewModel.openDialog() }
            }
        },
        floatingActionButton = {
            DestinationFloatingActionButton(
                if (edit.value) {
                    Icons.Filled.Save
                } else {
                    Icons.Filled.Edit
                }, edit,
                tripId,
                dest.value,
                updateDestinationFromFirestore = { viewModel::updateDestinationFromFirestore },
                accomodationCostState = accomodationCostState,
                transportationCostState = transportationCostState,
                foodCostState = foodCostState,
                tourismCostState = tourismCostState,
                tourismAttractionsState = tourismAttractionsState
            )
        }
    ) { padding ->

        if (viewModel.openDialog) {
            DeleteDestinationAlertDialog(
                tripId,
                destination?.id.toString(),
                viewModel.openDialog,
                closeDialog = { viewModel.closeDialog() },
                deleteDestination = { tripId, id -> viewModel.deleteDestination(tripId, id) },
                getTrip = parentViewModel::getTrip,
                navigateToTripPlanningScreen
            )
        }
        Column(
            Modifier
                .verticalScroll(state = scrollState)
                .padding(padding)
                .fillMaxWidth()
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

            CustomizedText(text = "Costes Estimados:")
            DestinationPieChart(
                accomodationCostState.value,
                transportationCostState.value,
                foodCostState.value,
                tourismCostState.value
            )
            Spacer(modifier = Modifier.height(30.dp))
            CostsFields(
                accomodationCostState.value,
                transportationCostState.value,
                foodCostState.value,
                tourismCostState.value,
                edit.value,
                onAccomodationCostChange = { accomodationCostState.value = it },
                onTransportationCostChange = { transportationCostState.value = it },
                onFoodCostChange = { foodCostState.value = it },
                onTourismCostChange = { tourismCostState.value = it }
            )
            CustomizedText(text = "Estancia:")
            TravelStay(edit = edit.value, destination?.travelStay, dest)
            CustomizedText(text = "Lugares de interés:")
            InterestingPlaces(
                edit = edit.value,
                tourismAttractionsState,
                onAddAttraction = onAddAtracction
            )
            CustomizedText(text = "Descripción:")
            Description(edit = edit.value, destination?.description, dest)

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TravelStay(edit: Boolean, travelStay: String?, dest: MutableState<Destination?>) {

    val showMap = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textState = remember() { mutableStateOf(TextFieldValue(travelStay ?: "")) }
        val textStateText = remember() { mutableStateOf(textState.value.text) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textStateText.value,
            onValueChange = { value ->
                textStateText.value = value
                dest.value?.travelStay = value
            },
            enabled = edit,
            label = { Text("Alojamiento", color = Color.Gray) },
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
        Spacer(modifier = Modifier.size(30.dp))
        IconButton(
            onClick = { showMap.value = true },
            enabled = edit,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "",
                tint = if (!edit) Color.LightGray else Navy
            )
        }
    }
    Row { DatePickStart(edit, dest) }
    Row { DatePickEnd(edit, dest) }
}

@Composable
fun Description(edit: Boolean, description: String?, dest: MutableState<Destination?>) {
    val textState = remember() { mutableStateOf(TextFieldValue(description ?: "")) }
    val textStateText = remember() { mutableStateOf(textState.value.text) }

    TextField(
        value = textStateText.value,
        onValueChange = { value ->
            textStateText.value = value
            dest.value?.description = value
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
fun InterestingPlaces(
    edit: Boolean,
    tourismAttractions: MutableState<List<String?>>,
    onAddAttraction: (List<String?>?) -> Unit
) {

    Column {
        tourismAttractions.value.forEachIndexed { index, attraction ->
            Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.width(300.dp),
                        enabled = edit,
                        value = attraction ?: "",
                        onValueChange = { newValue ->
                            val updatedList = tourismAttractions.value.toMutableList()
                            updatedList[index] = newValue
                            onAddAttraction(updatedList)
                        }
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    if (edit) {
                        IconButton(onClick = {
                            val updatedList = tourismAttractions.value.toMutableList()
                            updatedList.removeAt(index)
                            onAddAttraction(updatedList)
                        }, Modifier.size(24.dp)) {
                            Icon(Icons.Outlined.Close, contentDescription = "", tint = Navy)
                        }
                    }
                }
            }
        }
        if (edit) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    val updatedList = tourismAttractions.value.toMutableList()
                    updatedList.add("")
                    onAddAttraction(updatedList)
                }
            ) {
                Text(text = "Añadir")
            }
        }
    }
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
fun CostsFields(
    estimatedAccomodationCost: Long,
    estimatedTransportationCost: Long,
    estimatedFoodCost: Long,
    estimatedTourismCost: Long,
    edit: Boolean,
    onAccomodationCostChange: (Long) -> Unit,
    onTransportationCostChange: (Long) -> Unit,
    onFoodCostChange: (Long) -> Unit,
    onTourismCostChange: (Long) -> Unit
) {
    Row() {
        val textState =
            remember() { mutableStateOf(TextFieldValue(estimatedAccomodationCost.toString())) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                try {
                    onAccomodationCostChange(value.text.toLong())
                } catch (e: Exception) {
                    onAccomodationCostChange(0)
                }
            },
            enabled = edit,
            label = { Text("Gastos de alojamiento", color = Color.Gray) },
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
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                try {
                    onFoodCostChange(value.text.toLong())
                } catch (e: Exception) {
                    onFoodCostChange(0)
                }
            },
            enabled = edit,
            label = { Text("Gastos en comida", color = Color.Gray) },
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
        val textState =
            remember() { mutableStateOf(TextFieldValue(estimatedTransportationCost.toString())) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                try {
                    onTransportationCostChange(value.text.toLong())
                } catch (e: Exception) {
                    onTransportationCostChange(0)
                }
            },
            enabled = edit,
            label = { Text("Gastos en transporte", color = Color.Gray) },
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
        val textState =
            remember() { mutableStateOf(TextFieldValue(estimatedTourismCost.toString())) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
                try {
                    onTourismCostChange(value.text.toLong())
                } catch (e: Exception) {
                    onTourismCostChange(0)
                }
            },
            enabled = edit,
            label = { Text("Gastos en turismo", color = Color.Gray) },
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
    openDialog: Boolean,
    closeDialog: () -> Unit,
    deleteDestination: (tripId: String, id: String) -> Unit,
    getTrip: KFunction2<String, (TripPlanning?) -> Unit, Job>,
    navigateToTripPlanningScreen: (TripPlanning) -> Unit
) {
    var trip by remember { mutableStateOf(TripPlanning()) }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { closeDialog() },
            title = { Text(text = "¿Está seguro de que desea eliminar este destino?") },
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
                            .clickable(enabled = id.isNotEmpty()) {},
                        onClick = {
                            deleteDestination(tripId, id)
                            closeDialog()
                            getTrip(tripId) {
                                trip = it!!
                                if (!trip.id.isNullOrEmpty()) {
                                    navigateToTripPlanningScreen(trip)
                                }
                            }
                        }
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        )
    }
}

/*
@Preview*/
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickStart(edit: Boolean, dest: MutableState<Destination?>) {
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()
    val dateNotFormatted = dest.value?.startDate ?: Date()

    val dateFormatted =
        remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy").format(dateNotFormatted)) }

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf(dateFormatted.value) }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            dest.value?.startDate = try {
                SimpleDateFormat("dd/MM/yyyy").parse(mDate.value)
            } catch (e: Exception) {
                Date()
            }
        }, mYear, mMonth, mDay
    )

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            modifier = Modifier.width(300.dp),
            value = mDate.value,
            onValueChange = {},

            enabled = false,
            label = { Text("Fecha Inicio", color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                disabledTextColor = if (!edit) Color.LightGray else Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = if (!edit) Color.LightGray else Navy,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.size(30.dp))

        IconButton(
            onClick = { mDatePickerDialog.show() },
            enabled = edit,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                Icons.Filled.CalendarMonth,
                contentDescription = "",
                tint = if (!edit) Color.LightGray else Navy
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickEnd(edit: Boolean, dest: MutableState<Destination?>) {
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    val dateNotFormatted = dest.value?.endDate ?: Date()

    val dateFormatted =
        remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy").format(dateNotFormatted)) }

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf(dateFormatted.value) }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            dest.value?.endDate = try {
                SimpleDateFormat("dd/MM/yyyy").parse(mDate.value)
            } catch (e: Exception) {
                Date()
            }
        }, mYear, mMonth, mDay
    )

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            modifier = Modifier.width(300.dp),
            value = mDate.value,
            onValueChange = {},

            enabled = false,
            label = { Text("Fecha Fin", color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Navy,
                disabledTextColor = if (!edit) Color.LightGray else Navy,
                cursorColor = Navy,
                leadingIconColor = Navy,
                trailingIconColor = Navy,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = if (!edit) Color.LightGray else Navy,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.size(30.dp))

        IconButton(
            onClick = { mDatePickerDialog.show() },
            enabled = edit,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                Icons.Filled.CalendarMonth,
                contentDescription = "",
                tint = if (!edit) Color.LightGray else Navy
            )
        }
    }
}
