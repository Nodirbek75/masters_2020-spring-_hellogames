package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val callback : Callback<GameDetailsObject> = object : Callback<GameDetailsObject>{
            override fun onFailure(call: Call<GameDetailsObject>, t: Throwable) {
                Log.d("TAG", "WebService fail")
            }

            override fun onResponse(
                call: Call<GameDetailsObject>,
                response: Response<GameDetailsObject>
            ) {
                if(response.code() == 200){
                    val res = response.body();
                    if(res != null){
                        Log.d("TAG", res.id.toString())
                        Glide
                            .with(applicationContext)
                            .load(res.picture)
                            .fitCenter()
                            .into(mainImg)
                        name.text = res.name
                        type.text = res.type
                        NbPlayers.text = res.players.toString()
                        year.text = res.year.toString()
                        info.text = res.description_en
                        more.setOnClickListener{
                            val url = res.url
                            val implicitIntent = Intent(Intent.ACTION_VIEW)
                            implicitIntent.data = Uri.parse(url)
                            startActivity(implicitIntent)
                        }
                    }
                }
            }
        }

        val id = intent.getIntExtra("game_id", 0)
        Log.d("TAG","FUCK " +  id.toString())
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/game/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val server : WSInterface = retrofit.create(WSInterface::class.java)
        server.getGameDetails(id).enqueue(callback)
    }
}