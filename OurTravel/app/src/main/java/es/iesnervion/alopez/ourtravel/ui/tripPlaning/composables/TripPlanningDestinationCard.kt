package es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.domain.model.Destination

@ExperimentalMaterialApi
@Composable
fun TripPlanningDestinationCard(
    destination: Destination, tripId: String,
    navigateToDestinationScreen: (Destination, String) -> Unit
) {
    val path = rememberAsyncImagePainter(model = destination.cityPhoto)
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(16.dp), elevation = 8.dp, shape = RoundedCornerShape(8.dp),
        onClick = {
            navigateToDestinationScreen(destination, tripId)
        }) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Image(
                path,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier
                    .size(240.dp, 60.dp)
                    .clip(RoundedCornerShape(35.dp, 0.dp, 0.dp, 0.dp))
                    .background(Color.White)
                    .align(Alignment.BottomEnd)
            ) {
                destination.cityName?.let {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp), text = it, fontSize = 18.sp, color = Color.Black
                    )
                }
            }
        }
    }
}