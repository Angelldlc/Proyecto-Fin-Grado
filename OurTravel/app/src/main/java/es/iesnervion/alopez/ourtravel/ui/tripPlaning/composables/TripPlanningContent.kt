package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.domain.repository.TripPlannings
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import kotlin.math.max
import kotlin.math.min


@Composable
fun TripPlanningContent(
    padding: PaddingValues,
    scrollState: ScrollState,
    path: AsyncImagePainter,
    tripPlannings: TripPlannings,
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        val height = 220.dp
        Image(path,
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
    }
}