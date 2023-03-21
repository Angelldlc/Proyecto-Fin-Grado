package es.iesnervion.alopez.ourtravel.ui.tripList

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response.Success
import es.iesnervion.alopez.ourtravel.domain.model.Response.Loading
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.domain.repository.AddTripPlanningResponse
import es.iesnervion.alopez.ourtravel.domain.repository.UpdateTripPlanningResponse
import es.iesnervion.alopez.ourtravel.domain.repository.DeleteTripPlanningResponse
import es.iesnervion.alopez.ourtravel.domain.repository.TripPlanningResponse
import es.iesnervion.alopez.ourtravel.usecases.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
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
) : ViewModel() {

    var tripsResponse by mutableStateOf<TripPlanningResponse>(Loading)
        private set

    var addTripResponse by mutableStateOf<AddTripPlanningResponse>(Success(false, ""))
        private set

    var updateTripResponse by mutableStateOf<UpdateTripPlanningResponse>(Success(false, ""))
        private set

    var deleteTripResponse by mutableStateOf<DeleteTripPlanningResponse>(Success(false, ""))
        private set

    var openDialog by mutableStateOf(false)
        private set
/*
    private val _lastTripInsertedId = mutableStateOf("")
    val lastTripInsertedId: State<String> = _lastTripInsertedId*/

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


    fun getTrip(id: String, callback: (TripPlanning?) -> Unit) =
        viewModelScope.launch {
            val trip = useCases.getTrip(id)
            callback(trip)
        }
   /* fun getLastTripInsertedId() {
        viewModelScope.launch {
            _lastTripInsertedId.value = useCases.getLastTripInsertedId().toString()
        }
    }*/

    /*fun getLastTripInsertedId(): String {
        viewModelScope.launch {
            useCases.getLastTripInsertedId().collect { response ->
                _lastTripInsertedId.value = response
            }
        }
        return _lastTripInsertedId.value.toString() //TODO corregir
    }*/

    /*fun getLastTripInsertedId(): String {
        var lastTripInsertedId = ""
        viewModelScope.launch {
            lastTripInsertedId = useCases.getLastTripInsertedId().toString()*//*.collect { response ->
            lastTripInsertedId = response
        }.toString()*//*
        }
        return lastTripInsertedId //TODO Comprobar si esta bien
    }*/

    /*fun getLastTripInsertedId() {
        viewModelScope.launch {
            lastTripInsertedId.value = useCases.getLastTripInsertedId().toString()
        }
    }*/

    fun getLastTripInsertedId(callback: (String) -> Unit) {
        viewModelScope.launch {
            val lastTripInsertedId = useCases.getLastTripInsertedId()
            callback(lastTripInsertedId.toString())
        }
    }


    fun addTrip(
        name: String,
        startDate: Timestamp,
        endDate: Timestamp,
        totalCost: Long,
        photo: String,
        creationDate: Timestamp
    ) = viewModelScope.launch {
        addTripResponse = Loading
        addTripResponse = useCases.addTrip(name, startDate, endDate, totalCost, photo, creationDate)
    }

    fun updateTrip(id: String, name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String?) =
        viewModelScope.launch {
            updateTripResponse = Loading
            updateTripResponse = useCases.updateTrip(id, name, startDate, endDate, totalCost, photo)
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

    /*fun updateTripName(tripId: String, newName: String, tripsResponse: TripPlanningResponse) {
        updateTripResponse = Loading
        viewModelScope.launch {
            updateTripResponse = useCases.updateTrip(tripId, newName, tripsResponse.data[tripId]!!.startDate, tripsResponse.data[tripId]!!.endDate, tripsResponse.data[tripId]!!.totalCost)
        }
    }*/

}