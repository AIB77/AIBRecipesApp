package ran.am.AIBRecipesAPP

import AIBRecipesAPP.R
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewRecipes : AppCompatActivity() {
    lateinit var TheTitle: EditText
    lateinit var Author:EditText
    lateinit var Ingredents:EditText
    lateinit var Instruction:EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        val savebtn = findViewById<View>(R.id.buttonSave) as Button

        savebtn.setOnClickListener {

            var f = Recipes.RecipesDetails(TheTitle.text.toString(), Author.text.toString(),Ingredents.text.toString(),Instruction.text.toString())

            addSingleuser(f, onResult = {
                TheTitle.setText("")
                Author.setText("")
                Ingredents.setText("")
                Instruction.setText("")

                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
            })
        }
    }

    private fun addSingleuser(f: Recipes.RecipesDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@NewRecipes)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.addRecipes(f).enqueue(object : Callback<Recipes.RecipesDetails> {
                override fun onResponse(
                    call: Call<Recipes.RecipesDetails>,
                    response: Response<Recipes.RecipesDetails>
                ) {

                    onResult()
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<Recipes.RecipesDetails>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }

    fun addnew(view: android.view.View) {
        intent = Intent(applicationContext, NewRecipes::class.java)
        startActivity(intent)
    }

    fun viewusers(view: android.view.View) {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}