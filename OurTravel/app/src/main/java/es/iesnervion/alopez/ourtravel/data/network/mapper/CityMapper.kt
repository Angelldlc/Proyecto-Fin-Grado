package es.iesnervion.alopez.ourtravel.data.network.mapper

import es.iesnervion.alopez.ourtravel.data.network.model.CityDTO
import es.iesnervion.alopez.ourtravel.domain.model.City

fun CityDTO.toDomain() = City(name ?: "", photo ?: "" )