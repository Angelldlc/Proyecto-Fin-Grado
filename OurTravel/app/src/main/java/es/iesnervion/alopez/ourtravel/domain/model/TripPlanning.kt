package es.iesnervion.alopez.ourtravel.domain.model

import com.google.firebase.firestore.PropertyName
import com.google.firebase.Timestamp
import java.util.*

data class TripPlanning(
    @get:PropertyName("Id")
    @set:PropertyName("Id")
    var id: String? = null,
    @get:PropertyName("Name")
    @set:PropertyName("Name")
    var name: String? = null,
    @get:PropertyName("StartDate")
    @set:PropertyName("StartDate")
    var startDate: Timestamp? = null,
    @get:PropertyName("EndDate")
    @set:PropertyName("EndDate")
    var endDate: Timestamp? = null,
    @get:PropertyName("TotalCost")
    @set:PropertyName("TotalCost")
    var totalCost: Long? = null,
    @get:PropertyName("Photo")
    @set:PropertyName("Photo")
    var photo: String? = null
)
