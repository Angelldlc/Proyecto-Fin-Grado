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

@HiltViewModel
class TripListViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _tripsState = mutableStateOf<Response<List<TripPlanning>>>(Response.Loading)
    val tripsState: State<Response<List<TripPlanning>>> = _tripsState

    private val _lastTripInsertedId = mutableStateOf<Response<String>>(Response.Loading)
    val lastTripInsertedId: State<Response<String>> = _lastTripInsertedId

    private val _isTripAddedState = mutableStateOf<Response<Boolean>>(Success(null))
    val isTripAddedState: State<Response<Boolean>> = _isTripAddedState

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

    fun addTrip(id: String, name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String) {
        viewModelScope.launch {
            useCases.addTrip(id, name, startDate, endDate, totalCost, photo).collect { response ->
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

    /*suspend*/ fun addTripWithDestinationData(edit: Boolean, destination: Destination?, viewModel: DestinationViewModel,){
        if(!edit){

            addTrip("","", Timestamp.now(), Timestamp.now(),
                (destination?.accomodationCosts ?: 0) + (destination?.transportationCosts ?: 0) +
                        (destination?.foodCosts ?: 0) + (destination?.tourismCosts ?: 0), destination?.cityPhoto.toString())
//            delay(1500)
            getLastTripInsertedId()
            /*viewModel.addDestination(*//*lastTripInsertedId.value.toString()*//*(lastTripInsertedId.value.toString()),
                City(destination?.cityName, destination?.cityPhoto),
                destination?.description.toString(),
                destination?.accomodationCosts ?: 0,
                destination?.transportationCosts ?: 0,
                destination?.foodCosts ?: 0,
                destination?.tourismCosts ?: 0,
                destination?.startDate ?: Date(),
                destination?.endDate ?: Date(),
                destination?.travelStay.toString(),
                (destination?.tourismAttractions ?: emptyList<String>()) as List<String>
            )*/
        }
    }
}