package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
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
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel
import kotlin.math.max
import kotlin.math.min

@Composable
fun DestinationScreen(
//    name: String,
    destination: Destination?,
    navigateToTripPlanningScreen: () -> Unit,
    viewModel: DestinationViewModel = hiltViewModel()
) {
    val edit = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(model = destination?.cityPhoto ?: "")
//    val textState = remember() { mutableStateOf(TextFieldValue("")) }

//    val destination = viewModel.getDestinations(name)

    Scaffold(
        topBar = {
            destination?.cityName?.let {
                DestinationTopBar(
                    it,
                    navigateToTripPlanningScreen
                )
            }
        },
        floatingActionButton = {
            DestinationFloatingActionButton(
                if (edit.value) {
                    Icons.Filled.Save
                } else {
                    Icons.Filled.Edit
                }, edit, viewModel
            )
        }
    ) { padding ->
        Column(
            Modifier
                .verticalScroll(state = scrollState)
                .padding(padding)
        ) {
            val height = 220.dp
            Image(
                path,
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

            CustomizedText(text = "Estimated Costs:")
            DestinationPieChart(
                destination?.accomodationCosts ?: 0,
                destination?.transportationCosts ?: 0,
                destination?.foodCosts ?: 0,
                destination?.tourismCosts ?: 0
            )
            Spacer(modifier = Modifier.height(30.dp))
            CostsFields(
                destination?.transportationCosts ?: 0,
                destination?.foodCosts ?: 0,
                edit.value
            )
//            Spacer(modifier = Modifier.height(30.dp))
            CustomizedText(text = "Travel Stay:")
            TravelStay(edit = edit.value, destination?.travelStay)
//            Spacer(modifier = Modifier.height(30.dp))
            CustomizedText(text = "Description:")
            Description(edit = edit.value, destination?.description)
//            Spacer(modifier = Modifier.height(30.dp))
            CustomizedText(text = "Interesting Places:")
            InterestingPlaces(edit = edit.value, destination?.tourismAttractions)


        }
    }
}

@Composable
fun TravelStay(edit: Boolean, travelStay: String?) {

    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue("")) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
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
        val textState = remember() { mutableStateOf(TextFieldValue("")) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
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
        val textState = remember() { mutableStateOf(TextFieldValue("")) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
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
fun Description(edit: Boolean, description: String?) {
    val textState = remember() { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = textState.value,
        onValueChange = { value ->
            textState.value = value
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
fun InterestingPlaces(edit: Boolean, tourismAttractions: List<String?>?) {
    val textState = remember() { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = textState.value,
        onValueChange = { value ->
            textState.value = value
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
fun CostsFields(estimatedTransportationCost: Long, estimatedFoodCost: Long, edit: Boolean) {
    Row() {
        val textState = remember() { mutableStateOf(TextFieldValue("")) }

        TextField(
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
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
        val textState = remember() { mutableStateOf(TextFieldValue("")) }

        TextField(
            value = textState.value,
            onValueChange = { value ->
                textState.value = value
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
}