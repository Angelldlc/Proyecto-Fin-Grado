package es.iesnervion.alopez.ourtravel.domain.model

import java.util.*

data class Destination (
    var id: String?, //TODO Necesito el id?
    var city: City?,
    var description: String?,
    var accomodationCosts: Double?,
    var transportationCosts: Double?,
    var foodCosts: Double?,
    var tourismCosts: Double?,
    var startDate: Date?,
    var endDate: Date?,
    var travelStay: String?,
    var tourismAttractions: List<String?>?
        )