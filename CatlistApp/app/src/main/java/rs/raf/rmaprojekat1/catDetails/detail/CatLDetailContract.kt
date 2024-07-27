package rs.raf.rmaprojekat1.catDetails.detail

import rs.raf.rmaprojekat1.catDetails.detail.model.CatDetailsUiModel

interface CatLDetailContract {

    data class CatDetailState(
        val loading: Boolean = false,
        val cat: CatDetailsUiModel = CatDetailsUiModel(),
        val imageUrl: String = ""
    )


}

