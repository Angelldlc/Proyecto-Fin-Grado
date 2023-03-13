package es.iesnervion.alopez.ourtravel.usecases.destinationlist

import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository

class GetLastDestinationInsertedId (
    private val repo: DestinationRepository
) {
    suspend operator fun invoke() = repo.getLastDestinationInsertedId()
}