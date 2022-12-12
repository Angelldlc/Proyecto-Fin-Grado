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

/**
 * Clase pública LoginRepositoryImpl.
 *
 * Clase encargada de gestionar el inicio de sesión de la aplicación.
 *
 */
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

    /**
     * Método público implementado isUserAuthenticatedInFirebase.
     *
     * Método público que devuelve si un usuario esta actualmente auntenticado en Firebase.
     *
     * Entradas: void.
     * Salidas: Boolean
     *
     */
    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    /**
     * Método público implementado asíncrono oneTapSignInWithGoogle.
     *
     * Método publico que permite al usuario iniciar sesión con su cuenta de Google con One Tap.
     * El método devuelve una respuesta satisfactoria si se ejecuta correctamente y una fallida en
     * caso contrario.
     *
     * Entradas: void.
     * Salidas: Response<BeginSignInResult>.
     *
     */
    override suspend fun oneTapSignInWithGoogle() = flow {
        try {
            emit(Loading)
            val result = oneTapClient.beginSignIn(signInRequest).await()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    /**
     * Método público implementado asíncrono oneTapSignUpWithGoogle.
     *
     * Método publico que permite al usuario registrarse con su cuenta de Google con One Tap. El
     * método devuelve una respuesta satisfactoria si se ejecuta correctamente y una fallida en
     * caso contrario.
     *
     * Entradas: void.
     * Salidas: Response<BeginSignInResult>.
     *
     */
    override suspend fun oneTapSignUpWithGoogle() = flow {
        try {
            emit(Loading)
            val result = oneTapClient.beginSignIn(signUpRequest).await()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    /**
     * Método público implementado asíncrono firebaseSignInWithGoogle
     *
     * Método público que inicia sesión en Firebase con la cuenta de Google del usuario. El método
     * devuelve una respuesta satisfactoria si se ejecuta correctamente y una fallida en
     * caso contrario.
     *
     * Entradas: googleCredential AuthCredential. Credenciales del usuario.
     * Salidas: Response<Boolean>
     *
     */
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

    /**
     * Método público implementado asíncrono createUserInFirestore.
     *
     * Método público que crea un usuario en la base de datos de Firebase. El método devuelve una
     * respuesta satisfactoria si se ejecuta correctamente y una fallida en caso contrario.
     *
     * Entradas: void.
     * Salidas: Response<Boolean>
     *
     */
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

    /**
     * Método público implementado getFirebaseAuthState.
     *
     * Método público que devuelve el estado de la autenticación del usuario de Firebase. El método
     * devuelve true si el usuario actual es nulo y false en caso contrario.
     *
     * Entradas: void.
     * Salidas: Boolean.
     *
     */
    override fun getFirebaseAuthState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    /**
     * Método público implementado asíncrono signOut.
     *
     * Método público que realiza la desconexión del usuario de la aplicación. El método devuelve
     * una respuesta satisfactoria si se ejecuta correctamente y una fallida en caso contrario.
     *
     * Entradas: void.
     * Salidas: Response<Boolean>.
     *
     */
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

    /**
     * Método público implementado asíncrono revokeAccess.
     *
     * Método público que revoca el accesso a las aplicación al usuario. El método devuelve una
     * respuesta satisfactoria si se ejecuta correctamente y una fallida en caso contrario.
     *
     * Entradas: void.
     * Salidas: Response<Boolean>.
     *
     */
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

    /**
     * Método público implementado getDisplayName
     *
     * Método público que devuelve el nombre de usuario.
     *
     * Entradas: void.
     * Salidas: String.
     *
     */
    override fun getDisplayName() = auth.currentUser?.displayName.toString()

    /**
     * Método público implementado getPhotoUrl
     *
     * Método público que devuelve la foto del usuario.
     *
     * Entradas: void.
     * Salidas: String.
     *
     */
    override fun getPhotoUrl() = auth.currentUser?.photoUrl.toString()
}