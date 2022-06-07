package es.iesnervion.alopez.ourtravel.usecases

import es.iesnervion.alopez.ourtravel.usecases.destinationlist.AddDestination
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.DeleteDestination
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.GetDestinations
import es.iesnervion.alopez.ourtravel.usecases.triplist.AddTrip
import es.iesnervion.alopez.ourtravel.usecases.triplist.DeleteTrip
import es.iesnervion.alopez.ourtravel.usecases.triplist.GetTrips

data class UseCases(
    val getTrips: GetTrips,
    val addTrip: AddTrip,
    val deleteTrip: DeleteTrip,
    val getDestinations: GetDestinations,
    val addDestination: AddDestination,
    val deleteDestination: DeleteDestination
)
