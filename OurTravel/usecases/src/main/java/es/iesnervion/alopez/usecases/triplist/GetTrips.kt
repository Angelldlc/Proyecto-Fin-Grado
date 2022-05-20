package es.iesnervion.alopez.usecases.triplist

import es.iesnervion.alopez.domain.repository.TripRepository

class GetTrips (
    private val repo: TripRepository
) {
    operator fun invoke() = repo.getTripsFromFirestore()
}