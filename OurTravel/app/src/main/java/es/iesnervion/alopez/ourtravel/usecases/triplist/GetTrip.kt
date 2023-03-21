package es.iesnervion.alopez.ourtravel.usecases.triplist

import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository

class GetTrip(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(tripId: String) = tripRepository.getTripFromFirestore(tripId)
}