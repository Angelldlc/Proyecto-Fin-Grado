package es.iesnervion.alopez.ourtravel.usecases.cities

import es.iesnervion.alopez.ourtravel.data.repository.CitiesRepository

class GetCities(
    private val repo: CitiesRepository
){
    suspend operator fun invoke() = repo.getCities()
}