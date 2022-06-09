package es.iesnervion.alopez.ourtravel.ui.tripList

data class BottomNavState private constructor(val status: BottomNavStatus) {
    companion object{
        val PENDING = BottomNavState(BottomNavStatus.PENDING)
        val FINALIZED = BottomNavState(BottomNavStatus.FINALIZED)
    }

    enum class BottomNavStatus{
        PENDING,
        FINALIZED
    }
}