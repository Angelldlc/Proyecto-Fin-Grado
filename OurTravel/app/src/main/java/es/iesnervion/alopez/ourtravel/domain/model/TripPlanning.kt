package es.iesnervion.alopez.ourtravel.domain.model

import java.util.*

data class TripPlanning(
    var id: String? = null,
    var name: String? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var totalCost: Double? = null
)
