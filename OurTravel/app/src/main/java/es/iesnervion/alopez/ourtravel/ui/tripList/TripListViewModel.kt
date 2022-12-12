package es.iesnervion.alopez.ourtravel.ui.tripList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.Success
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.DestinationViewModel
import es.iesnervion.alopez.ourtravel.usecases.UseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import okhttp3.internal.wait
import java.util.*
import javax.inject.Inject
/**
 * Clase pública TripListViewModel.
 *
 * Clase pública que administra la lógica de la vista TripListScreen y TripPlanningScreen.
 *
 */
@HiltViewModel
class TripListViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _tripsState = mutableStateOf<Response<List<TripPlanning>>>(Response.Loading)
    val tripsState: State<Response<List<TripPlanning>>> = _tripsState

    private val _lastTripInsertedId = mutableStateOf<Response<String>>(Success(""))
    val lastTripInsertedId: State<Response<String>> = _lastTripInsertedId

    private val _isTripAddedState = mutableStateOf<Response<Boolean>>(Success(false))
    val isTripAddedState: State<Response<Boolean>> = _isTripAddedState

    private val _isTripUpdatedState = mutableStateOf<Response<Boolean>>(Success(null))
    val isTripUpdatedState: State<Response<Boolean>> = _isTripUpdatedState


    private val _isTripDeletedState = mutableStateOf<Response<Boolean>>(Success(null))
    val isTripDeletedState: State<Response<Boolean>> = _isTripDeletedState

    init {
        getTrips()
    }

    private fun getTrips() {
        viewModelScope.launch {
            useCases.getTrips().collect { response ->
                _tripsState.value = response
            }
        }
    }

    fun getLastTripInsertedId(){
        viewModelScope.launch {
            useCases.getLastTripInsertedId().collect { response ->
                _lastTripInsertedId.value = response
            }
        }
    }

    fun addTrip(name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String, creationDate: Timestamp) {
        viewModelScope.launch {
            useCases.addTrip(name, startDate, endDate, totalCost, photo, creationDate).collect { response ->
                _isTripAddedState.value = response
            }
        }
    }

    fun updateTrip(id: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long){
        viewModelScope.launch {
            useCases.updateTrip(id, startDate, endDate, totalCost).collect() { response ->
                _isTripUpdatedState.value = response
            }
        }
    }

    fun deleteTrip(id: String) {
        viewModelScope.launch {
            useCases.deleteTrip(id).collect { response ->
                _isTripDeletedState.value = response
            }
        }
    }
}