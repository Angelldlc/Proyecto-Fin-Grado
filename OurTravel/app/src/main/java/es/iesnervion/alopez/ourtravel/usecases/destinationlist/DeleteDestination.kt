package es.iesnervion.alopez.ourtravel.usecases.destinationlist

import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository

class DeleteDestination(
    private val destinationRepo: DestinationRepository
) {
    suspend operator fun invoke(tripId: String, id: String) =
        destinationRepo.deleteDestinationFromFirestore(tripId, id)
}