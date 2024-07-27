package rs.raf.rmaprojekat1.cats.list

import android.util.Log
import rs.raf.rmaprojekat1.cats.repository.CatRepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import rs.raf.rmaprojekat1.cats.api.model.CatsApiModel
import rs.raf.rmaprojekat1.cats.list.model.CatUiModel
import rs.raf.rmaprojekat1.cats.list.CatListContract.CatListState
import rs.raf.rmaprojekat1.cats.list.CatListContract.CatListUiEvent

import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatListViewModel(
    private val repository: CatRepository = CatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CatListState())
    val state = _state.asStateFlow()
    private fun setState(reducer: CatListState.() -> CatListState) = _state.value.let { _state.value = it.reducer() }

    private val events = MutableSharedFlow<CatListUiEvent>()
    fun setEvent(event: CatListUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        fetchAllCats()
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            events
                .filterIsInstance<CatListUiEvent.SearchQueryChanged>()
                .collect {
                }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    is CatListUiEvent.SearchQueryChanged -> searchCats(event.query)
                    CatListUiEvent.ClearSearch -> searchCats("")
                    CatListUiEvent.CloseSearchMode -> setState { copy(isSearchMode = false) }
                    CatListUiEvent.Dummy -> Unit
                }
            }
        }
    }

    private fun fetchAllCats() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val cats = withContext(Dispatchers.IO) {
                    repository.fetchAllCats().map { it.asCatUiModel() }
                }
                setState { copy(cats = cats) }
            } catch (error: Exception) {
                Log.e("ERROR", "Greska prilikom dohvatanja macaka: ${error.localizedMessage}")
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun searchCats(query: String) {
        viewModelScope.launch {
            setState { copy(loading = true, query = query) }
            try {
                val searchedCats = withContext(Dispatchers.IO) {
                    repository.searchCats(query)
                }.map { it.asCatUiModel() }
                setState { copy(filteredCats = searchedCats) }
            } catch (error: Exception) {
                Log.e("ERROR", "Error searching cats: ${error.localizedMessage}")
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun CatsApiModel.asCatUiModel() = CatUiModel(
        id = this.id,
        name = formatName(this.name),
        alt_names = formatAltNames(this.alt_names),
        description = truncateDescription(this.description),
        temperament = formatTemperament(this.temperament)
    )
}

    private fun truncateDescription(description: String): String {
        return if (description.length <= 250) {
            description
        } else {
            description.substring(0, 247) + "..."
        }
    }

private fun formatTemperament(temperament: String): String {
    val temperamentList = temperament.split(",")

    val randomTemperaments = temperamentList.shuffled().take(3)

    return randomTemperaments.joinToString(", ")
}


    private fun formatAltNames(altNames: String): String {
        return if (altNames.isNotBlank()) {
            altNames
        } else {
            "/"
        }
    }

    private fun formatName(name: String): String {
        return name.toUpperCase()
    }


