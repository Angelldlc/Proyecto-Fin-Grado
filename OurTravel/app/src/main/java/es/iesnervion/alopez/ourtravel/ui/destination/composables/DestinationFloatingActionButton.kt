package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import kotlinx.coroutines.Job
import java.util.*
import kotlin.reflect.KFunction12

@Composable
fun DestinationFloatingActionButton(
    icon: ImageVector,
    edit: MutableState<Boolean>,
    tripId: String,
    destination: Destination?,
    updateDestinationFromFirestore: () -> KFunction12<String, String, City, String, Long, Long, Long, Long, Date, Date, String, List<String>, Job>,
    accomodationCostState: MutableState<Long>,
    transportationCostState: MutableState<Long>,
    foodCostState: MutableState<Long>,
    tourismCostState: MutableState<Long>,
    tourismAttractionsState: MutableState<List<String?>>
    ) {
    FloatingActionButton(onClick = {
        edit.value = !edit.value
        if (!edit.value) {
            updateDestinationFromFirestore()(
                tripId,
                destination?.id.toString(),
                City(destination?.cityName, destination?.cityPhoto),
                destination?.description.toString(),
                accomodationCostState.value,
                transportationCostState.value,
                foodCostState.value,
                tourismCostState.value,
                destination?.startDate ?: Date(),
                destination?.endDate ?: Date(),
                destination?.travelStay.toString(),
                tourismAttractionsState.value as List<String>
            )
        }

    }) {
        Icon(icon, contentDescription = "", tint = Color.White)
    }
}