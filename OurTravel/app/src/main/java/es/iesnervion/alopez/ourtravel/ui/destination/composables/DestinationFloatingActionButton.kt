package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel
import java.util.*

@Composable
fun DestinationFloatingActionButton(icon: ImageVector, edit: MutableState<Boolean>, tripId:String, destination: Destination?, viewModel: DestinationViewModel = hiltViewModel()) {
    FloatingActionButton(onClick = {
        edit.value = !edit.value
        if(!edit.value){
            viewModel.updateDestinationFromFirestore(
                tripId,
                destination?.id.toString(),
                City(destination?.cityName, destination?.cityPhoto),
                destination?.description.toString(),
                destination?.accomodationCosts ?: 0,
                destination?.transportationCosts ?: 0,
                destination?.foodCosts ?: 0,
                destination?.tourismCosts ?: 0,
                destination?.startDate ?: Date(),
                destination?.endDate ?: Date(),
                destination?.travelStay.toString(),
                (destination?.tourismAttractions ?: emptyList<String>()) as List<String>)
        }
    }) {
        Icon(icon, contentDescription = "", tint = Color.White)
    }
}