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
    tourismAttractionsState: MutableState<List<String?>>,
    /*updateTrip: KFunction6<String, String, Timestamp, Timestamp, Long, String?, Job>,
    getTrip: KFunction2<String, (TripPlanning?) -> Unit, Job>,*/

    ) {
    FloatingActionButton(onClick = {
        edit.value = !edit.value
        if (!edit.value) {
            updateDestinationFromFirestore()(
                tripId,
                destination?.id.toString(),
                City(destination?.cityName, destination?.cityPhoto),
                destination?.description.toString(),
//                destination?.accomodationCosts ?: 0,
//                destination?.transportationCosts ?: 0,
//                destination?.foodCosts ?: 0,
//                destination?.tourismCosts ?: 0,
                accomodationCostState.value,
                transportationCostState.value,
                foodCostState.value,
                tourismCostState.value,
                destination?.startDate ?: Date(),
                destination?.endDate ?: Date(),
                destination?.travelStay.toString(),
                tourismAttractionsState.value as List<String>
//                (destination?.tourismAttractions ?: emptyList<String>()) as List<String>
            )

            /*getTrip(tripId) { trip ->
                updateTrip(
                    tripId,
                    trip?.name.toString(),
                    (if (trip?.startDate!! < Timestamp(destination?.startDate!!)) trip.startDate else Timestamp(
                        destination.startDate!!
                    ))!!,
                    (if (trip.endDate!! > Timestamp(destination.endDate!!)) trip.endDate else Timestamp(
                        destination.endDate!!
                    ))!!,
                    trip.totalCost ?: 0,
                    trip.photo.toString()
                )
            }*/
        }

    }) {
        Icon(icon, contentDescription = "", tint = Color.White)
    }
}