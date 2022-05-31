package es.iesnervion.alopez.ourtravel.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.iesnervion.alopez.ourtravel.R
import es.iesnervion.alopez.ourtravel.data.repository.AuthRepositoryImpl
import es.iesnervion.alopez.ourtravel.data.repository.TripRepositoryImpl
import es.iesnervion.alopez.ourtravel.domain.repository.AuthRepository
import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository
import es.iesnervion.alopez.ourtravel.usecases.triplist.AddTrip
import es.iesnervion.alopez.ourtravel.usecases.triplist.DeleteTrip
import es.iesnervion.alopez.ourtravel.usecases.triplist.GetTrips
import es.iesnervion.alopez.ourtravel.usecases.triplist.UseCases
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    @Named("tripsReference")
    fun provideTripsRef(
        db: FirebaseFirestore
    ) = db.collection("Users").document("Q80KRXzmoOrdItR8h1ib").collection("TripPlannings")

    @Provides
    fun provideTripsRepository(
        @Named("tripsReference")
        tripPlanningRef: CollectionReference
    ): TripRepository = TripRepositoryImpl(tripPlanningRef)

    @Provides
    fun provideUseCases(
        repo: TripRepository
    ) = UseCases(
        getTrips = GetTrips(repo),
        addTrip = AddTrip(repo),
        deleteTrip = DeleteTrip(repo)
    )

    //Auth
    @Provides
    fun provideContext(
        app: Application
    ): Context = app.applicationContext

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Named("usersReference")
    fun provideUsersRef(
        db: FirebaseFirestore
    ) = db.collection("Users")

    @Provides
    fun provideOneTapClient(
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named("signInRequest")
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.your_web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build())
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named("signUpRequest")
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.your_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build())
        .build()

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.your_web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named("signInRequest")
        signInRequest: BeginSignInRequest,
        @Named("signUpRequest")
        signUpRequest: BeginSignInRequest,
        signInClient: GoogleSignInClient,
        @Named("usersReference")
        usersRef: CollectionReference
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        signInClient = signInClient,
        usersRef = usersRef
    )

}