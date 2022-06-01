package es.iesnervion.alopez.ourtravel.ui.navigation

sealed class Screen(val route: String){
    object LoginScreen: Screen("LoginScreen")
    object TripListScreen: Screen("TripListScreen")
}
