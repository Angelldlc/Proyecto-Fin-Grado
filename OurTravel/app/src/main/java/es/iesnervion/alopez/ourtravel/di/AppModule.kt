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
import es.iesnervion.alopez.ourtravel.data.datasource.CitiesDataSource
import es.iesnervion.alopez.ourtravel.data.datasource.CitiesDataSourceImpl
import es.iesnervion.alopez.ourtravel.data.repository.CitiesRepository
import es.iesnervion.alopez.ourtravel.data.repository.DestinationRepositoryImpl
import es.iesnervion.alopez.ourtravel.data.repository.LoginRepositoryImpl
import es.iesnervion.alopez.ourtravel.data.repository.TripRepositoryImpl
import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository
import es.iesnervion.alopez.ourtravel.domain.repository.LoginRepository
import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository
import es.iesnervion.alopez.ourtravel.usecases.UseCases
import es.iesnervion.alopez.ourtravel.usecases.cities.GetCities
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.AddDestination
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.DeleteDestination
import es.iesnervion.alopez.ourtravel.usecases.destinationlist.GetDestinations
import es.iesnervion.alopez.ourtravel.usecases.triplist.AddTrip
import es.iesnervion.alopez.ourtravel.usecases.triplist.DeleteTrip
import es.iesnervion.alopez.ourtravel.usecases.triplist.GetLastTripInsertedId
import es.iesnervion.alopez.ourtravel.usecases.triplist.GetTrips
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    @Named("tripsReference")
    fun provideTripsRef(
        db: FirebaseFirestore
    ) = db.collection("TripPlannings")

    @Provides
    fun provideTripsRepository(
        auth: FirebaseAuth,
        @Named("usersReference")
        usersRef: CollectionReference,
        @Named("tripsReference")
        tripPlanningRef: CollectionReference
    ): TripRepository = TripRepositoryImpl(
        auth = auth,
        usersRef = usersRef,
        tripPlanningRef = tripPlanningRef
    )

    @Provides
    @Named("destinationsReference")
    fun provideDestinationsRef(
        db: FirebaseFirestore
    ) = db.collection("Destinations")

    @Provides
    fun provideDestinationsRepository(
        auth: FirebaseAuth,
        @Named("usersReference")
        usersRef: CollectionReference,
        @Named("tripsReference")
        tripPlanningRef: CollectionReference,
        @Named("destinationsReference")
        destinationRef: CollectionReference
    ): DestinationRepository = DestinationRepositoryImpl(
        auth, usersRef, tripPlanningRef, destinationRef
    )

    @Provides
    fun provideUseCases(
        tripRepo: TripRepository,
        destinationRepo: DestinationRepository,
        citiesRepo : CitiesRepository
    ) = UseCases(
        getTrips = GetTrips(tripRepo),
        getLastTripInsertedId = GetLastTripInsertedId(tripRepo),
        addTrip = AddTrip(tripRepo),
        deleteTrip = DeleteTrip(tripRepo),
        getDestinations = GetDestinations(destinationRepo),
        addDestination = AddDestination(destinationRepo),
        deleteDestination = DeleteDestination(destinationRepo),
        getCities = GetCities(citiesRepo)
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
    ): LoginRepository = LoginRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        signInClient = signInClient,
        usersRef = usersRef
    )

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = /*"https://apicitiesourtravel2.azurewebsites.net/api/"*/ "https://citiesourtravel.azurewebsites.net/api/" //TODO preguntar a fernando por la api


    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideCitiesDataSource(retrofit: Retrofit): CitiesDataSource = CitiesDataSourceImpl(retrofit)

}