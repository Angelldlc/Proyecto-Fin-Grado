package es.iesnervion.alopez.ourtravel.ui.tripList.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.iesnervion.alopez.ourtravel.ui.theme.LightBlue
import es.iesnervion.alopez.ourtravel.ui.theme.Navy
import es.iesnervion.alopez.ourtravel.ui.tripList.TripListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun TripListSearchView(
    textState: MutableState<TextFieldValue>, scope: CoroutineScope, state: ScaffoldState
//    viewModel: TripListViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = LightBlue
    ) {
        Row() {
            IconButton(onClick = { scope.launch { state.drawerState.open() } }, modifier = Modifier.padding(8.dp)) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(24.dp)/*.clickable { scope.launch { state.drawerState.open() } }*/,
                )
            }
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

@Preview
@Composable
fun TripListSearchViewPreview() {
//    TripListSearchView(textState = remember { mutableStateOf(TextFieldValue("")) }, drawerState = rememberDrawerState(
//        initialValue = DrawerValue.Closed
//    ))
}