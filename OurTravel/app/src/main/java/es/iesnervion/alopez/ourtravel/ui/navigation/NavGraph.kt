package es.iesnervion.alopez.ourtravel.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import es.iesnervion.alopez.ourtravel.ui.login.composables.LoginScreen
import es.iesnervion.alopez.ourtravel.ui.navigation.Screen.*
import es.iesnervion.alopez.ourtravel.ui.tripList.composables.TripListScreen
import es.iesnervion.alopez.ourtravel.ui.tripPlaning.composables.TripPlanningScreen

@Composable
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
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
        ) {
            TripListScreen(
                navigateToLoginScreen = {
                    navController.popBackStack()
                    navController.navigate(LoginScreen.route)
                },
                navigateToNewTripPlanningScreen = {
                    navController.navigate(TripPlanningScreen.route.plus("/").plus(null))
                },
                navigateToTripPlanningScreen = {
                    navController.navigate(TripPlanningScreen.route.plus("/").plus(it[0]).plus("?name=").plus(it[1]).plus("&photo=").plus(it[2]))
                }
            )
        }
        composable(
            route = TripPlanningScreen.route.plus("/{tripId}").plus("?name={name}").plus("&photo={photo}"),
            arguments = listOf(navArgument("tripId"){ type = NavType.StringType },
                navArgument("name"){ type = NavType.StringType/*; defaultValue = ""*/ },
                navArgument("photo"){ type = NavType.StringType/*; defaultValue = ""*/ })) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("tripId")
            val name = backStackEntry.arguments?.getString("name")
            val photo = backStackEntry.arguments?.getString("photo")
            requireNotNull(id)
            requireNotNull(name)
            requireNotNull(photo)
            TripPlanningScreen(id, name, photo,
                navigateToTripListScreen = {
                    navController.popBackStack()
//                    navController.navigate(TripListScreen.route)
                },
                navigateToDestinationScreen = {
                    navController.navigate(DestinationScreen.route)
                },
//                navigateBack =
            )
        }
    }
}