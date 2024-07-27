package rs.raf.rmaprojekat1.catDetails.detail

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text as Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.SubcomposeAsyncImage
import rs.raf.rmaprojekat1.core.theme.orange

fun NavGraphBuilder.catDetails(
    route: String,
    arguments: List<NamedNavArgument>,
    onClose: () -> Unit,
) = composable(
    route = route,
    arguments = arguments,
) { navBackStackEntry ->
    val catId = navBackStackEntry.arguments?.getString("catId")
        ?: throw IllegalStateException("catId required")

    val catDetailViewModel = viewModel<CatDetailViewModel>(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CatDetailViewModel(catId = catId) as T
            }
        }
    )

    val state = catDetailViewModel.state.collectAsState()

    CatDetailScreen(
        state = state.value,
        onClose = onClose,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailScreen(
    state: CatLDetailContract.CatDetailState,
    onClose: () -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = state.cat.name) },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            if (state.loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues,
                ) {
                    item {
                        SubcomposeAsyncImage(
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                            model = state.imageUrl,
                            contentDescription = "Cat picture",
                            alignment = Alignment.Center,
                        )
                    }

                    item {

                        Column {
                            ListItem(
                                headlineContent = { Text("Description: " + state.cat.description) },
                            )
                            ListItem(
                                headlineContent = { Text("Origin: " + state.cat.origin) },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Localized description",
                                    )
                                }
                            )
                            ListItem(
                                headlineContent = { Text("Temperament: " + state.cat.temperament) },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Localized description",
                                    )
                                }
                            )
                            ListItem(
                                headlineContent = { Text("Weight: " + state.cat.weight.imperial +"Imperial: ${state.cat.weight.imperial}, Metric: ${state.cat.weight.metric}" ) },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Localized description",
                                    )
                                }
                            )
                            ListItem(
                                headlineContent = { Text("Life Span: " + state.cat.life_span) },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Localized description",
                                    )
                                }
                            )
                            ListItem(
                                headlineContent = { Text("Adaptability: " + state.cat.adaptability) },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Localized description",
                                    )
                                }
                            )
                            ListItem(
                                headlineContent = { Text("Affection Level: " + state.cat.affectionLevel.toString()) },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Localized description",
                                    )
                                }
                            )
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                val url = state.cat.wikipedia_url
                                Log.d("Test","URL ZA VIKIPEDIU $url")
                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(urlIntent)
                            },
                            modifier = Modifier.padding(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = orange)

                        ) {
                            Text(text = "Open Wikipedia")
                        }
                    }
                }
            }
        }
    )
}
