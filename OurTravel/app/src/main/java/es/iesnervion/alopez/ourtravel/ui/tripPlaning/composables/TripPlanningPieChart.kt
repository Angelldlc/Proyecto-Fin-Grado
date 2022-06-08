package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
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
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import java.text.NumberFormat
import java.util.*

@Composable
fun TripPlanningPieChart(totalAccomodationCost: Long?, totalTransportationCost: Long?, totalFoodCost: Long?, totalTourismCost: Long?) {
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
                    .align(Alignment.Center)
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


