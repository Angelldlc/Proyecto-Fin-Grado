package es.iesnervion.alopez.ourtravel.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.ktx.Firebase
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DestinationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @Named("usersReference")
    private val usersRef: CollectionReference,
    @Named("tripsReference")
    private val tripPlanningRef: CollectionReference,
    @Named("destinationsReference")
    private val destinationRef: CollectionReference
) : DestinationRepository {

    override fun getDestinationsFromFirestore(tripId: String) = callbackFlow {
        val userRef = auth.currentUser?.let { usersRef.document(it.uid) }
        val snapshotListener = userRef?.collection("TripPlannings")?.document(tripId)
            ?.collection(destinationRef.path)
            ?.orderBy("StartDate")?.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val destinations = try {
                        snapshot.toObjects(Destination::class.java)
                    } catch (e: Exception) {
                        emptyList()
                    }
                    Response.Success(destinations)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapshotListener?.remove()
        }
    }

    override suspend fun addDestinationToFirestore(
        id: String,
        city: City,
        description: String,
        accomodationCosts: Long,
        transportationCosts: Long,
        foodCosts: Long,
        tourismCosts: Long,
        startDate: Date,
        endDate: Date,
        travelStay: String,
        tourismAttractions: List<String>
    ) = flow {
        val user = Firebase.auth.currentUser?.uid
        if (user != null) {
            try {
                emit(Response.Loading)
                auth.currentUser?.apply {
                    usersRef.document(uid)
                        .collection("TripPlannings")
                        .document(tripPlanningRef.id)
                        .collection("Destinations")
                        .document(destinationRef.id).set(
                            mapOf(
                                "Id" to destinationRef.id,
                                "City" to city,
                                "Description" to description,
                                "AccommodationCosts" to accomodationCosts,
                                "transportationCosts" to transportationCosts,
                                "foodCosts" to foodCosts,
                                "tourismCosts" to tourismCosts,
                                "startDate" to startDate,
                                "endDate" to endDate,
                                "travelStay" to travelStay,
                                "tourismAttractions" to tourismAttractions,
                            )
                        ).await()
                    emit(Response.Success(true))
                }
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        } else {
//            emit(Error("Unexpected Error"))
        }
    }

    override suspend fun deleteDestinationFromFirestore(id: String) = flow {
        val user = Firebase.auth.currentUser?.uid
        if (user != null){
            try {
                emit(Response.Loading)
                auth.currentUser?.apply {
                    try {
                        usersRef.document(uid).collection("TripPlannings").document(tripPlanningRef.id).collection("Destinations").document(id).delete().await()
                        emit(Response.Success(true))
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
        //TODO("Not yet implemented")
//        try{
//            usersRef.document(id).delete().await()
//            emit(Response.Success(true))
//        }catch (e: Exception){
//            emit(Response.Failure(e))
//        }

}