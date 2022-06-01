package es.iesnervion.alopez.ourtravel.ui.login.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.login.LoginViewModel

@Composable
fun LoginContent(padding: PaddingValues,
                 viewModel: LoginViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = BottomCenter
    ) {
        SignInButton {
            viewModel.oneTapSignIn()
        }
    }
}