package es.iesnervion.alopez.ourtravel.ui.tripList

data class BottomNavState private constructor(val status: BottomNavStatus) {
    companion object{
        val PENDANT = BottomNavState(BottomNavStatus.PENDANT)
        val FINALIZED = BottomNavState(BottomNavStatus.FINALIZED)
    }

    enum class BottomNavStatus{
        PENDANT,
        FINALIZED
    }
}