package pl.bartoszostrowski.glandroidcrashcourse.service.repository

import pl.bartoszostrowski.glandroidcrashcourse.service.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDatabaseApi {

    @GET("3/movie/{id}")
    fun getMovieById(@Path("id") id: String,
                     @Query("api_key") apiKey: String): Call<Movie>
}
