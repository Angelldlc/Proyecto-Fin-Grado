package es.iesnervion.alopez.ourtravel.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.domain.repository.AddDestinationResponse
import es.iesnervion.alopez.ourtravel.domain.repository.DeleteDestinationResponse
import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository
import es.iesnervion.alopez.ourtravel.domain.repository.UpdateDestinationResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Clase pública DestinationRepositoryImpl.
 *
 * Clase encargada de realizar las lecturas, inserciones, actualizaciones y borrados de los objetos
 * Destination de una base de datos Firebase.
 *
 */
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

    /**
     * Método público implementado getDestinationsFromFirestore.
     *
     * Método público que recoge los destinos de una base de datos Firebase asociados a un viaje.
     * El método realiza la búsqueda de los destinos de un viaje seleccionado por el usuario actual
     * y ordena el resultado por fecha de inicio en orden descendente, devuelve una respuesta
     * satisfactoria con la lista de destinos si la consulta se ejecuta sin problemas, una respuesta
     * satisfactoria con una lista vacía si la consulta se ha ejecutado correctamente pero no ha
     * encontrado ningún destino o una respuesta de error en caso de que falle la consulta.
     *
     * Entradas: tripId String. Representa el id del viaje seleccionado.
     * Salidas: Response<List<Destination>>.
     *
     */
    override fun getDestinationsFromFirestore(tripId: String) = callbackFlow {
        val userRef = auth.currentUser?.let { usersRef.document(it.uid) }
        val snapshotListener = userRef?.collection("TripPlannings")?.document(tripId)
            ?.collection(destinationRef.path)
            ?.orderBy("StartDate", Query.Direction.DESCENDING)?.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val destinations = try {
                        snapshot.toObjects(Destination::class.java)
                    } catch (e: Exception) {
                        emptyList()
                    }
                    Success(destinations, "")
                } else {
                    Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapshotListener?.remove()
        }
    }

    /**
     * Método público implementado asíncrono addDestinationToFirestore.
     *
     * Método público que añade un destino a una base de datos Firebase asociado a un viaje. El
     * método crea un id para el nuevo destino que se va a añadir y se realiza la inserción de
     * este en la base de datos con los valores proporcionados. El método devuelve una respuesta
     * satisfactoria en caso de realizarse correctamente la inserción.
     *
     * Entradas: tripId String, id String, city City, description String, accomodationCosts Long,
     * transportationCosts Long, foodCosts Long, tourismCosts Long, startDate Date, endDate Date,
     * travelStay String, tourismAttractions List<String>. El conjunto de parámetros de entrada
     * representa el destino.
     * Salidas: Response<Boolean>.
     *
     * ******************************************************************************************
     * ******************************************************************************************
     * Comentario: Este método acaba ejecutandose varias veces por un problema con el ámbito de las
     * corrutinas, no he podido solucionarlo por falta de tiempo.
     *
     */
    override suspend fun addDestinationToFirestore(
        tripId: String,
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
    ): AddDestinationResponse /*= flow*/ {
        return try {
            val user = Firebase.auth.currentUser?.uid
            val uniqueid = usersRef.document(user!!)
                .collection("TripPlannings")
                .document(tripId)
                .collection("Destinations")
                .document()
                .id
            val destination = Destination(
                uniqueid,
                city.name,
                city.photo,
                description,
                accomodationCosts,
                transportationCosts,
                foodCosts,
                tourismCosts,
                startDate,
                endDate,
                travelStay,
                tourismAttractions
            )
            usersRef.document(user)
                .collection("TripPlannings")
                .document(tripId)
                .collection("Destinations")
                .document(uniqueid)
                .set(destination)
                .await()
            Success(true, "")
        } catch (e: Exception) {
            Error(e.message ?: e.toString())
        }
    }

    /**
     * Método público implementado asíncrono updateDestinationFromFirestore.
     *
     * Método público que actualiza los campos de un destino de un viaje en una base de datos
     * Firebase. El método, tras comprobar que el usuario no sea nulo, actualiza los valores de los
     * campos del destino en la base de datos y emite una respuesta satisfactoria si esta acción se
     * ha completado con éxito, por contraparte, emite una respuesta fallida si no se ha
     * completado con éxito.
     *
     * Entradas: tripId String, id String, city City, description String, accomodationCosts Long,
     * transportationCosts Long, foodCosts Long, tourismCosts Long, startDate Date, endDate Date,
     * travelStay String, tourismAttractions List<String>. El conjunto de parámetros de entrada
     * representa el destino.
     * Salida: Response<Boolean>.
     *
     */
    override suspend fun updateDestinationFromFirestore(
        tripId: String,
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
    ): UpdateDestinationResponse {
        return try{
            val user = Firebase.auth.currentUser?.uid
            if (user != null){
                usersRef.document(user)
                    .collection("TripPlannings")
                    .document(tripId)
                    .collection("Destinations")
                    .document(id)
                    .update(
                        mapOf(
                            "CityName" to city.name,
                            "CityPhoto" to city.photo,
                            "Description" to description,
                            "AccomodationCosts" to accomodationCosts,
                            "TransportationCosts" to transportationCosts,
                            "FoodCosts" to foodCosts,
                            "TourismCosts" to tourismCosts,
                            "StartDate" to startDate,
                            "EndDate" to endDate,
                            "TravelStay" to travelStay,
                            "TourismAttractions" to tourismAttractions,
                        )
                    ).await()
                Success(true, "")
            } else {
                Error("User is null")
            }
        } catch (e: Exception) {
            Error(e.message ?: e.toString())
        }
    }

    /**
     * Método público implementado asíncrono deleteDestinationFromFirestore.
     *
     * Método público que elimina un destino de un viaje en una base de datos de Firebase. El
     * método, tras comprobar que el usuario no sea nulo, realiza el borrado del destino en la base
     * de datos y emite una respuesta satisfactoria si esta acción se ha completado con éxito, por
     * contraparte, emite una respuesta fallida si no se ha completado con éxito.
     *
     * Entradas: tripId String, id String. Ids del viaje y del destino.
     * Salidas: Response<Boolean>.
     *
     */
    override suspend fun deleteDestinationFromFirestore(tripId: String, id: String): DeleteDestinationResponse {
        return try{
            val user = Firebase.auth.currentUser?.uid
            if (user != null){
                usersRef.document(user)
                    .collection("TripPlannings")
                    .document(tripId)
                    .collection("Destinations")
                    .document(id)
                    .delete()
                    .await()
                Success(true, "")
            } else {
                Error("User is null")
            }
        } catch (e: Exception) {
            Error(e.message ?: e.toString())
        }
    }
}