package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import es.iesnervion.alopez.ourtravel.ui.login.LoginViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun TripListDrawer(
    viewModel: LoginViewModel,
    navigateToLoginScreen: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Center)) {
            val path =
                rememberAsyncImagePainter(model = viewModel.userPhoto)
            Text(
                text = "Sesión Iniciada como:",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 20.dp)
                    .align(CenterHorizontally)
            )
            Image(
                painter = path,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(154.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
                    .align(CenterHorizontally)
            )
            Text(
                text = viewModel.userDisplayName,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
                    .align(CenterHorizontally)
            )
        }

        Row(modifier = Modifier
            .align(BottomStart)
            .padding(16.dp)
            .clickable {
                viewModel.signOut()
                navigateToLoginScreen()
            }) {
            Text(text = "Cerrar Sesión", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Icon(
                Icons.Filled.Logout,
                contentDescription = "Logout",
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp),
                tint = Color.Red
            )
        }
    }
}