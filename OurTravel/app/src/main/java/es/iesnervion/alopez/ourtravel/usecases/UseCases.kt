package es.iesnervion.alopez.ourtravel.usecases

import es.iesnervion.alopez.ourtravel.usecases.cities.GetCities
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.AddDestination
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.DeleteDestination
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.GetDestinations
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.UpdateDestination
import es.iesnervion.alopez.ourtravel.usecases.triplist.*

data class UseCases(
    val getTrips: GetTrips,
    val getLastTripInsertedId: GetLastTripInsertedId,
    val addTrip: AddTrip,
    val updateTrip: UpdateTrip,
    val deleteTrip: DeleteTrip,
    val getDestinations: GetDestinations,
    val addDestination: AddDestination,
    val updateDestination: UpdateDestination,
    val deleteDestination: DeleteDestination,
    val getCities: GetCities
)
