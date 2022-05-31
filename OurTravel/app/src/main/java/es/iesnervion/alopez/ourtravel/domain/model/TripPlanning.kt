package es.iesnervion.alopez.ourtravel.domain.model

import java.util.*

data class TripPlanning(
    var id: String? = null, //TODO Necesito el id?
    var name: String? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var totalCost: Double? = null,
    var destinations: List<Destination?>?,
    var photo: String?
//TODO añadir campo Foto, proviene de API
)
