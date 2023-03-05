package es.iesnervion.alopez.ourtravel.ui.tripList

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.Success
import es.iesnervion.alopez.ourtravel.domain.model.Response.Loading
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.domain.repository.AddTripPlanningResponse
import es.iesnervion.alopez.ourtravel.domain.repository.UpdateTripPlanningResponse
import es.iesnervion.alopez.ourtravel.domain.repository.DeleteTripPlanningResponse
import es.iesnervion.alopez.ourtravel.domain.repository.TripPlanningResponse
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

    var tripsResponse by mutableStateOf<TripPlanningResponse>(Loading)
        private set

    var addTripResponse by mutableStateOf<AddTripPlanningResponse>(Success(false,""))
        private set

    var updateTripResponse by mutableStateOf<UpdateTripPlanningResponse>(Success(false,""))
        private set

    var deleteTripResponse by mutableStateOf<DeleteTripPlanningResponse>(Success(false,""))
        private set

    var openDialog by mutableStateOf(false)
        private set

    /*private val _tripsState = mutableStateOf<Response<List<TripPlanning>>>(Response.Loading)
    val tripsState: State<Response<List<TripPlanning>>> = _tripsState

    private val _lastTripInsertedId = mutableStateOf<Response<Boolean>>(Success(null,""))
    val lastTripInsertedId: State<Response<Boolean>> = _lastTripInsertedId

    private val _isTripAddedState = mutableStateOf<Response<Boolean>>(Success(null,""))
    val isTripAddedState: State<Response<Boolean>> = _isTripAddedState

    private val _isTripUpdatedState = mutableStateOf<Response<Boolean>>(Success(null,""))
    val isTripUpdatedState: State<Response<Boolean>> = _isTripUpdatedState


    private val _isTripDeletedState = mutableStateOf<Response<Boolean>>(Success(null,""))
    val isTripDeletedState: State<Response<Boolean>> = _isTripDeletedState*/

    init {
        getTrips()
    }

    private fun getTrips() =
        viewModelScope.launch {
            useCases.getTrips().collect { response ->
                tripsResponse = response
            }
        }


    /*fun getLastTripInsertedId(): String {
        viewModelScope.launch {
            useCases.getLastTripInsertedId().collect { response ->
                _lastTripInsertedId.value = response
            }
        }
        return _lastTripInsertedId.value.toString() //TODO corregir
    }*/

    fun addTrip(name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String, creationDate: Timestamp) = viewModelScope.launch {
        addTripResponse = Loading
        addTripResponse = useCases.addTrip(name, startDate, endDate, totalCost, photo, creationDate)
    }

    fun updateTrip(id: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long) = viewModelScope.launch {
        updateTripResponse = Loading
        updateTripResponse = useCases.updateTrip(id, startDate, endDate, totalCost)
    }


    fun deleteTrip(id: String) = viewModelScope.launch {
        deleteTripResponse = Loading
        deleteTripResponse = useCases.deleteTrip(id)
    }

    fun openDialog() {
        openDialog = true
    }

    fun closeDialog() {
        openDialog = false
    }

}