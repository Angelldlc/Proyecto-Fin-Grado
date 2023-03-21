package es.iesnervion.alopez.ourtravel.usecases

import es.iesnervion.alopez.ourtravel.usecases.cities.GetCities
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.*
import es.iesnervion.alopez.ourtravel.usecases.triplist.*

data class UseCases(
    val getTrips: GetTrips,
    val getTrip: GetTrip,
    val getLastTripInsertedId: GetLastTripInsertedId,
    val addTrip: AddTrip,
    val updateTrip: UpdateTrip,
    val deleteTrip: DeleteTrip,
    val getDestinations: GetDestinations,
    val getLastDestinationInsertedId: GetLastDestinationInsertedId,
    val addDestination: AddDestination,
    val updateDestination: UpdateDestination,
    val deleteDestination: DeleteDestination,
    val getCities: GetCities
)
