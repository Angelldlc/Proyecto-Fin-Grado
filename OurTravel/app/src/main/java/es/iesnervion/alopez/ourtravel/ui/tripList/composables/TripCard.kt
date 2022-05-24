package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@ExperimentalMaterialApi
@Composable
fun TripCard(
    trip: TripPlanning,
    viewmodel: TripListViewModel = hiltViewModel()
){
    val path = rememberAsyncImagePainter(model = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/GoldenGateBridge-001.jpg/268px-GoldenGateBridge-001.jpg")
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(16.dp), elevation = 8.dp ,shape = RoundedCornerShape(8.dp),
        onClick = {

    }) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()){
            Image(path, contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillWidth)
            Box(modifier = Modifier
                .size(240.dp, 60.dp)
                .clip(RoundedCornerShape(35.dp, 0.dp, 0.dp, 0.dp))
                .background(Color.White)
                .align(Alignment.BottomEnd)){
                trip.name?.let {
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