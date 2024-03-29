package es.iesnervion.alopez.ourtravel.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import es.iesnervion.alopez.ourtravel.domain.model.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz LoginRepository.
 *
 */
interface LoginRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun oneTapSignInWithGoogle(): Flow<Response<BeginSignInResult>>

    suspend fun oneTapSignUpWithGoogle(): Flow<Response<BeginSignInResult>>

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Flow<Response<Boolean>>

    suspend fun createUserInFirestore(): Flow<Response<Boolean>>

    suspend fun signOut(): Flow<Response<Boolean>>

    suspend fun revokeAccess(): Flow<Response<Boolean>>

    fun getFirebaseAuthState(): Flow<Boolean>

    fun getDisplayName(): String

    fun getPhotoUrl(): String
}