package rs.raf.rmaprojekat1.catDetails.repository

import rs.raf.rmaprojekat1.catDetails.api.CatDetailsApi
import rs.raf.rmaprojekat1.networking.retrofit

object CatDeatailRepository {

    private val catDetailsApi : CatDetailsApi = retrofit.create(CatDetailsApi::class.java)

    suspend fun getCat(catId : String) = catDetailsApi.getOneCat(catId = catId)

    suspend fun getPhotoUrl(photoId : String) = catDetailsApi.getPhotoURL(photoId = photoId)
}