package es.iesnervion.alopez.ourtravel.ui.login.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.iesnervion.alopez.ourtravel.R
import es.iesnervion.alopez.ourtravel.ui.login.LoadingState
import es.iesnervion.alopez.ourtravel.ui.login.LoadingState.Companion.IDLE
import es.iesnervion.alopez.ourtravel.ui.login.LoadingState.Companion.LOADING
import es.iesnervion.alopez.ourtravel.ui.login.LoadingState.Status.*
import es.iesnervion.alopez.ourtravel.ui.login.LoginViewModel

@Preview
@Composable
fun LoginScreen(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.loadingState.collectAsState()

    // Equivalent of onActivityResult
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            viewModel.signWithCredential(credential)
        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 1.dp,
                    title = {
                        Text(text = "Login")
                    },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { Firebase.auth.signOut() }) {
                            Icon(
                                imageVector = Icons.Rounded.ExitToApp,
                                contentDescription = null,
                            )
                        }
                    }
                )
                if (state.status == LoadingState.Status.RUNNING) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    val context = LocalContext.current
                    val token = stringResource(R.string.your_web_client_id)

                    OutlinedButton(
                        border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .align(Alignment.End),
                        onClick = {
                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()

                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        },
                        content = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        tint = Color.Unspecified,
                                        painter = painterResource(id = R.drawable.googleg_standard_color_18),
                                        contentDescription = null,
                                    )
                                    Text(
                                        style = MaterialTheme.typography.button,
                                        color = MaterialTheme.colors.onSurface,
                                        text = "Google"
                                    )
                                    Icon(
                                        tint = Color.Transparent,
                                        imageVector = Icons.Default.MailOutline,
                                        contentDescription = null,
                                    )
                                }
                            )
                        }
                    )

                    when(state.status) {
                        SUCCESS -> {
                            Text(text = "Success")
                        }
                        FAILED -> {
                            Text(text = state.msg ?: "Error")
                        }
                        else -> {}
                    }
                }
            )
        }
    )
}











//fun LoginScreen() {
//    val state: LoadingState = IDLE
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        content = {
//            OutlinedButton(border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp), onClick = {
//                    // On click event.
//                },
//                content = {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = if(state == LOADING) Arrangement.Center else Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically,
//                        content = {
//                            if(state == LOADING) {
//                                CircularProgressIndicator()
//                            } else {
//                                Icon(
//                                    tint = Color.Unspecified,
//                                    painter = painterResource(id = R.drawable.googleg_standard_color_18),
//                                    contentDescription = null,
//                                )
//                                Text(
//                                    style = MaterialTheme.typography.button,
//                                    color = MaterialTheme.colors.onSurface,
//                                    text = "Sign in with Google"
//                                )
//                                Icon(
//                                    tint = Color.Transparent,
//                                    imageVector = Icons.Default.MailOutline,
//                                    contentDescription = null,
//                                )
//                            }
//                        }
//                    )
//                })
//
//            when (state.status) {
//                SUCCESS -> {
//                    Text(text = "Success")
//                }
//                FAILED -> {
//                    Text(text = state.msg ?: "Error")
//                }
//                LOGGED_IN -> {
//                    Text(text = "Already Logged In")
//                }
//                else -> {
//                }
//            }
//        }
//    )
//}