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
                    navController.navigate(TripListScreen.route/*.plus("/?username=").plus(it[0]).plus("&photo=").plus(it[1])*/)
                }
            )
        }
        composable(
            route = TripListScreen.route/*.plus("/?username={username}&photo={photo}")*/
        ) {
            TripListScreen(
                navigateToLoginScreen = {
                    navController.popBackStack()
//                    navController.navigate(LoginScreen.route)
                },
                navigateToNewTripPlanningScreen = {
                    navController.navigate(TripPlanningScreen.route.plus("/?name=&photo="))
                },
                navigateToTripPlanningScreen = {
                    navController.navigate(TripPlanningScreen.route.plus("/").plus(it[0]).plus("?name=").plus(it[1]).plus("&photo=").plus(it[2]))
                }
            )
        }
        composable(
            route = TripPlanningScreen.route.plus("/{tripId}?name={name}&photo={photo}"),
            arguments = listOf(navArgument("tripId"){ type = NavType.StringType },
                navArgument("name"){ type = NavType.StringType/*; defaultValue = ""*/ },
                navArgument("photo"){ type = NavType.StringType/*; defaultValue = ""*/ }/*,
                navArgument("destinationId"){ type = NavType.StringType }*/))
        { backStackEntry ->
            val id = backStackEntry.arguments?.getString("tripId")
            val name = backStackEntry.arguments?.getString("name")
            val photo = backStackEntry.arguments?.getString("photo")
//            val destinationId = backStackEntry.arguments?.getString("destinationId")
            requireNotNull(id)
            requireNotNull(name)
            requireNotNull(photo)
//            requireNotNull(destinationId)

            TripPlanningScreen(id, name, photo,
                navigateToTripListScreen = {
                    navController.popBackStack()
//                    navController.navigate(TripListScreen.route)
                },
                navigateToDestinationScreen = {
                    navController.navigate(DestinationScreen.route/*.plus("/$destinationId")*/)
                },
                navigateToSearchCityScreen = {
                    navController.navigate(SearchCityScreen.route)
                }
            )
        }
        composable(
            route = DestinationScreen.route.plus("/{destinationId}"),
            arguments = listOf(navArgument("destinationId"){ type = NavType.StringType })
        ){ backStackEntry ->
            val id = backStackEntry.arguments?.getString("destinationId")
            requireNotNull(id)

            DestinationScreen(id,
                navigateToTripPlanningScreen = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = SearchCityScreen.route
        ){backStackEntry ->
            SearchCityScreen(

            )

        }
    }
}