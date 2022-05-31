package es.iesnervion.alopez.ourtravel.data.repository

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.Success
import es.iesnervion.alopez.ourtravel.domain.model.Response.Error
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    @Named("tripsReference")
    private val tripPlanningRef: CollectionReference
) : TripRepository {

    val db = Firebase.firestore

    override fun getTripsFromFirestore() = callbackFlow {
        val snapshotListener = tripPlanningRef.orderBy("name").addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val trips = snapshot.toObjects(TripPlanning::class.java)
                Success(trips)
            } else {
                Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addTripToFirestore(
        id: String,
        name: String,
        startDate: Date,
        endDate: Date,
        totalCost: Double
    ): Flow<Response<Void?>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTripFromFirestore(id: String): Flow<Response<Void?>> {
        TODO("Not yet implemented")
    }
}