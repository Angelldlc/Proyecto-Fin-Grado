package es.iesnervion.alopez.ourtravel.ui.tripPlaning

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData

@Composable
fun TripPlanningPieChart() {
    PieChart(
        pieChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice(
                    200f,
                    Color.Companion.Green
                ),
                PieChartData.Slice(50f, Color.Companion.Blue),
                PieChartData.Slice(50f, Color.Companion.Red)
            )
        ), modifier = Modifier.size(400.dp, 272.dp)
    )
}