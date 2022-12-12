package es.iesnervion.alopez.ourtravel.domain.model

import java.lang.Exception

/**
 * Sealed Class pública Response.
 *
 * Clase sellada pública que representa una respuesta obtenida de una corrutina.
 *
 */
sealed class Response<out T> {

    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ): Response<T>()

    data class Error(
        val message: String
    ): Response<Nothing>()

    data class Failure(
        val e: Exception?
    ): Response<Nothing>()
}
