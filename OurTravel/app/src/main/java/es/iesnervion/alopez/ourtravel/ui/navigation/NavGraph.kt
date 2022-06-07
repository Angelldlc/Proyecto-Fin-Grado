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
                    navController.navigate(TripPlanningScreen.route.plus("/").plus(it))
                }
            )
        }
        composable(
            route = TripPlanningScreen.route.plus("/{tripId}"),
            arguments = listOf(navArgument("tripId"){ type = NavType.StringType })) { b ->
            val id = b.arguments?.getString("tripId")
            requireNotNull(id)
            TripPlanningScreen(id,
                navigateToTripListScreen = {
                    navController.popBackStack()
                    navController.navigate(TripListScreen.route)
                },
                navigateToDestinationScreen = {
                    navController.navigate(DestinationScreen.route)
                },
//                navigateBack =
            )
        }
    }
}