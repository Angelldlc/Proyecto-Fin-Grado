package es.iesnervion.alopez.ourtravel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint
import es.iesnervion.alopez.ourtravel.ui.login.LoginViewModel
import es.iesnervion.alopez.ourtravel.ui.login.composables.LoginScreen
import es.iesnervion.alopez.ourtravel.ui.navigation.NavGraph
import es.iesnervion.alopez.ourtravel.ui.theme.OurTravelTheme
import es.iesnervion.alopez.ourtravel.ui.tripList.composables.TripListScreen
import es.iesnervion.alopez.ourtravel.ui.navigation.Screen.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private lateinit var oneTapClient: SignInClient
//    private lateinit var signUpRequest: BeginSignInRequest

    private lateinit var navController: NavHostController
    private val viewModel by viewModels<LoginViewModel>()

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            OurTravelTheme {
                navController = rememberAnimatedNavController()
                NavGraph(
                    navController = navController
                )
                if(viewModel.isUserAuthenticated) {
                    navController.navigate(TripListScreen.route)
                }
                viewModel.getAuthState()
//                TripListScreen()
            }
        }
//        oneTapClient = Identity.getSignInClient(this)
//        signUpRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setServerClientId(getString(R.string.your_web_client_id))
//                    .setFilterByAuthorizedAccounts(false)
//                    .build())
//            .build()
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview
//@Composable
//fun GoogleLoginView(){
//    LoginScreen()
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OurTravelTheme {
        Greeting("Android")
    }
}