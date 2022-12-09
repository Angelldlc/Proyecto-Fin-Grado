package es.iesnervion.alopez.ourtravel.usecases.triplist

import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository

class GetLastTripInsertedId(
    private val repo: TripRepository
) {
    operator fun invoke() = repo.getLastTripInsertedId()
}