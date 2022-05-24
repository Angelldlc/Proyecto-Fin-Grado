package es.iesnervion.alopez.ourtravel.usecases.triplist

import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository

class DeleteTrip (
    private val repo: TripRepository
) {
    suspend operator fun invoke(id: String) = repo.deleteTripFromFirestore(id)
}