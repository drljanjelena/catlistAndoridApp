package rs.raf.rmaprojekat1.catDetails.detail.model

import rs.raf.rmaprojekat1.catDetails.api.model.CatDetailsApiModel

data class CatDetailsUiModel(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val origin: String = "",
    val temperament: String = "",
    val life_span: String = "",
    val weight: CatDetailsApiModel.Weight = CatDetailsApiModel.Weight("", ""),
    val rare: Int = 0,
    val wikipedia_url: String = "",
    val adaptability: Int = 0,
    val affectionLevel: Int = 0,
    val dogFriendly: Int = 0,
    val energyLevel: Int = 0,
    val intelligence: Int = 0,
    val sheddingLevel: Int = 0,
    val socialNeeds: Int = 0,
    val strangerFriendly: Int = 0,
    val reference_image_id: String = "",
    val imageURL : String = ""
) {

}
