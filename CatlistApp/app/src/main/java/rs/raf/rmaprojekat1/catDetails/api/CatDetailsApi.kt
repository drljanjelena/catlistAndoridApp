package rs.raf.rmaprojekat1.catDetails.api

import retrofit2.http.GET
import retrofit2.http.Path
import rs.raf.rmaprojekat1.catDetails.api.model.CatDetailsApiModel
import rs.raf.rmaprojekat1.catDetails.api.model.CatImageUrlApiModel

interface CatDetailsApi {

    @GET("breeds/{id}")
    suspend fun getOneCat(
        @Path("id") catId: String,
    ): CatDetailsApiModel

    @GET("images/{id}")
    suspend fun getPhotoURL(
        @Path("id") photoId: String,
    ): CatImageUrlApiModel
}