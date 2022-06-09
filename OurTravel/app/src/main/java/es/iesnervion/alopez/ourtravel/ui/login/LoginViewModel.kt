package es.iesnervion.alopez.ourtravel.ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential

import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: LoginRepository,
    val oneTapClient: SignInClient
): ViewModel() {

    val isUserAuthenticated get() = repo.isUserAuthenticatedInFirebase()

    private val _oneTapSignInState = mutableStateOf<Response<BeginSignInResult>>(Success(null))
    val oneTapSignInState: State<Response<BeginSignInResult>> = _oneTapSignInState

    private val _oneTapSignUpState = mutableStateOf<Response<BeginSignInResult>>(Success(null))
    val oneTapSignUpState: State<Response<BeginSignInResult>> = _oneTapSignUpState

    private val _signInState = mutableStateOf<Response<Boolean>>(Success(null))
    val signInState: State<Response<Boolean>> = _signInState

    private val _createUserState = mutableStateOf<Response<Boolean>>(Success(null))
    val createUserState: State<Response<Boolean>> = _createUserState

    private var _userDisplayName: String = repo.getDisplayName()
    val userDisplayName: String = _userDisplayName

    private var _userPhoto: String = repo.getPhotoUrl()
    val userPhoto: String = _userPhoto

    fun getAuthState() = liveData(Dispatchers.IO) {
        repo.getFirebaseAuthState().collect { response ->
            emit(response)
        }
    }

    fun oneTapSignIn() {
        viewModelScope.launch {
            repo.oneTapSignInWithGoogle().collect { response ->
                _oneTapSignInState.value = response
            }
        }
    }

    fun oneTapSignUp() {
        viewModelScope.launch {
            repo.oneTapSignUpWithGoogle().collect { response ->
                _oneTapSignUpState.value = response
            }
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) {
        viewModelScope.launch {
            repo.firebaseSignInWithGoogle(googleCredential).collect { response ->
                _signInState.value = response
            }
        }
    }

    fun createUser() {
        viewModelScope.launch {
            repo.createUserInFirestore().collect { response ->
                _createUserState.value = response
            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            repo.signOut().collect { response ->
                _signInState.value != response
            }
        }
    }

//    fun getDisplayName(){
//        viewModelScope.launch {
//            _userDisplayName = repo.getDisplayName()
//        }
//    }
//
//    fun getUserPhoto(){
//        viewModelScope.launch {
//            _userPhoto = repo.getPhotoUrl()
//        }
//    }
}

//    val loadingState = MutableStateFlow(LoadingState.IDLE)
//
//    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
//        try {
//            loadingState.emit(LoadingState.LOADING)
//            Firebase.auth.signInWithEmailAndPassword(email, password).await()
//            loadingState.emit(LoadingState.LOADED)
//        } catch (e: Exception) {
//            loadingState.emit(LoadingState.error(e.localizedMessage))
//        }
//    }
//
//    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
//        try {
//            loadingState.emit(LoadingState.LOADING)
//            Firebase.auth.signInWithCredential(credential).await()
//            loadingState.emit(LoadingState.LOADED)
//        } catch (e: Exception) {
//            loadingState.emit(LoadingState.error(e.localizedMessage))
//        }
//    }
//}