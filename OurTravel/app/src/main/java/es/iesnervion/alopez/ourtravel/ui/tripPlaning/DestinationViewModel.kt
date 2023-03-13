package es.iesnervion.alopez.ourtravel.ui.tripPlaning

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.domain.repository.AddDestinationResponse
import es.iesnervion.alopez.ourtravel.domain.repository.DeleteDestinationResponse
import es.iesnervion.alopez.ourtravel.domain.repository.DestinationsResponse
import es.iesnervion.alopez.ourtravel.domain.repository.UpdateDestinationResponse
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

    var destinationsResponse by mutableStateOf<DestinationsResponse>(Loading)
        private set

    var addDestinationResponse by mutableStateOf<AddDestinationResponse>(Success(false,""))
        private set

    var updateDestinationResponse by mutableStateOf<UpdateDestinationResponse>(Success(false,""))
        private set

    var deleteDestinationResponse by mutableStateOf<DeleteDestinationResponse>(Success(false,""))
        private set

    var openDialog by mutableStateOf(false)
        private set

    /*private val _destinationsState = mutableStateOf<Response<List<Destination>>>(Response.Loading)
    val destinationsState: State<Response<List<Destination>>> = _destinationsState

    private val _isDestinationAddedState = mutableStateOf<Response<Boolean>>(Response.Success(false,""))
    val isDestinationAddedState: State<Response<Boolean>> = _isDestinationAddedState

    private val _isDestinationUpdatedState = mutableStateOf<Response<Boolean>>(Response.Success(false,""))
    val isDestinationUpdatedState: State<Response<Boolean>> = _isDestinationUpdatedState

    private val _isDestinationDeletedState = mutableStateOf<Response<Boolean>>(Response.Success(null,""))
    val isDestinationDeletedState: State<Response<Boolean>> = _isDestinationDeletedState*/


    fun getDestinations(tripId: String) =
        viewModelScope.launch {
            useCases.getDestinations(tripId).collect { response ->
                destinationsResponse = response
            }
        }

    fun addDestination(
        tripId: String,
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
    ) = viewModelScope.launch {
        addDestinationResponse = Loading
        addDestinationResponse = useCases.addDestination(tripId, city, description, accomodationCosts, transportationCosts, foodCosts, tourismCosts, startDate, endDate, travelStay, tourismAttractions)
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
    ) = viewModelScope.launch {
        updateDestinationResponse = Loading
        updateDestinationResponse = useCases.updateDestination(tripId, id, city, description, accomodationCosts, transportationCosts, foodCosts, tourismCosts, startDate, endDate, travelStay, tourismAttractions)
    }

    fun deleteDestination(tripId: String, id: String) =
        viewModelScope.launch {
            deleteDestinationResponse = Loading
            deleteDestinationResponse = useCases.deleteDestination(tripId,id)
        }

    fun openDialog(){
        openDialog = true
    }

    fun closeDialog(){
        openDialog = false
    }
}