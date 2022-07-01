package es.iesnervion.alopez.ourtravel.domain.model

data class User(
    var id: String?,
    var username: String?,
    var photo: String?,
    var tripPlannings: List<TripPlanning?>?
)
