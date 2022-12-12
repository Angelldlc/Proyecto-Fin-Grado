package es.iesnervion.alopez.ourtravel.data.network.mapper

import es.iesnervion.alopez.ourtravel.data.network.model.CityDTO
import es.iesnervion.alopez.ourtravel.domain.model.City

/**
 * Método público CityDTO.toDomain.
 *
 * Método público que transforma objetos JSON CityDTO provenientes de una llamada API a objetos
 * City para poder trabajar con ellos en las distintas vistas de la aplicación.
 *
 * Entradas: CityDTO.
 * Salidas: City.
 *
 */
fun CityDTO.toDomain() = City(name ?: "", photo ?: "" )