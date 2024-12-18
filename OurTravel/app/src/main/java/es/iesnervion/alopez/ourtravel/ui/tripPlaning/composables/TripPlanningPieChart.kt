package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Square
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import es.iesnervion.alopez.ourtravel.ui.theme.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.repository.Destinations
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import java.text.NumberFormat
import java.util.*

@Composable

fun TripPlanningPieChart(
    destinationsResponse: Destinations,
    tripId: String,
    tripName: String,
    tripPhoto: String?,
    viewModel: TripListViewModel = hiltViewModel(),
    nameUpdated: MutableState<Boolean>
) {
    val costs = calculateTotalCosts(destinationsResponse)
    val totalAccomodationCost = costs[0]
    val totalTransportationCost = costs[1]
    val totalFoodCost = costs[2]
    val totalTourismCost = costs[3]
    updateTripFields(destinationsResponse, tripId, tripName, tripPhoto, viewModel, nameUpdated)

    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val symbol = numberFormat.currency?.symbol
    Column() {
        BoxWithConstraints(modifier = Modifier.align(CenterHorizontally)) {
            PieChart(
                pieChartData = PieChartData(
                    slices = listOf(
                        PieChartData.Slice(
                            totalAccomodationCost.toString().toFloat(),
                            PaleCerulean
                        ),
                        PieChartData.Slice(
                            totalTransportationCost.toString().toFloat(),
                            PastelPink
                        ),
                        PieChartData.Slice(totalFoodCost.toString().toFloat(), DeepChampagne),
                        PieChartData.Slice(totalTourismCost.toString().toFloat(), MediumSpringBud)
                    )
                ),
                modifier = Modifier
                    .size(400.dp, 272.dp)
                    .align(Center)
            )
            Text(
                text = "Total: " + "\n" + ((totalAccomodationCost ?: 0) + (totalTransportationCost
                    ?: 0) + (totalFoodCost ?: 0) + (totalTourismCost ?: 0)).toString() + " $symbol",
                modifier = Modifier.align(Center),
                fontSize = 24.sp
            )
        }
        Legend(
            totalAccomodationCost,
            totalTransportationCost,
            totalFoodCost,
            totalTourismCost,
            symbol
        )
    }
}

@Composable
fun Legend(
    totalAccomodationCost: Long?,
    totalTransportationCost: Long?,
    totalFoodCost: Long?,
    totalTourismCost: Long?,
    symbol: String?
) {
    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
        Column(Modifier.padding(16.dp)) {
            LegendItem("Alojamiento: $totalAccomodationCost $symbol", PaleCerulean)
            LegendItem("Transporte: $totalTransportationCost $symbol", PastelPink)
        }
        Column(Modifier.padding(16.dp)) {

            LegendItem("Comida: $totalFoodCost $symbol", DeepChampagne)
            LegendItem("Turismo: $totalTourismCost $symbol", MediumSpringBud)
        }
    }
}

@Composable
fun LegendItem(text: String, color: Color) {
    Row() {
        Icon(
            Icons.Filled.Square,
            contentDescription = "",
            tint = color,
            modifier = Modifier.size(25.dp)
        )
        Text(text = text, fontSize = 16.sp)
    }
}

fun calculateTotalCosts(
    destinationsList: Destinations?
): MutableList<Long?> {
    val costs = mutableListOf<Long?>(0, 0, 0, 0)
    if (destinationsList != null) {
        for (destination in destinationsList) {
            costs[0] = destination.accomodationCosts?.let { costs[0]?.plus(it) }
            costs[1] = destination.transportationCosts?.let { costs[1]?.plus(it) }
            costs[2] = destination.foodCosts?.let { costs[2]?.plus(it) }
            costs[3] = destination.tourismCosts?.let { costs[3]?.plus(it) }


        }
    }
    return costs
}

fun updateTripFields(
    destinations: Destinations?,
    tripId: String,
    tripName: String,
    tripPhoto: String?,
    viewModel: TripListViewModel,
    nameUpdated: MutableState<Boolean>
) {
    if (destinations != null) {
        if (destinations.isNotEmpty()) {
            val costs = calculateTotalCosts(destinations)
            val totalCosts = try {
                (costs[0] ?: 0) + (costs[1] ?: 0) + (costs[2] ?: 0) + (costs[3] ?: 0)
            } catch (e: Exception) {
                0
            }
            var startDate = destinations[0].startDate
            var endDate = destinations[0].endDate
            for (destination in destinations) {
                startDate =
                    if (startDate?.before(destination.startDate) == true) startDate else destination.startDate
                endDate =
                    if (endDate?.after(destination.endDate) == true) endDate else destination.endDate
            }
            if (!nameUpdated.value) {
                viewModel.updateTrip(
                    tripId,
                    tripName,
                    Timestamp(startDate!!),
                    Timestamp(endDate!!),
                    totalCosts,
                    tripPhoto
                )
            }
        }
    }
}
