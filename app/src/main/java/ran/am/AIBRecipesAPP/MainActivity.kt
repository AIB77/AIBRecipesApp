package ran.am.AIBRecipesAPP

import AIBRecipesAPP.R
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val responseText = findViewById<View>(R.id.textView) as TextView
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null)
        {
            apiInterface.getRecipes()?.enqueue(object :Callback<List<Recipes.RecipesDetails>>
            {
                override fun onResponse(
                    call: Call<List<Recipes.RecipesDetails>>,
                    response: Response<List<Recipes.RecipesDetails>>) {
                    progressDialog.dismiss()
                    var stringToBePritined:String? = "";
                    for(Recipes in response.body()!!)
                    {
                        stringToBePritined = stringToBePritined +Recipes.title+ "\n"+Recipes.author + "\n"+Recipes.ingredients +"\n"+Recipes.instructions+"\n"
                    }
                    responseText.text= stringToBePritined
                }
                override fun onFailure(call: Call<List<Recipes.RecipesDetails>>, t: Throwable) {

                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_SHORT).show();
                }
            })
        }

    }

    fun addnew(view: android.view.View) {
        intent = Intent(applicationContext, NewRecipes::class.java)
        startActivity(intent)
    }
}