package es.iesnervion.alopez.ourtravel.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import es.iesnervion.alopez.ourtravel.domain.model.Response.Error
import es.iesnervion.alopez.ourtravel.domain.model.Response.Success
import es.iesnervion.alopez.ourtravel.domain.model.Response.Failure
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Clase pública TripRepositoryImpl.
 *
 * Clase encargada de realizar las lecturas, inserciones, actualizaciones y borrados de los objetos
 * TripPlanning de una base de datos Firebase.
 *
 */
@Singleton
class TripRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @Named("usersReference")
    private val usersRef: CollectionReference,
    @Named("tripsReference")
    private val tripPlanningRef: CollectionReference
) : TripRepository {

    /**
     * Método público implementado getTripsFromFirestore.
     *
     * Método público que recoge los viajes de una base de datos Firebase.
     * El método realiza la búsqueda de los viajes y ordena el resultado por nombre en orden
     * ascendente, devuelve una respuesta satisfactoria con la lista de viajes si la consulta se
     * ejecuta sin problemas, una respuesta satisfactoria con una lista vacía si la consulta se ha
     * ejecutado correctamente pero no ha encontrado ningún viaje o una respuesta de error en caso
     * de que falle la consulta.
     *
     * Entradas: void
     * Salidas: Response<List<TripPlanning>>.
     *
     */
    override fun getTripsFromFirestore() = callbackFlow {
        val userRef = auth.currentUser?.let { usersRef.document(it.uid) }
        val snapshotListener = userRef?.collection(tripPlanningRef.path)
            ?.orderBy("Name", Query.Direction.ASCENDING)?.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val trips = /*try {*/
                        snapshot.toObjects(TripPlanning::class.java)
                    /*} catch (e: Exception) {
                        emptyList()
                    }*/
                    Success(trips, "")
                } else {
                    Failure(e)
                }
                trySend(response)
            }
        awaitClose {
            snapshotListener?.remove()
        }
    }


    ///////////////////////////////////
    override suspend fun getTripFromFirestore(id: String): TripPlanning? {

        val userRef = auth.currentUser?.let { usersRef.document(it.uid) }
        val tripsCollection = userRef?.collection(tripPlanningRef.path)?.document(id)

        val trip = try {
            tripsCollection?.get()?.await()?.toObject(TripPlanning::class.java)
        } catch (e: Exception) {
            null
        }

        return trip

    }/*val documentSnapshot =
            userRef?.collection(tripPlanningRef.path)?.document(id)?.get()?.await()
        if (documentSnapshot != null) {
            if (documentSnapshot.exists()) {
                val trip = documentSnapshot.toObject(TripPlanning::class.java)
                Success(trip, "")
            } else {
                Error("Trip not found")
            }
        }*/


    /**
     * Método público implementado getLastTripInserted
     *
     * Método público que recoge el id del último viaje insertado. El método devuelve una respuesta
     * satisfactoria con el id si la consulta se ejecuta sin problemas, una respuesta satisfactoria
     * con un valor nulo si la consulta se ha ejecutado correctamente pero no ha encontrado ningún
     * viaje o una respuesta de error en caso de que falle la consulta.
     *
     * Entradas: void.
     * Salidas: Response<String>
     *
     * ******************************************************************************************
     * ******************************************************************************************
     * Comentario: Este método acaba ejecutandose antes de tiempo por un problema con el ámbito de
     * las corrutinas, no he podido solucionarlo por falta de tiempo.
     */
    override suspend fun getLastTripInsertedId(): String? {
        val userRef = auth.currentUser?.let { usersRef.document(it.uid) }
        val tripsCollection = userRef
            ?.collection(tripPlanningRef.path)
            ?.orderBy("CreationDate", Query.Direction.DESCENDING)
            ?.limit(1)

        /*return */
        val lastTrip = try {
            tripsCollection?.get()
                ?.await()
                ?.documents
                ?.firstOrNull()
                ?.id
        } catch (e: Exception) {
            null
        }
        return lastTrip

        /*?.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val tripId = *//*try {*//*
                        snapshot.toObjects(TripPlanning::class.java)[0].id
                    *//*} catch (e: Exception) {
                        null
                    }*//*
                    tripId.toString()
                } else {
                    ""
                }
                trySend(response)
            }*/
        /*awaitClose {
            snapshotListener?.remove()
        }*/
    }

    /**
     * Método público implementado asíncrono addTripToFirestore.
     *
     * Método público que añade un viaje a una base de datos Firebase. El método crea un id para el
     * viaje que se va a añadir y se realiza la inserción de este en la base de datos con los
     * valores proporcionados. El método devuelve una respuesta satisfactoria en caso de realizarse
     * correctamente la inserción.
     *
     * Entradas: name String, startDate Timestamp, endDate Timestamp, totalCost Long, photo String,
     * creationDate Timestamp. El conjunto de parámetros de entrada representa el viaje y el
     * momento de su creación.
     * Salidas: Response<Boolean>.
     *
     * ******************************************************************************************
     * ******************************************************************************************
     * Comentario: Este método acaba ejecutandose varias veces por un problema con el ámbito de las
     * corrutinas, no he podido solucionarlo por falta de tiempo.
     *
     */
    override suspend fun addTripToFirestore(
        name: String,
        startDate: Timestamp,
        endDate: Timestamp,
        totalCost: Long,
        photo: String,
        creationDate: Timestamp
    ): AddTripPlanningResponse {
        return try {
            val user = Firebase.auth.currentUser?.uid
            /*emit(Response.Loading)*/
            val uniqueid =
                usersRef.document(user!!).collection("TripPlannings").document().id
            val trip = TripPlanning(
                uniqueid,
                name,
                startDate,
                endDate,
                totalCost,
                photo,
                creationDate
            )
            usersRef.document(user).collection("TripPlannings").document(uniqueid)
                .set(trip).await()
            Success(true, id = uniqueid)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    /**
     * Método público implementado asíncrono updateTripFromFirestore.
     *
     * Método público que actualiza los campos de un viaje en una base de datos Firebase. El método,
     * tras comprobar que el usuario no sea nulo, actualiza los valores de los campos del viaje en
     * la base de datos y emite una respuesta satisfactoria si esta acción se ha completado con
     * éxito, por contraparte, emite una respuesta fallida si no se ha completado con éxito.
     *
     * Entradas: id String, startDate Timestamp, endDate Timestamp, totalCost Long. El conjunto de
     * parámetros de entrada representa el viaje.
     * Salida: Response<Boolean>.
     *
     */
    override suspend fun updateTripFromFirestore(
        id: String,
        name: String,
        startDate: Timestamp,
        endDate: Timestamp,
        totalCost: Long,
        photo: String?
    ): UpdateTripPlanningResponse {
        return try {
            val user = Firebase.auth.currentUser?.uid
            if (user != null) {
                usersRef.document(user).collection("TripPlannings").document(id)
                    .update(
                        mapOf(
                            "Name" to name,
                            "StartDate" to startDate,
                            "EndDate" to endDate,
                            "TotalCost" to totalCost,
                            "Photo" to photo
                        )
                    ).await()
                Success(true, "")
            } else {
                Error("User is null")
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    /**
     * Método público implementado asíncrono deleteTripFromFirestore.
     *
     * Método público que elimina un viaje en una base de datos de Firebase. El método, tras
     * comprobar que el usuario no sea nulo, realiza el borrado del viaje en la base de datos y
     * emite una respuesta satisfactoria si esta acción se ha completado con éxito, por contraparte,
     * emite una respuesta fallida si no se ha completado con éxito.
     *
     * Entradas: id String.
     * Salidas: Response<Boolean>.
     *
     */
    override suspend fun deleteTripFromFirestore(id: String): DeleteTripPlanningResponse {
        return try {
            val user = Firebase.auth.currentUser?.uid
            usersRef.document(user!!)
                .collection("TripPlannings")
                .document(id)
                .delete().await()
            Success(true, "")

        } catch (e: Exception) {
            Failure(e)
        }
    }
}