package es.iesnervion.alopez.ourtravel.data.repository

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import es.iesnervion.alopez.ourtravel.domain.model.Response.*
import es.iesnervion.alopez.ourtravel.domain.repository.LoginRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named("signInRequest")
    private var signInRequest: BeginSignInRequest,
    @Named("signUpRequest")
    private var signUpRequest: BeginSignInRequest,
    private var signInClient: GoogleSignInClient,
    @Named("usersReference")
    private val usersRef: CollectionReference
) : LoginRepository {

    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle() = flow {
        try {
            emit(Loading)
            val result = oneTapClient.beginSignIn(signInRequest).await()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override suspend fun oneTapSignUpWithGoogle() = flow {
        try {
            emit(Loading)
            val result = oneTapClient.beginSignIn(signUpRequest).await()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential) = flow {
        try {
            emit(Loading)
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser
            emit(Success(isNewUser!!))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override suspend fun createUserInFirestore() = flow {
        try {
            emit(Loading)
            auth.currentUser?.apply {
                usersRef.document(uid).set(
                    mapOf(
                        "id" to uid,
                        "Username" to displayName,
                        "Photo" to photoUrl?.toString()
                    )
                ).await()
                usersRef.document(uid).collection("TripPlannings").document()
                emit(Success(true))
            }
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override fun getFirebaseAuthState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override suspend fun signOut() = flow {
        try {
            emit(Loading)
            auth.signOut()
            oneTapClient.signOut().await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override suspend fun revokeAccess() = flow {
        try {
            emit(Loading)
            auth.currentUser?.apply {
                usersRef.document(uid).delete().await()
                delete().await()
                signInClient.revokeAccess().await()
                oneTapClient.signOut().await()
            }
            emit(Success(true))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override fun getDisplayName() = auth.currentUser?.displayName.toString()

    override fun getPhotoUrl() = auth.currentUser?.photoUrl.toString()
}