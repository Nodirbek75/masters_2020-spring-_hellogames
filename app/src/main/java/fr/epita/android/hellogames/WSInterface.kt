package fr.epita.android.hellogames

import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {
    @GET("list")
    fun getGamelist(): retrofit2.Call<List<GameObject>>

    @GET("details")
    fun getGameDetails(@Query("game_id") id: Int): retrofit2.Call<GameDetailsObject>
}