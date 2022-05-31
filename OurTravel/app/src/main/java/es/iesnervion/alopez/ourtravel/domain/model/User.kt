package es.iesnervion.alopez.ourtravel.domain.model

data class User(
    var id: String?, //TODO Necesito el id?
    var username: String?,
    var photo: String?,
    var tripPlannings: List<TripPlanning?>?
)
