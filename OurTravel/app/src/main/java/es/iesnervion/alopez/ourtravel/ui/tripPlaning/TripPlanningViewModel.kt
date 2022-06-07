package es.iesnervion.alopez.ourtravel.ui.tripPlaning

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.usecases.UseCases
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripPlanningViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _destinationsState = mutableStateOf<Response<List<Destination>>>(Response.Loading)
    val destinationsState: State<Response<List<Destination>>> = _destinationsState

    private val _isDestinationAddedState = mutableStateOf<Response<Boolean>>(Response.Success(null))
    val isDestinationAddedState: State<Response<Boolean>> = _isDestinationAddedState

    private val _isDestinationDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isDestinationDeletedState: State<Response<Void?>> = _isDestinationDeletedState

//    init {
//        getDestinations(tripId)
//    }

    fun getDestinations(tripId: String) {
        viewModelScope.launch {
            useCases.getDestinations(tripId).collect { response ->
                _destinationsState.value = response
            }
        }
    }

    fun addDestination(id: String,
                       city: City,
                       description: String,
                       accomodationCosts: Double,
                       transportationCosts: Double,
                       foodCosts: Double,
                       tourismCosts: Double,
                       startDate: Date,
                       endDate: Date,
                       travelStay: String,
                       tourismAttractions: List<String>) {
        viewModelScope.launch {
            useCases.addDestination(id, city, description, accomodationCosts, transportationCosts, foodCosts, tourismCosts, startDate, endDate, travelStay, tourismAttractions).collect { response ->
                _isDestinationAddedState.value = response
            }
        }
    }

    fun deleteDestination(id: String) {
        viewModelScope.launch {
            useCases.deleteDestination(id).collect { response ->
                _isDestinationDeletedState.value = response
            }
        }
    }
}