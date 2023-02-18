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
/**
 * Clase pública DestinationViewModel.
 *
 * Clase pública que administra la lógica de la vista DestinationScreen.
 *
 */
@HiltViewModel
class DestinationViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _destinationsState = mutableStateOf<Response<List<Destination>>>(Response.Loading)
    val destinationsState: State<Response<List<Destination>>> = _destinationsState

    private val _isDestinationAddedState = mutableStateOf<Response<Boolean>>(Response.Success(false,""))
    val isDestinationAddedState: State<Response<Boolean>> = _isDestinationAddedState

    private val _isDestinationUpdatedState = mutableStateOf<Response<Boolean>>(Response.Success(false,""))
    val isDestinationUpdatedState: State<Response<Boolean>> = _isDestinationUpdatedState

    private val _isDestinationDeletedState = mutableStateOf<Response<Boolean>>(Response.Success(null,""))
    val isDestinationDeletedState: State<Response<Boolean>> = _isDestinationDeletedState


    fun getDestinations(tripId: String) {
        viewModelScope.launch {
            useCases.getDestinations(tripId).collect { response ->
                _destinationsState.value = response
            }
        }
    }

    fun addDestination(
        tripId: String,
        id: String,
        city: City,
        description: String,
        accomodationCosts: Long,
        transportationCosts: Long,
        foodCosts: Long,
        tourismCosts: Long,
        startDate: Date,
        endDate: Date,
        travelStay: String,
        tourismAttractions: List<String>
    ) {
        viewModelScope.launch {
            useCases.addDestination(tripId, id, city, description, accomodationCosts, transportationCosts, foodCosts, tourismCosts, startDate, endDate, travelStay, tourismAttractions).collect { response ->
                _isDestinationAddedState.value = response
            }
        }
    }

    fun updateDestinationFromFirestore(
        tripId: String,
        id: String,
        city: City,
        description: String,
        accomodationCosts: Long,
        transportationCosts: Long,
        foodCosts: Long,
        tourismCosts: Long,
        startDate: Date,
        endDate: Date,
        travelStay: String,
        tourismAttractions: List<String>
    ) {
        viewModelScope.launch {
            useCases.updateDestination(tripId, id, city, description, accomodationCosts, transportationCosts, foodCosts, tourismCosts, startDate, endDate, travelStay, tourismAttractions).collect() { response ->
                _isDestinationUpdatedState.value = response
            }
        }
    }

    fun deleteDestination(tripId: String, id: String) {
        viewModelScope.launch {
            useCases.deleteDestination(tripId,id).collect { response ->
                _isDestinationDeletedState.value = response
            }
        }
    }
}