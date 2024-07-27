package rs.raf.rmaprojekat1.cats.api

import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.rmaprojekat1.cats.api.model.CatsApiModel

interface CatsApi {

    @GET("breeds")
    suspend fun getAllCats(): List<CatsApiModel>

    @GET("search")
    suspend fun searchCats(
        @Query("q") query: String
    ): List<CatsApiModel>

}