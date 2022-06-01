package es.iesnervion.alopez.ourtravel.ui.login.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.iesnervion.alopez.ourtravel.R

@Composable
fun SignInButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(bottom = 48.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.purple_700)
        )
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.googleg_standard_color_18
            ),
            contentDescription = null
        )
        Text(
            text = "Sign In with Google",
            modifier = Modifier.padding(6.dp),
            fontSize = 18.sp
        )
    }
}