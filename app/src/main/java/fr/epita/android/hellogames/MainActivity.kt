package fr.epita.android.hellogames

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data : List<GameObject>;

        val callback : Callback<List<GameObject>> = object : Callback<List<GameObject>>{

            override fun onFailure(call: Call<List<GameObject>>, t: Throwable) {
                Log.d("TAG", "WebService fail")
            }

            override fun onResponse(
                call: Call<List<GameObject>>,
                response: Response<List<GameObject>>
            ) {
                if(response.code() == 200){
                    val res = response.body();
                    if (res != null) {

                        var rnd1 = (1..10).random();
                        var rnd2 = (1..10).random();
                        while (rnd1 == rnd2){
                            rnd2 = (1..10).random()
                        }
                        var rnd3 = (1..10).random();
                        while (rnd1 == rnd3 || rnd2 == rnd3){
                            rnd3 = (1..10).random()
                        }
                        var rnd4 = (1..10).random();
                        while (rnd1 == rnd4 || rnd2 == rnd4 || rnd3 == rnd4){
                            rnd4 = (1..10).random()
                        }


                        Glide.with(applicationContext)
                            .load(res[rnd1].picture)
                            .into(game1)

                        Glide.with(applicationContext)
                            .load(res[rnd2].picture)
                            .into(game2)

                        Glide.with(applicationContext)
                            .load(res[rnd3].picture)
                            .into(game3)

                        Glide.with(applicationContext)
                            .load(res[rnd4].picture)
                            .fitCenter()
                            .into(game4)


                        game1.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, DetailsActivity ::class.java)
                            explicitIntent.putExtra("game_id", res[rnd1].id)
                            startActivity(explicitIntent)
                        }

                        game2.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, DetailsActivity ::class.java)
                            explicitIntent.putExtra("game_id", res[rnd2].id)
                            startActivity(explicitIntent)
                        }

                        game3.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, DetailsActivity ::class.java)
                            explicitIntent.putExtra("game_id", res[rnd3].id)
                            startActivity(explicitIntent)
                        }

                        game4.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, DetailsActivity ::class.java)
                            explicitIntent.putExtra("game_id", res[rnd4].id)
                            startActivity(explicitIntent)
                        }
                    }
                }
            }
        }


        val baseURL = "https://androidlessonsapi.herokuapp.com/api/game/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val server : WSInterface = retrofit.create(WSInterface::class.java)
        server.getGamelist().enqueue(callback)
    }
}