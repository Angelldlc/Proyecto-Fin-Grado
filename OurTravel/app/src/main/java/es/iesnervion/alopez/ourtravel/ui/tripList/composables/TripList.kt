package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.tripList.BottomNavState
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel

@ExperimentalMaterialApi
@Composable
fun TripList(
    padding: PaddingValues,
    textState: MutableState<TextFieldValue>,
    viewModel: TripListViewModel = hiltViewModel(),
    navigateToTripPlanningScreen: (String) -> Unit,
    bottomNavState: MutableState<BottomNavState>
) {
    val filteredTrips: List<TripPlanning>?
    when (val tripsResponse = viewModel.tripsState.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            val searchedText = textState.value.text
            filteredTrips = if (searchedText.isEmpty()) {
                if(bottomNavState.value == BottomNavState.FINALIZED){
                    tripsResponse.data?.filter { !(it.endDate == null || it.endDate!! > Timestamp.now()) }
                }else {
                    tripsResponse.data?.filter { it.endDate == null || it.endDate!! > Timestamp.now() }
                }
            } else {
                val resultList = ArrayList<TripPlanning>()
                for (trip in tripsResponse.data!!) {
                    if (trip.name?.lowercase()
                            ?.contains(searchedText.lowercase()) == true
                    ) {
                        resultList.add(trip)
                    }
                }
                if(bottomNavState.value == BottomNavState.FINALIZED){
                    resultList.filter { !(it.endDate == null || it.endDate!! > Timestamp.now()) }
                }else {
                    resultList.filter { it.endDate == null || it.endDate!! > Timestamp.now() }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(filteredTrips!!) { trip ->
                    TripCard(trip = trip, viewmodel = viewModel, navigateToTripPlanningScreen = navigateToTripPlanningScreen)
                }
            }
        }
        is Response.Error -> Log.d("OurTravel", tripsResponse.message)
    }
}