package es.iesnervion.alopez.ourtravel.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.gson.Gson
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.destination.composables.DestinationScreen
import es.iesnervion.alopez.ourtravel.ui.login.composables.LoginScreen
import es.iesnervion.alopez.ourtravel.ui.navigation.Screen.*
import es.iesnervion.alopez.ourtravel.ui.searchCity.composables.SearchCityScreen
import es.iesnervion.alopez.ourtravel.ui.tripList.composables.TripListScreen
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.TripPlanningScreen

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
            TripListScreen(
                navigateToLoginScreen = {
                    navController.popBackStack()

                },
                navigateToNewTripPlanningScreen = {
                    val trip = Gson().toJson(TripPlanning())
                    navController.navigate(TripPlanningScreen.route.plus("/?name=&photo="))
                },
                navigateToTripPlanningScreen = {

                    navController.navigate(TripPlanningScreen.route.plus("/").plus(it[0]).plus("?name=")/*.plus("?trip=$trip")*/.plus(it[1]).plus("&photo=").plus(it[2]))
                }
            )
        }
        composable(
            route = TripPlanningScreen.route.plus("/{tripId}?").plus("name={name}&photo={photo}"),
            arguments = listOf(

                navArgument("tripId"){ type = NavType.StringType },
                navArgument("name"){ type = NavType.StringType },
                navArgument("photo"){ type = NavType.StringType },
            ))
        { backStackEntry ->
            val trip = backStackEntry.arguments?.getString("trip").let { json ->
                Gson().fromJson(json, TripPlanning::class.java)
            }
            val id = backStackEntry.arguments?.getString("tripId")
            val name = backStackEntry.arguments?.getString("name")
            val photo = backStackEntry.arguments?.getString("photo")
            requireNotNull(id)
            requireNotNull(name)
            requireNotNull(photo)

            TripPlanningScreen(id, name, photo,
                navigateToTripListScreen = {
                    navController.currentBackStackEntry 
                    navController.clearBackStack(SearchCityScreen.route)
                    navController.popBackStack()
                },
                navigateToDestinationScreen = {
                    val destination = Gson().toJson(it)
                    val idDestination = it.id
                    navController.navigate(DestinationScreen.route.plus("/$idDestination").plus("?destination=$destination"))
                },
                navigateToSearchCityScreen = {
                    navController.navigate(SearchCityScreen.route)
                }
            )
        }
        composable(
            route = DestinationScreen.route.plus("/{destinationId}?destination={destination}"/*cityName={cityName}&cityPhoto={cityPhoto}&description={description}&accomodationCosts={accomodationCosts}&transportationCosts={transportationCosts}&foodCosts={foodCosts}&TourismCosts={}&StartDate={}&EndDate={}&TravelStay={}&TourismAttractions"*/),
            arguments = listOf(
                navArgument("destinationId"){ type = NavType.StringType },
                navArgument("destination"){ type = NavType.StringType }
            )
        ){ backStackEntry ->
            val destination = backStackEntry.arguments?.getString("destination").let { json ->
                 Gson().fromJson(json, Destination::class.java) }
//            requireNotNull(id)

            DestinationScreen(destination = destination,
                navigateToTripPlanningScreen = {
                    navController.clearBackStack(SearchCityScreen.route)
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = SearchCityScreen.route,
//            arguments = listOf(
//                navArgument("cityName"){ type = NavType.StringType },
//                navArgument("cityPhoto"){ type = NavType.StringType }
//            )
        ){ backStackEntry ->
//            val
            SearchCityScreen(
                navigateToDestinationScreen = {
                    val destination = Gson().toJson(it)
                    navController.popBackStack()
                    navController.navigate(DestinationScreen.route.plus("/${it.id}?destination=$destination"))
                }
            )

        }
    }
}