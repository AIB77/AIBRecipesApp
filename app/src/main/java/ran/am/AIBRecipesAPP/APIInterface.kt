package ran.am.AIBRecipesAPP

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/recipes/")
    fun getRecipes(): Call<List<Recipes.RecipesDetails>>


    @Headers("Content-Type: application/json")
    @POST("/recipes/")
    fun addRecipes(@Body userData: Recipes.RecipesDetails): Call<Recipes.RecipesDetails>


}