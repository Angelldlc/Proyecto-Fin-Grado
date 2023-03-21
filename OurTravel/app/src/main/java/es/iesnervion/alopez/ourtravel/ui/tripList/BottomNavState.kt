package es.iesnervion.alopez.ourtravel.ui.tripList

/**
 * Data Class pública BottomNavState.
 *
 * Clase de datos pública que representa el estado en el que se encuentra el elemento
 * TripListBottomNavBar de TripListScreen.
 *
 */
data class BottomNavState constructor(val status: BottomNavStatus) {
    companion object{
        val PENDING = BottomNavState(BottomNavStatus.PENDING)
        val FINALIZED = BottomNavState(BottomNavStatus.FINALIZED)
    }

    enum class BottomNavStatus{
        PENDING,
        FINALIZED
    }

}