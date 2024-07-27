package rs.raf.rmaprojekat1.cats.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import rs.raf.rmaprojekat1.cats.list.CatListContract.CatListState
import rs.raf.rmaprojekat1.cats.list.CatListContract.CatListUiEvent
import rs.raf.rmaprojekat1.cats.list.model.CatUiModel
import rs.raf.rmaprojekat1.core.theme.kajsija
import rs.raf.rmaprojekat1.core.theme.orange

fun NavGraphBuilder.cats(
    route: String,
    onCatClick: (String) -> Unit,
) = composable(
    route = route
) {
    val catListViewModel = viewModel<CatListViewModel>()
    val state = catListViewModel.state.collectAsState()
    CatListScreen(
        state = state.value,
        eventPublisher = {
            catListViewModel.setEvent(it)
        },
        onCatClick = onCatClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CatListScreen(
    state: CatListState,
    eventPublisher: (uiEvent: CatListUiEvent) -> Unit,
    onCatClick: (String) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var searchQuery by remember { mutableStateOf("") }
    var isSearchExpanded by remember { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = orange,
                ),
                title = {
                    Text(
                        "Learn about cat breeds",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { newValue ->
                            searchQuery = newValue
                        },
                        label = { Text("Search") },
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    IconButton(
                        onClick = {
                            eventPublisher(CatListUiEvent.SearchQueryChanged(searchQuery))
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
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
                    items(
                        items = state.cats,
                        key = { it.id },
                    ) { cat ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .clickable { onCatClick(cat.id) },
                            colors = CardDefaults.cardColors(
                                containerColor = kajsija
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = cat.name,
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Alternative names " + "(" + cat.alt_names + ")",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Temperament",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Left
                                )
                                FlowRow(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .align(Alignment.Start)
                                ) {
                                    cat.temperament.split(",").forEach { temperament ->
                                        SuggestionChip(
                                            modifier = Modifier.padding(1.dp),
                                            onClick = {},
                                            label = {
                                                Text(
                                                    text = temperament.trim(),
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                                                )
                                            }
                                        )
                                    }
                                }
                                Text(
                                    text = "Description:",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                Text(
                                    text = cat.description,
                                    style = MaterialTheme.typography.labelSmall,
                                   // color = grey,
                                    modifier = Modifier.fillMaxWidth(),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                            }
                        }
                    }
                }
            }
        }
    )
}

class UserListStateParameterProvider : PreviewParameterProvider<CatListState> {
    override val values: Sequence<CatListState> = sequenceOf(
        CatListState(
            loading = true,
        ),
        CatListState(
            loading = false,
        ),
        CatListState(
            loading = false,
            cats = listOf(
                CatUiModel(id = "id1", name = "MACKA 1", alt_names = "name1,name2,name3", description = "opisMace", temperament = "Playful Curious,Friendly,Adventurous"),
                CatUiModel(id = "id2", name = "MACKA 2", alt_names = "name,ime,imence", description = "opisMace", temperament  = "Playful,Curious,Friendly,Adventurous"),
                CatUiModel(id = "id3", name = "MACKA 3", alt_names ="a,b", description = "opisMace", temperament  = "Playful Curious,Friendly,Adventurous"),
                ),
        ),
    )
}


@Preview
@Composable
private fun PreviewUserList(
    @PreviewParameter(UserListStateParameterProvider::class) userListState: CatListState,
) {
    CatListScreen(
        state = userListState,
        eventPublisher = {},
        onCatClick = {},
    )
}
