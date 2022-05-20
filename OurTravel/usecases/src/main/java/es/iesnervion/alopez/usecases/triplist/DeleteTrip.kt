package es.iesnervion.alopez.usecases.triplist

import es.iesnervion.alopez.domain.repository.TripRepository

class DeleteTrip (
    private val repo: TripRepository
) {
    suspend operator fun invoke(id: String) = repo.deleteTripFromFirestore(id)
}