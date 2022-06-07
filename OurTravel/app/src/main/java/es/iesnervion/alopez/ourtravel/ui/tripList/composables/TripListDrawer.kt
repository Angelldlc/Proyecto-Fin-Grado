package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope

@Composable
fun TripListDrawer(modifier: Modifier, scope: CoroutineScope, state: ScaffoldState) {
    Box(modifier = (Modifier.fillMaxSize())) {
        Column(modifier = modifier.fillMaxSize()) {
            val path =
                rememberAsyncImagePainter(model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS66MKD0mUL1dtAsmJRtbiDj3rd-kDR9acoNA&usqp=CAU")
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = path,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(94.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
                Text(text = "Pepito", fontSize = 18.sp)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit Profile",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Filled.Logout,
                        contentDescription = "Logout",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(text = "Logout", fontSize = 18.sp)

            }
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
//    TripListDrawer(Modifier.padding(16.dp))
}