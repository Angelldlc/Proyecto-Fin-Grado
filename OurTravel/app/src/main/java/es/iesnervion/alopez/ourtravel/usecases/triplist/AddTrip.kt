package es.iesnervion.alopez.ourtravel.usecases.triplist

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository
import java.util.*

class AddTrip(
    private val repo: TripRepository
) {
    suspend operator fun invoke(
        id: String,
        name: String,
        startDate: Timestamp,
        endDate: Timestamp,
        totalCost: Long,
        photo: String
    ) = repo.addTripToFirestore(id, name, startDate, endDate, totalCost, photo)
}