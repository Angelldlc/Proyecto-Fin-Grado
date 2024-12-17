package es.iesnervion.alopez.ourtravel.domain.model

import com.google.firebase.firestore.PropertyName
import java.util.*

/**
 * Data Class pública Destination.
 *
 * Clase de datos pública que representa un destino.
 *
 */
data class Destination (
    @get:PropertyName("Id")
    @set:PropertyName("Id")
    var id: String? = null,
    @get:PropertyName("CityName")
    @set:PropertyName("CityName")
    var cityName: String? = null,
    @get:PropertyName("CityPhoto")
    @set:PropertyName("CityPhoto")
    var cityPhoto: String? = null,
    @get:PropertyName("Description")
    @set:PropertyName("Description")
    var description: String? = null,
    @get:PropertyName("AccomodationCosts")
    @set:PropertyName("AccomodationCosts")
    var accomodationCosts: Long? = null,
    @get:PropertyName("TransportationCosts")
    @set:PropertyName("TransportationCosts")
    var transportationCosts: Long? = null,
    @get:PropertyName("FoodCosts")
    @set:PropertyName("FoodCosts")
    var foodCosts: Long? = null,
    @get:PropertyName("TourismCosts")
    @set:PropertyName("TourismCosts")
    var tourismCosts: Long? = null,
    @get:PropertyName("StartDate")
    @set:PropertyName("StartDate")
    var startDate: Date? = null,
    @get:PropertyName("EndDate")
    @set:PropertyName("EndDate")
    var endDate: Date? = null,
    @get:PropertyName("TravelStay")
    @set:PropertyName("TravelStay")
    var travelStay: String? = null,
    @get:PropertyName("TourismAttractions")
    @set:PropertyName("TourismAttractions")
    var tourismAttractions: List<String?>? = null,
    @get:PropertyName("CreationDate")
    @set:PropertyName("CreationDate")
    var creationDate: Date? = null
)