package es.iesnervion.alopez.ourtravel.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.gson.Gson
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.destination.composables.DestinationScreen
import es.iesnervion.alopez.ourtravel.ui.login.LoginViewModel
import es.iesnervion.alopez.ourtravel.ui.login.composables.LoginScreen
import es.iesnervion.alopez.ourtravel.ui.navigation.Screen.*
import es.iesnervion.alopez.ourtravel.ui.searchCity.composables.SearchCityScreen
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import es.iesnervion.alopez.ourtravel.ui.tripList.composables.TripListScreen
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.TripPlanningScreen

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraph (
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = LoginScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = LoginScreen.route
        ) {
            LoginScreen(
                navigateToTripListScreen = {
                    navController.navigate(TripListScreen.route)
                }
            )
        }
        composable(
            route = TripListScreen.route
        ) { backStackEntry ->

            val parentEntry = remember(backStackEntry){
                navController.getBackStackEntry(LoginScreen.route)
            }
            val parentViewModel = hiltViewModel<LoginViewModel>(parentEntry)

            TripListScreen(parentViewModel,
                navigateToLoginScreen = {
                    navController.popBackStack()

                },

                //TODO Cambiar el siguiente metodo para navegar a la pantalla del viaje, añadirlo, y ya dentro añadir el destino.

                navigateToNewTripPlanningScreen = {
                    navController.navigate(SearchCityScreen.route.plus("/?tripId="))
                },
                navigateToTripPlanningScreen = {
                    val trip = Gson().toJson(it)
                    navController.navigate(TripPlanningScreen.route.plus("/${it.id}").plus("?trip=${trip}"/*&city=*/))
                }
            )
        }
        composable(
            route = TripPlanningScreen.route.plus("/{tripId}?trip={trip}"/*&city={city}*/),
            arguments = listOf(

                navArgument("tripId"){ type = NavType.StringType },
                navArgument("trip"){ type = NavType.StringType },/*
                navArgument("city"){ type = NavType.StringType },*/
            ))
        { backStackEntry ->
            val parentEntry = remember(backStackEntry){
                navController.getBackStackEntry(TripListScreen.route)
            }
            val parentViewModel = hiltViewModel<TripListViewModel>(parentEntry)

            val trip = backStackEntry.arguments?.getString("trip").let { json ->
                Gson().fromJson(json, TripPlanning::class.java)
            }
//            val id = if(trip.id.isNullOrEmpty()) "" else trip.id
            val id = if (trip == null) backStackEntry.arguments?.getString("tripId") else if(trip.id.isNullOrEmpty()) "" else trip.id
            /*val city = backStackEntry.arguments?.getString("city").let { json ->
                Gson().fromJson(json, City::class.java)
            }*/
            requireNotNull(id)

            TripPlanningScreen(parentViewModel, id, trip/*, (city ?: City("",""))*/,
                navigateToTripListScreen = {
                    navController.currentBackStackEntry
                    navController.clearBackStack(SearchCityScreen.route)
                    navController.clearBackStack(DestinationScreen.route)
                    navController.navigateBack(TripListScreen.route, TripPlanningScreen.route.plus("/{tripId}?trip={trip}"/*&city={city}*/))
                },
                navigateToDestinationScreen = { dest, tripId ->
                    val destination = Gson().toJson(dest)
                    val idDestination = dest.id
                    val tripPlanningId = if(tripId.isNullOrEmpty()) "" else tripId
                    navController.navigate(DestinationScreen.route.plus("/$idDestination").plus("?destination=${destination}&tripId=${tripPlanningId}"))
                },
                navigateToSearchCityScreen = {
                    navController.navigate(SearchCityScreen.route.plus("/?tripId=${id}"))
                }
            )
        }
        composable(
            route = DestinationScreen.route.plus("/{destinationId}?destination={destination}&tripId={tripId}"),
            arguments = listOf(
                navArgument("destinationId"){ type = NavType.StringType },
                navArgument("destination"){ type = NavType.StringType },
                navArgument("tripId"){ type = NavType.StringType }
            )
        ){ backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")
            val parentEntry = remember(backStackEntry){
                navController.getBackStackEntry(TripListScreen.route)
            }
            val parentViewModel = hiltViewModel<TripListViewModel>(parentEntry)
            val destination = backStackEntry.arguments?.getString("destination").let { json ->
                 Gson().fromJson(json, Destination::class.java) }

            requireNotNull(tripId)

            DestinationScreen(parentViewModel, destination = destination, tripId,
                navigateToTripPlanningScreen = {
                    val trip = Gson().toJson(it)
                    navController.clearBackStack(SearchCityScreen.route.plus("/?tripId=${tripId}"))
                    navController.navigateBack(TripPlanningScreen.route.plus("/${tripId}?trip=${trip}"/*&city=*/), DestinationScreen.route.plus("/{${destination.id}}?destination=${destination}&tripId=${tripId}"))

                }
            )
        }
        composable(
            route = SearchCityScreen.route.plus("/?tripId={tripId}"),
            arguments = listOf(
                navArgument("tripId"){ type = NavType.StringType }
            )
        ){ backStackEntry ->
            val parentEntry = remember(backStackEntry){
                navController.getBackStackEntry(TripListScreen.route)
            }
            val parentViewModel = hiltViewModel<TripListViewModel>(parentEntry)
            val tripPlanningId = backStackEntry.arguments?.getString("tripId")
            SearchCityScreen(parentViewModel, tripPlanningId,
                navigateToTripPlanningScreenFromSearchCity = { it, tripId ->
                    val city = Gson().toJson(it)
                    val trip = TripPlanning()
                    navController.popBackStack()
                    navController.navigate(TripPlanningScreen.route.plus("/{$tripId}?trip={$trip}"/*&city=${city}*/))
                },
                navigateToDestinationScreenFromSearchCity = { dest, tripId ->
                    val destination = Gson().toJson(dest)
                    val idDestination = dest.id
                    navController.navigate(DestinationScreen.route.plus("/$idDestination").plus("?destination=${destination}&tripId=${tripPlanningId}"))

                }
            )
        }
    }
}

fun NavHostController.navigateBack(
    targetRoute: String,
    currentRoute: String
) {
    val previousRoute = previousBackStackEntry?.destination?.route ?: "null"

    // if the previous route is what we want, just go back
    if (previousRoute == targetRoute) popBackStack()
    // otherwise, we do the navigation explicitly
    else navigate(route = targetRoute) {
        // remove the entire backstack up to this this route, including herself
        popUpTo(route = currentRoute) { inclusive = true }
        launchSingleTop = true
    }
}