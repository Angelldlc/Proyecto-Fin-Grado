package es.iesnervion.alopez.ourtravel.ui.tripList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iesnervion.alopez.domain.model.Response
import es.iesnervion.alopez.domain.model.Response.Success
import es.iesnervion.alopez.domain.model.TripPlanning
import es.iesnervion.alopez.usecases.triplist.UseCases
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripListViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _tripsState = mutableStateOf<Response<List<TripPlanning>>>(Response.Loading)
    val tripsState: State<Response<List<TripPlanning>>> = _tripsState

    private val _isTripAddedState = mutableStateOf<Response<Void?>>(Success(null))
    val isTripAddedState: State<Response<Void?>> = _isTripAddedState

    private val _isTripDeletedState = mutableStateOf<Response<Void?>>(Success(null))
    val isTripDeletedState: State<Response<Void?>> = _isTripDeletedState

    var openDialogState = mutableStateOf(false)

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

    fun addTrip(id: String, name: String, startDate: Date, endDate: Date, totalCost: Double) {
        viewModelScope.launch {
            useCases.addTrip(id, name, startDate, endDate, totalCost).collect { response ->
                _isTripAddedState.value = response
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