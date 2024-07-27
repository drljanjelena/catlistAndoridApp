package rs.raf.rmaprojekat1.cats.repository

import rs.raf.rmaprojekat1.cats.api.CatsApi
import rs.raf.rmaprojekat1.cats.api.model.CatsApiModel
import rs.raf.rmaprojekat1.networking.retrofit

object CatRepository {
    private val catsApi: CatsApi = retrofit.create(CatsApi::class.java)

    suspend fun fetchAllCats(): List<CatsApiModel> {
        return catsApi.getAllCats();
    }

    suspend fun searchCats(query: String): List<CatsApiModel> {
        return catsApi.searchCats(query)
    }
}
