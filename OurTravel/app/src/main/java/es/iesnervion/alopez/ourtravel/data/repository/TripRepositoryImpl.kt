package es.iesnervion.alopez.ourtravel.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.ktx.Firebase
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.Error
import es.iesnervion.alopez.ourtravel.domain.model.Response.Success
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
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

    override fun getTripsFromFirestore() = callbackFlow {
        val userRef = auth.currentUser?.let { usersRef.document(it.uid) }
        val snapshotListener = userRef?.collection(tripPlanningRef.path)
            ?.orderBy("Name")?.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
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

    override fun getLastTripInsertedId() = callbackFlow {
        val userRef = auth.currentUser?.let { usersRef.document(it.uid) }
        val snapshotListener = userRef?.collection(tripPlanningRef.path)
            ?.whereEqualTo("Name", "")?.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val tripId = try {
                        snapshot.toObjects(TripPlanning::class.java)[0].id
//                        snapshot.toObjects(TripPlanning::class.java)
                    } catch (e: Exception) {
                        null
                    }
                    Success(tripId)
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
        totalCost: Long,
        photo: String
    ) = flow {
        val user = Firebase.auth.currentUser?.uid
        if (user != null) {
            try {
                emit(Response.Loading)
                auth.currentUser?.apply {
                    val uniqueid = usersRef.document(uid).collection("TripPlannings").document().id
                    usersRef.document(uid).collection("TripPlannings").document(uniqueid).set(
                        mapOf(
                            "Id" to uniqueid,
                            "Name" to name,
                            "StartDate" to startDate,
                            "EndDate" to endDate,
                            "TotalCost" to totalCost,
                            "Photo" to photo
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

    override suspend fun deleteTripFromFirestore(id: String) = flow {
        val user = Firebase.auth.currentUser?.uid
        if (user != null) {
            try {
                emit(Response.Loading)
                auth.currentUser?.apply {
                    try {
                        usersRef.document(uid).collection("TripPlannings").document(id).delete().await()
                        emit(Success(true))
                    } catch (e: Exception) {
                        emit(Response.Failure(e))
                    }
                }
            }catch (e: Exception){
                emit(Response.Failure(e))
            }
        }else{

        }
    }
}