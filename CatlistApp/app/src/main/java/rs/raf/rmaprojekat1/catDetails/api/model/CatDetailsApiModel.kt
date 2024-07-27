package rs.raf.rmaprojekat1.catDetails.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CatDetailsApiModel(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val origin: String = "",
    val temperament: String = "",
    val life_span: String = "",
    val weight: Weight = Weight("",""),
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
    val reference_image_id: String = ""

) {
    @Serializable
    data class Weight(
        val imperial: String = "",
        val metric: String = ""
    )

}
