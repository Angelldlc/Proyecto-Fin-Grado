package es.iesnervion.alopez.ourtravel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import es.iesnervion.alopez.ourtravel.ui.login.composables.LoginScreen
import es.iesnervion.alopez.ourtravel.ui.theme.OurTravelTheme

class MainActivity : ComponentActivity() {
    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            OurTravelTheme {
                GoogleLoginView()
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

@Preview
@Composable
fun GoogleLoginView(){
    LoginScreen()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OurTravelTheme {
        Greeting("Android")
    }
}