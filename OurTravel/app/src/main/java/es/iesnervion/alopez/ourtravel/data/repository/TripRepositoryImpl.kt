package es.iesnervion.alopez.ourtravel.data.repository

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.Success
import es.iesnervion.alopez.ourtravel.domain.model.Response.Error
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @Named("usersReference")
    private val usersRef: CollectionReference,
    @Named("tripsReference")
    private val tripPlanningRef: CollectionReference
) : TripRepository {

    val db = Firebase.firestore

    override fun getTripsFromFirestore() = callbackFlow {
        val userRef = auth.currentUser?.let { usersRef.document(it.uid) }
        val snapshotListener = userRef?.collection(tripPlanningRef.path)
            ?.orderBy("Name")?.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                //                var trips: List<TripPlanning>
                val trips = try {
                    snapshot.toObjects(TripPlanning::class.java)
                } catch (e: Exception) {
                    emptyList()
                }
                Success(trips)
            } else {
                Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener?.remove()
        }
    }

    override suspend fun addTripToFirestore(
        id: String,
        name: String,
        startDate: Timestamp,
        endDate: Timestamp,
        totalCost: Long
    ) = flow {
//        TODO("Not yet implemented")
        val user = Firebase.auth.currentUser?.uid
        if (user != null) {
            try {
                emit(Response.Loading)
                auth.currentUser?.apply {
                    usersRef.document(uid).collection("TripPlannings").document(tripPlanningRef.id).set(
                        mapOf(
                            "Id" to tripPlanningRef.id,
                            "Name" to name,
                            "StartDate" to startDate,
                            "EndDate" to endDate,
                            "TotalCost" to totalCost,
                            "Photo" to photoUrl?.toString()
                        )
                    ).await()
                    emit(Success(true))
                }
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        } else {
//            emit(Error("Unexpected Error"))
        }

    }


    override suspend fun deleteTripFromFirestore(id: String): Flow<Response<Void?>> {
        TODO("Not yet implemented")
    }
}