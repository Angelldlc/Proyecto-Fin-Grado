package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.TripPlanningPieChart
import kotlin.math.max
import kotlin.math.min

@Composable
fun DestinationScreen(
    name: String,
    navigateToTripPlanningScreen: () -> Unit
){
    val edit = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val path = rememberAsyncImagePainter(model = "")
    
    Scaffold(
        topBar = { DestinationTopBar(name, navigateToTripPlanningScreen) },
        floatingActionButton = { DestinationFloatingActionButton(
           if (edit.value){ Icons.Filled.Edit } else { Icons.Filled.Save } 
        ) }
    ) { padding ->  
        Column(
            Modifier
                .verticalScroll(state = scrollState)
                .padding(padding)
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
            Text(
                text = "Costs:",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                color = Navy
            )
            DestinationPieChart(0,0,0,0)//TODO cambiar valores
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Travel Stay:",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                color = Navy
            )
            TravelStay(edit = edit.value)
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Travel:",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                color = Navy
            )
            Travel(edit = edit.value)
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Interesting Places:",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                color = Navy
            )
            InterestingPlaces(edit = edit.value)
        }
    }
}

@Composable
fun TravelStay(edit: Boolean){
    Row() {
        TextField(value = "", onValueChange = { }, enabled = edit)
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.PinDrop, contentDescription = "")
        }
    }
}

@Composable
fun Travel(edit: Boolean){
    TextField(value = "", onValueChange = {}, enabled = edit, modifier = Modifier.fillMaxWidth().height(400.dp))
}

@Composable
fun InterestingPlaces(edit: Boolean){
    //TODO Ver LazyColumn adding new elements by button
}