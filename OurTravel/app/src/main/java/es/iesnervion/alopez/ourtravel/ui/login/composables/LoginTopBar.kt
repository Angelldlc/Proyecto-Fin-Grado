package es.iesnervion.alopez.ourtravel.ui.login.composables

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun LoginTopBar(){
    TopAppBar (
        title = {
            Text(
                text = "Login"//TODO Editar
            )
        }
    )
}