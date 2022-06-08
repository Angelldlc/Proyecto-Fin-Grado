package es.iesnervion.alopez.ourtravel.domain.model

import com.google.firebase.firestore.PropertyName
import com.google.firebase.Timestamp
import java.util.*

data class TripPlanning(
    @get:PropertyName("Id")
    @set:PropertyName("Id")
    var id: String? = null, //TODO Necesito el id?
    @get:PropertyName("Name")
    @set:PropertyName("Name")
    var name: String? = null,
    @get:PropertyName("StartDate")
    @set:PropertyName("StartDate")
    var startDate: Timestamp? = null,
    @get:PropertyName("EndDate")
    @set:PropertyName("EndDate")
    var endDate: Timestamp? = null,
//    @get:PropertyName("TotalCost")
//    @set:PropertyName("TotalCost")
//    var totalCost: Long? = null,
    @get:PropertyName("TotalAcommodationCost")
    @set:PropertyName("TotalAcommodationCost")
    var totalAccomodationCosts: Long? = null,
    @get:PropertyName("TotalTransportationCost")
    @set:PropertyName("TotalTransportationCost")
    var totalTransportationCosts: Long? = null,
    @get:PropertyName("TotalFoodCost")
    @set:PropertyName("TotalFoodCost")
    var totalFoodCosts: Long? = null,
    @get:PropertyName("TotalTourismCost")
    @set:PropertyName("TotalTourismCost")
    var totalTourismCosts: Long? = null,
//    var destinations: List<Destination?>?,
    @get:PropertyName("Photo")
    @set:PropertyName("Photo")
    var photo: String? = null
//TODO añadir campo Foto, proviene de API
)
