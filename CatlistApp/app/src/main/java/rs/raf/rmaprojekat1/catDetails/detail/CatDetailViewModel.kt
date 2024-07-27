package rs.raf.rmaprojekat1.catDetails.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.raf.rmaprojekat1.catDetails.api.model.CatDetailsApiModel
import rs.raf.rmaprojekat1.catDetails.repository.CatDeatailRepository
import rs.raf.rmaprojekat1.catDetails.detail.CatLDetailContract.CatDetailState
import rs.raf.rmaprojekat1.catDetails.detail.model.CatDetailsUiModel

class CatDetailViewModel(
    private val catId: String,
   // private val photoId: String,
    private val repository: CatDeatailRepository = CatDeatailRepository,
    ) : ViewModel() {

    private val _state = MutableStateFlow(CatDetailState())
    val state = _state.asStateFlow()
    private fun setState(reducer: CatDetailState.() -> CatDetailState) =
        _state.value.let { _state.value = it.reducer() }


    /**    private val _state1 = MutableStateFlow(ImageState())
    val state1 = _state1.asStateFlow()
    private fun setImageState(reducer: ImageState.() -> ImageState) = _state1.value.let { _state1.value = it.reducer() }
     */

    init {
        getOneCat()
    }

    private fun getOneCat() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val cat = withContext(Dispatchers.IO) {
                    repository.getCat(catId = catId)
                }
                Log.d("TEST", "Usao u uzimanje macke $cat")
                setState { copy(cat = cat.asCatDetailiUiModel()) }
                getImageUrl()
            } catch (error: Exception) {
                Log.e("ERROR", "Greska prilikom dohvatanja macke: ${error.localizedMessage}")
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun getImageUrl() {
        viewModelScope.launch {
            try {
                val url = withContext(Dispatchers.IO) {
                    repository.getPhotoUrl(photoId = state.value.cat.reference_image_id)
                }
                Log.d("TEST", "Usao u uzimanje slike $url")
                setState { copy(imageUrl = url.url) }
            } catch (error: Exception) {
                Log.e("ERROR", "Greska prilikom dohvatanja slike: ${error.localizedMessage}")
            }
        }
    }


    private fun CatDetailsApiModel.asCatDetailiUiModel() = CatDetailsUiModel(
        id = this.id,
        name = this.name,
        description = this.description,
        origin = this.origin,
        temperament = this.temperament,
        life_span = this.life_span,
        weight = this.weight,
        rare = this.rare,
        wikipedia_url = this.wikipedia_url,
        adaptability = this.adaptability,
        affectionLevel = this.affectionLevel,
        dogFriendly = this.dogFriendly,
        energyLevel = this.energyLevel,
        intelligence = this.intelligence,
        sheddingLevel = this.sheddingLevel,
        socialNeeds = this.socialNeeds,
        strangerFriendly = this.strangerFriendly,
        reference_image_id = this.reference_image_id,
        imageURL = _state.value.imageUrl
    )

}


