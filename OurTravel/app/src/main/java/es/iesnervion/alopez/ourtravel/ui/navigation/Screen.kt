package es.iesnervion.alopez.ourtravel.ui.navigation

/**
 * Sealed Class pública Screen.
 *
 * Clase sellada que contiene las distintas pantallas por las que se va a navegar en la aplicación.
 *
 */
sealed class Screen(val route: String){
    object LoginScreen: Screen("LoginScreen")
    object TripListScreen: Screen("TripListScreen")
    object TripPlanningScreen: Screen("TripPlanningScreen")
    object DestinationScreen: Screen("DestinationScreen")
    object SearchCityScreen: Screen("SearchCityScreen")
}

