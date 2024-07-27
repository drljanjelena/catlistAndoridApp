package rs.raf.rmaprojekat1.cats.list

import rs.raf.rmaprojekat1.cats.list.model.CatUiModel

interface CatListContract {

    data class CatListState(
        val loading: Boolean = false,
        val query: String = "",
        val isSearchMode: Boolean = false,
        val cats: List<CatUiModel> = emptyList(),
        val filteredCats: List<CatUiModel> = emptyList(),
    )

    sealed class CatListUiEvent {
        data class SearchQueryChanged(val query: String) : CatListUiEvent()
        data object ClearSearch : CatListUiEvent()
        data object CloseSearchMode : CatListUiEvent()
        data object Dummy : CatListUiEvent()
    }
}