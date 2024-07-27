package rs.raf.rmaprojekat1.catDetails.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CatImageUrlApiModel(
    val id: String = "",
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0
)
