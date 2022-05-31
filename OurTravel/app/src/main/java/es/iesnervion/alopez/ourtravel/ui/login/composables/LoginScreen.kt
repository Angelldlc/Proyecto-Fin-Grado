package es.iesnervion.alopez.ourtravel.ui.login.composables

//import android.annotation.SuppressLint
//import android.util.Log
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.MailOutline
//import androidx.compose.material.icons.rounded.ArrowBack
//import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
import es.iesnervion.alopez.ourtravel.R
//import es.iesnervion.alopez.ourtravel.ui.login.LoadingState
//import es.iesnervion.alopez.ourtravel.ui.login.LoadingState.Companion.IDLE
//import es.iesnervion.alopez.ourtravel.ui.login.LoadingState.Companion.LOADING
//import es.iesnervion.alopez.ourtravel.ui.login.LoadingState.Status.*
import es.iesnervion.alopez.ourtravel.ui.login.LoginViewModel
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.GoogleAuthProvider.getCredential
//import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.*


//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            viewModel.signInWithGoogle(credential)
        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }

    Scaffold(
        topBar = {
//            AuthTopBar()
        },
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = BottomCenter
            ) {
                val context = LocalContext.current
                    val token = stringResource(R.string.your_web_client_id)
                Button(
                    onClick = { val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()

                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            launcher.launch(googleSignInClient.signInIntent)
                         },
                    modifier = Modifier.padding(bottom = 48.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.purple_700)
                    )
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.googleg_standard_color_18
                        ),
                        contentDescription = null
                    )
                    Text(
                        text = "SIGN_IN_WITH_GOOGLE",
                        modifier = Modifier.padding(6.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
    )

//    val launcher =  rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
//        if (result.resultCode == RESULT_OK) {
//            try {
//                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
//                val googleIdToken = credentials.googleIdToken
//                val googleCredentials = getCredential(googleIdToken, null)
//                viewModel.signInWithGoogle(googleCredentials)
//            } catch (it: ApiException) {
//                print(it)
//            }
//        }
//    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent.fillInIntent)
    }

    when(val oneTapSignInResponse = viewModel.oneTapSignInState.value) {
        is Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        is Success -> {
            oneTapSignInResponse.data?.let {
                LaunchedEffect(it) {
                    launch(it)
                }
            }
        }
        is Failure -> {
            oneTapSignInResponse.e?.let {
                LaunchedEffect(Unit) {
                    print(it)
                    if (it.message == "SIGN_IN_ERROR_MESSAGE") {
                        viewModel.oneTapSignUp()
                    }
                }
            }
        }
    }

    when(val oneTapSignUpResponse = viewModel.oneTapSignUpState.value) {
        is Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        is Success -> {
            oneTapSignUpResponse.data?.let {
                LaunchedEffect(it) {
                    launch(it)
                }
            }
        }
        is Failure -> oneTapSignUpResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    when(val signInResponse = viewModel.signInState.value) {
        is Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        is Success -> {
            signInResponse.data?.let { isNewUser ->
                if (isNewUser) {
                    LaunchedEffect(isNewUser) {
                        viewModel.createUser()
                    }
                } else {
//                    navigateToProfileScreen()
                }
            }
        }
        is Failure -> signInResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    when(val createUserResponse = viewModel.createUserState.value) {
        is Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        is Success -> {
            createUserResponse.data?.let { isUserCreated ->
                if (isUserCreated) {
//                    navigateToProfileScreen()
                    Text(text = "Success")
                }
            }
        }
        is Failure -> createUserResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }
}











//}
//
//    var userEmail by remember { mutableStateOf("") }
//    var userPassword by remember { mutableStateOf("") }
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    val state by viewModel.loadingState.collectAsState()
//
//    // Equivalent of onActivityResult
//    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
//        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
//        try {
//            val account = task.getResult(ApiException::class.java)!!
//            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
//            viewModel.signWithCredential(credential)
//        } catch (e: ApiException) {
//            Log.w("TAG", "Google sign in failed", e)
//        }
//    }
//
//    Scaffold(
//        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
//        topBar = {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                TopAppBar(
//                    backgroundColor = Color.White,
//                    elevation = 1.dp,
//                    title = {
//                        Text(text = "Login")
//                    },
//                    navigationIcon = {
//                        IconButton(onClick = { /*TODO*/ }) {
//                            Icon(
//                                imageVector = Icons.Rounded.ArrowBack,
//                                contentDescription = null,
//                            )
//                        }
//                    },
//                    actions = {
//                        IconButton(onClick = { Firebase.auth.signOut() }) {
//                            Icon(
//                                imageVector = Icons.Rounded.ExitToApp,
//                                contentDescription = null,
//                            )
//                        }
//                    }
//                )
//                if (state.status == LoadingState.Status.RUNNING) {
//                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//                }
//            }
//        },
//        content = {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(24.dp),
//                verticalArrangement = Arrangement.spacedBy(18.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                content = {
//
//                    val context = LocalContext.current
//                    val token = stringResource(R.string.your_web_client_id)
//
//                    OutlinedButton(
//                        border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp)
//                            .align(Alignment.End),
//                        onClick = {
//                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                .requestIdToken(token)
//                                .requestEmail()
//                                .build()
//
//                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
//                            launcher.launch(googleSignInClient.signInIntent)
//                        },
//                        content = {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically,
//                                content = {
//                                    Icon(
//                                        tint = Color.Unspecified,
//                                        painter = painterResource(id = R.drawable.googleg_standard_color_18),
//                                        contentDescription = null,
//                                    )
//                                    Text(
//                                        style = MaterialTheme.typography.button,
//                                        color = MaterialTheme.colors.onSurface,
//                                        text = "Google"
//                                    )
//                                    Icon(
//                                        tint = Color.Transparent,
//                                        imageVector = Icons.Default.MailOutline,
//                                        contentDescription = null,
//                                    )
//                                }
//                            )
//                        }
//                    )
//
//                    when(state.status) {
//                        SUCCESS -> {
//                            Text(text = "Success")
//                        }
//                        FAILED -> {
//                            Text(text = state.msg ?: "Error")
//                        }
//                        else -> {}
//                    }
//                }
//            )
//        }
//    )
//}











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