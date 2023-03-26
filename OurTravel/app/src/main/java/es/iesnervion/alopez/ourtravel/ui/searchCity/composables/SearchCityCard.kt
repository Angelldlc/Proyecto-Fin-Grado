package es.iesnervion.alopez.ourtravel.ui.searchCity.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.GetLastDestinationInsertedId
import java.util.*
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction2


@ExperimentalMaterialApi
@Composable
fun SearchCityCard(tripId: String?,
    city: City,
    addDestination: (city: City,
                     description: String,
                     accomodationCosts: Long,
                     transportationCosts: Long,
                     foodCosts: Long,
                     tourismCosts: Long,
                     startDate: Date,
                     endDate: Date,
                     travelStay: String,
                     tourismAttractions: List<String>,
                     creationDate: Date) -> Unit,
    navigateToTripPlanningScreenFromSearchCity: (City, String?) -> Unit,
    getLastDestinationInsertedId: KFunction2<(String) -> Unit, String, Unit>,
    navigateToDestinationScreenFromSearchCity: (Destination, String?) -> Unit
){
    var lastDestinationInsertedId by remember { mutableStateOf("") }
    val path = rememberAsyncImagePainter(model = city.photo)
    Card(modifier = Modifier
        .fillMaxSize()
        .height(200.dp)
        .padding(8.dp), elevation = 8.dp ,shape = RoundedCornerShape(8.dp),
        onClick = {
            addDestination(city, "", 0, 0, 0, 0, Date(), Date(), "", listOf(), Date())
            getLastDestinationInsertedId({ lastDestinationInsertedId = it }, tripId!!)
        }
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Image(
                path,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            city.name?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp), text = it, fontSize = 18.sp, color = Color.White, fontWeight= FontWeight.Bold
                )
            }
        }
    }

    LaunchedEffect(lastDestinationInsertedId){
        if (lastDestinationInsertedId.isNotEmpty()) {
            navigateToDestinationScreenFromSearchCity(
                Destination(
                    lastDestinationInsertedId,
                    cityName = city.name,
                    cityPhoto = city.photo
                ), tripId
            )
        }
    }
}