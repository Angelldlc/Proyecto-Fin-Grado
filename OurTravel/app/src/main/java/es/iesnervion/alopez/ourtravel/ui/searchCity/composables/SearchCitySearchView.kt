package es.iesnervion.alopez.ourtravel.ui.searchCity.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.iesnervion.alopez.ourtravel.ui.theme.LightBlue
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SearchCitySearchView(
    textState: MutableState<TextFieldValue>
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = LightBlue
    ) {
        Row() {
            TextField(
                placeholder = { Text(text = "Buscar...") },
                value = textState.value,
                onValueChange = { value ->
                    textState.value = value
                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(color = Navy, fontSize = 18.sp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                },
                trailingIcon = {
                    if (textState.value != TextFieldValue("")) {
                        IconButton(
                            onClick = {
                                textState.value =
                                    TextFieldValue("")
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(15.dp)
                                    .size(24.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RectangleShape,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Navy,
                    cursorColor = Navy,
                    leadingIconColor = Navy,
                    trailingIconColor = Navy,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    placeholderColor = Color.Gray
                )
            )
        }
    }
}