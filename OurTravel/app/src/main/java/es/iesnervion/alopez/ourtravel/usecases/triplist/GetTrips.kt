package es.iesnervion.alopez.ourtravel.usecases.triplist

import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository

class GetTrips(
    private val repo: TripRepository
) {
    operator fun invoke() = repo.getTripsFromFirestore()
}