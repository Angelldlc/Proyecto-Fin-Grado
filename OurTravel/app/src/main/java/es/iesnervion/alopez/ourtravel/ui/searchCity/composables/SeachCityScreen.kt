package es.iesnervion.alopez.ourtravel.ui.searchCity.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.searchCity.SearchCityViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun SearchCityScreen(
    viewModel: SearchCityViewModel = hiltViewModel(),
){
    val textState = remember() { mutableStateOf(TextFieldValue("")) }
    Scaffold(
        topBar = { SearchCitySearchView(textState) },
    ) { padding ->
        SearchCityList(textState, viewModel, padding)
    }

}