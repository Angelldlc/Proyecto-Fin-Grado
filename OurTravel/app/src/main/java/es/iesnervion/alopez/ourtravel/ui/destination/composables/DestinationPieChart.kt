package es.iesnervion.alopez.ourtravel.ui.destination.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Square
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.iesnervion.alopez.ourtravel.ui.theme.DeepChampagne
import es.iesnervion.alopez.ourtravel.ui.theme.MediumSpringBud
import es.iesnervion.alopez.ourtravel.ui.theme.PaleCerulean
import es.iesnervion.alopez.ourtravel.ui.theme.PastelPink
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import java.text.NumberFormat
import java.util.*

@Composable
fun DestinationPieChart(accomodationCost: Long?, transportationCost: Long?, foodCost: Long?, tourismCost: Long?) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val symbol = numberFormat.currency?.symbol
    Column() {
        BoxWithConstraints(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            PieChart(
                pieChartData = PieChartData(
                    slices = listOf(
                        PieChartData.Slice(accomodationCost.toString().toFloat(), PaleCerulean),
                        PieChartData.Slice(transportationCost.toString().toFloat(), PastelPink),
                        PieChartData.Slice(foodCost.toString().toFloat(), DeepChampagne),
                        PieChartData.Slice(tourismCost.toString().toFloat(), MediumSpringBud)
                    )
                ),
                modifier = Modifier
                    .size(400.dp, 272.dp)
                    .align(Alignment.Center)
            )
            Text(text = "Total: "+"\n"+((accomodationCost?:0)+(transportationCost?:0)+(foodCost?:0)+(tourismCost?:0)).toString()+" $symbol",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp
            )
        }
        Legend(accomodationCost, transportationCost, foodCost, tourismCost, symbol)
    }
}

@Composable
fun Legend(accomodationCost: Long?, transportationCost: Long?, foodCost: Long?, tourismCost: Long?, symbol: String?) {
    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
        Column(Modifier.padding(16.dp)) {
            LegendItem("Alojamiento: $accomodationCost $symbol", PaleCerulean)
            LegendItem("Transporte: $transportationCost $symbol", PastelPink)
        }
        Column(Modifier.padding(16.dp)) {

            LegendItem("Comida: $foodCost $symbol", DeepChampagne)
            LegendItem("Turismo: $tourismCost $symbol", MediumSpringBud)
        }
    }
}

@Composable
fun LegendItem(text: String, color: Color) {
    Row() {
        Icon(Icons.Filled.Square, contentDescription = "", tint = color)
        Text(text = text, fontSize = 16.sp)
    }
}