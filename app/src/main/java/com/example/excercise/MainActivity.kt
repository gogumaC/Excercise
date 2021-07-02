package com.example.excercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET




object RetrofitBuilder {
    var api : API

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //addConvertFactory가  GsonConverter 추가해서 JSON 형식을 DTO클래스 형식으로 변환
//오 개꿀인걸
        api = retrofit.create(API::class.java)
    }
}
interface API{
    @GET("users/gogumaC")
    fun getUsersInfo(): Call<UserInfo>
}
class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val text = findViewById<TextView>(R.id.textView)


        //https://jsonplaceholder.typicode.com/posts/1
    RetrofitBuilder.api.getUsersInfo().enqueue(object: Callback<UserInfo> {
        override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
            val userInfo=response.body()
            Log.d("response", "${userInfo?.login} ${userInfo?.id} ${userInfo?.name}")
        }

        override fun onFailure(call: Call<UserInfo>, t: Throwable) {
            Log.d("error", t.message.toString())
        }
    })

     }

}



data class UserInfo(val login:String,val id:Int,val name:String,val public_repos:Int)