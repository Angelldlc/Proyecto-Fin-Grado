package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Square
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import es.iesnervion.alopez.ourtravel.ui.theme.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import java.text.NumberFormat
import java.util.*
import kotlin.math.cos

@Composable
fun TripPlanningPieChart(destinationsResponse: Response.Success<List<Destination>>) {
    val costs = calculateTotalCosts(destinationsResponse.data)
    val totalAccomodationCost = costs[0]
    val totalTransportationCost = costs[1]
    val totalFoodCost = costs[2]
    val totalTourismCost = costs[3]

    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val symbol = numberFormat.currency?.symbol
    Column() {
        BoxWithConstraints(modifier = Modifier.align(CenterHorizontally)) {
            PieChart(
                pieChartData = PieChartData(
                    slices = listOf(
                        PieChartData.Slice(totalAccomodationCost.toString().toFloat(), PaleCerulean),
                        PieChartData.Slice(totalTransportationCost.toString().toFloat(), PastelPink),
                        PieChartData.Slice(totalFoodCost.toString().toFloat(), DeepChampagne),
                        PieChartData.Slice(totalTourismCost.toString().toFloat(), MediumSpringBud)
                    )
                ),
                modifier = Modifier
                    .size(400.dp, 272.dp)
                    .align(Center)
            )
            Text(text = "Total: "+"\n"+((totalAccomodationCost?:0)+(totalTransportationCost?:0)+(totalFoodCost?:0)+(totalTourismCost?:0)).toString()+" $symbol",
                modifier = Modifier.align(Center),
                fontSize = 24.sp
                )
        }
        Legend(totalAccomodationCost, totalTransportationCost, totalFoodCost, totalTourismCost, symbol)

    }


}

@Composable
fun Legend(totalAccomodationCost: Long?, totalTransportationCost: Long?, totalFoodCost: Long?, totalTourismCost: Long?, symbol: String?) {
    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
        Column(Modifier.padding(16.dp)) {
            LegendItem("Accomodation: $totalAccomodationCost $symbol", PaleCerulean)
            LegendItem("Transportation: $totalTransportationCost $symbol", PastelPink)
        }
        Column(Modifier.padding(16.dp)) {

            LegendItem("Food: $totalFoodCost $symbol", DeepChampagne)
            LegendItem("Tourism: $totalTourismCost $symbol", MediumSpringBud)
        }
    }
//    Column(Modifier.padding(16.dp)) {
//            LegendItem("Accomodation: $totalAccomodationCost $symbol", PaleCerulean)
//            LegendItem("Transportation: $totalTransportationCost $symbol", PastelPink)
//            LegendItem("Food: $totalFoodCost $symbol", DeepChampagne)
//            LegendItem("Tourism: $totalTourismCost $symbol", MediumSpringBud)
//    }
}

@Composable
fun LegendItem(text: String, color: Color) {
    Row() {
        Icon(Icons.Filled.Square, contentDescription = "", tint = color/*, modifier = Modifier.size(25.dp)*/)
        Text(text = text, fontSize = 16.sp)
    }
}

fun calculateTotalCosts(
    destinationsList: List<Destination>?
): MutableList<Long?> {
    var costs = mutableListOf<Long?>(0,0,0,0)
    if (destinationsList != null) {
        for (destination in destinationsList){
            costs[0] = destination.accomodationCosts?.let { costs[0]?.plus(it) }
            costs[1] = destination.transportationCosts?.let { costs[1]?.plus(it) }
            costs[2] = destination.foodCosts?.let { costs[2]?.plus(it) }
            costs[3] = destination.tourismCosts?.let { costs[3]?.plus(it) }


        }
    }
    return costs
}
