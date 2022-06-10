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
import com.google.firebase.Timestamp
import com.google.gson.Gson
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import es.iesnervion.alopez.ourtravel.ui.destination.composables.DestinationScreen
import es.iesnervion.alopez.ourtravel.ui.login.composables.LoginScreen
import es.iesnervion.alopez.ourtravel.ui.navigation.Screen.*
import es.iesnervion.alopez.ourtravel.ui.searchCity.composables.SearchCityScreen
import es.iesnervion.alopez.ourtravel.ui.tripList.composables.TripListScreen
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.TripPlanningScreen
import java.util.*

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
                    navController.navigate(TripListScreen.route/*.plus("/?username=").plus(it[0]).plus("&photo=").plus(it[1])*/)
                }
            )
        }
        composable(
            route = TripListScreen.route/*.plus("/?username={username}&photo={photo}")*/
        ) { backStackEntry ->
            TripListScreen(
                navigateToLoginScreen = {
                    navController.popBackStack()
//                    navController.navigate(LoginScreen.route)

                },
                navigateToNewTripPlanningScreen = {
                    val trip = Gson().toJson(TripPlanning(/*"", "", Timestamp(Date()), Timestamp(Date()), 0, ""*/))
                    navController.navigate(TripPlanningScreen.route.plus("/?name=&photo="))
                },
                navigateToTripPlanningScreen = {
//                    val trip = Gson().toJson(it)
//                    val id = it.id
                    navController.navigate(TripPlanningScreen.route.plus("/").plus(it[0]).plus("?name=")/*.plus("?trip=$trip")*/.plus(it[1]).plus("&photo=").plus(it[2]))
                }
            )
        }
        composable(
            route = TripPlanningScreen.route.plus("/{tripId}?")/*trip={trip}"*/.plus("name={name}&photo={photo}"/*&destinationId={destinationId}*/),
            arguments = listOf(
//                navArgument("trip"){ type = NavType.StringType; defaultValue = "" }
                navArgument("tripId"){ type = NavType.StringType },
                navArgument("name"){ type = NavType.StringType/*; defaultValue = ""*/ },
                navArgument("photo"){ type = NavType.StringType/*; defaultValue = ""*/ },
//                navArgument("destinationId"){ type = NavType.StringType; defaultValue = "" }
            ))
        { backStackEntry ->
            val trip = backStackEntry.arguments?.getString("trip").let { json ->
                Gson().fromJson(json, TripPlanning::class.java)
            }
            val id = backStackEntry.arguments?.getString("tripId")
            val name = backStackEntry.arguments?.getString("name")
            val photo = backStackEntry.arguments?.getString("photo")
//            val destinationId = backStackEntry.arguments?.getString("destinationId")
            requireNotNull(id)
            requireNotNull(name)
            requireNotNull(photo)
//            requireNotNull(destinationId)

            TripPlanningScreen(id, name, photo/*trip*/,
                navigateToTripListScreen = {
                    navController.popBackStack()
//                    navController.navigate(TripListScreen.route)
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
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = SearchCityScreen.route
        ){ backStackEntry ->
            SearchCityScreen(

            )

        }
    }
}