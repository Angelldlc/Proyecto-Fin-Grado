package es.iesnervion.alopez.ourtravel.ui.searchCity.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.ui.searchCity.SearchCityViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun SearchCityScreen(
    parentViewModel: TripListViewModel = hiltViewModel(),
    tripId: String?,
    destinationViewModel: DestinationViewModel = hiltViewModel(),
    viewModel: SearchCityViewModel = hiltViewModel(),
    navigateToTripPlanningScreenFromSearchCity: (City, String?) -> Unit,
    navigateToDestinationScreenFromSearchCity: (Destination, String?) -> Unit
) {
    val textState = remember() { mutableStateOf(TextFieldValue("")) }
    Scaffold(
        topBar = { SearchCitySearchView(textState) },
    ) { padding ->
        SearchCityList(
            tripId,
            textState,
            viewModel,
            padding,
            addDestination = { city,
                               description,
                               accomodationCosts,
                               transportationCosts,
                               foodCosts,
                               tourismCosts,
                               startDate,
                               endDate,
                               travelStay,
                               tourismAttractions,
                               creationDate ->
                destinationViewModel.addDestination(
                    tripId!!,
                    city,
                    description,
                    accomodationCosts,
                    transportationCosts,
                    foodCosts,
                    tourismCosts,
                    startDate,
                    endDate,
                    travelStay,
                    tourismAttractions,
                    creationDate
                )
            },
            navigateToTripPlanningScreenFromSearchCity,
            navigateToDestinationScreenFromSearchCity,
            getLastDestinationInsertedId = destinationViewModel::getLastDestinationInsertedId
        )
    }

}