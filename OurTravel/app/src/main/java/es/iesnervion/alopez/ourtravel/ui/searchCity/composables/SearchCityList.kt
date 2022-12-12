package es.iesnervion.alopez.ourtravel.ui.searchCity.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.ui.searchCity.SearchCityViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SearchCityList(
    tripId: String?,
    textState: MutableState<TextFieldValue>,
    viewModel: SearchCityViewModel,
    padding: PaddingValues,
    navigateToTripPlanningScreenFromSearchCity: (City, String?) -> Unit
) {
    val cities by viewModel.citiesState.observeAsState(Response.Loading)
    viewModel.getCities()
    val filteredCities: List<City>?
    when (cities) {
        is Response.Loading -> {}
        is Response.Success -> {
            val searchedText = textState.value.text
            filteredCities = if (searchedText.isNotEmpty()) {
                val resultList = ArrayList<City>()
                for (city in (cities as Response.Success<List<City>>).data?: emptyList()) {
                    if (city.name?.lowercase()?.contains(searchedText.lowercase()) == true
                    ) {
                        resultList.add(city)
                    }
                }
                resultList
            } else {
                (cities as Response.Success<List<City>>).data ?: emptyList()
            }

            LazyVerticalGrid(
                modifier = Modifier.padding(padding),
                columns = GridCells.Fixed(2)
            ) {
                filteredCities?.size?.let {
                    items(it) { city ->
                        BoxWithConstraints {
                            SearchCityCard(tripId, filteredCities[city], navigateToTripPlanningScreenFromSearchCity)
                        }
                    }
                }
            }
        }
    }
}