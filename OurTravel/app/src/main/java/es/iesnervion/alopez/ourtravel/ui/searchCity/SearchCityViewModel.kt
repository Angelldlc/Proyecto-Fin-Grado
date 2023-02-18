package es.iesnervion.alopez.ourtravel.ui.searchCity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.usecases.UseCases
import es.iesnervion.alopez.ourtravel.usecases.cities.GetCities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Clase pública SearchCityViewModel.
 *
 * Clase pública que administra la lógica de la vista SearchCityScreen.
 *
 */
@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private var _citiesState = MutableLiveData<Response<List<City>>>(Response.Loading)
    val citiesState: LiveData<Response<List<City>>> = _citiesState

    fun getCities(){
        viewModelScope.launch(Dispatchers.IO) {
            val cities1 = try { useCases.getCities() } catch (e: Exception){ emptyList() }
            _citiesState.postValue(Response.Success(cities1, ""))
        }
    }

}