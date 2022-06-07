package es.iesnervion.alopez.ourtravel.usecases.destinationlist

import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository

class GetDestinations(
    private val destinationRepo: DestinationRepository
) {
    operator fun invoke(tripId: String) = destinationRepo.getDestinationsFromFirestore(tripId)
}