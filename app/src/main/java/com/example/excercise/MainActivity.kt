package com.example.excercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


object RetrofitClient{

    private var instance:Retrofit?=null
    private var gson= GsonBuilder().setLenient().create()
    private const val BASE_URL="https://api.github.com/"

    //singleTon
    fun getInstance():Retrofit{
        if(instance==null){
            instance=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
        }
        return instance!!
    }

}
//object RetrofitBuilder {
//    var api : API
//
//    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        //addConvertFactory가  GsonConverter 추가해서 JSON 형식을 DTO클래스 형식으로 변환
////오 개꿀인걸
//        api = retrofit.create(API::class.java)
//    }
//}
class MainActivity : AppCompatActivity() {


    interface RetrofitService {
        @GET("/users/gogumaC")
        fun getInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val text = findViewById<TextView>(R.id.textView)
//        CoroutineScope(IO).launch{
//            val url= URL("http://54.180.209.151:3000/api-docs/")
//            val http=url.openConnection() as HttpURLConnection
//            val inputs=http.getInputStream()
//
//            val builder=StringBuilder()
//            val reader= BufferedReader(InputStreamReader(inputs,"UTF-8"))
//            val line:String=reader.readLine()
//            while(line!=null){
//                builder.append(line)
//            }
//
//            val result=builder.toString()
//            withContext(Main){
//                text.setText(result)
//            }
//        }

        //https://jsonplaceholder.typicode.com/posts/1
    }

    val rf = Retrofit.Builder().baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    var regionServer: RetrofitService? = rf.create(RetrofitService::class.java)
}




    //https://velog.io/@tornbolo/Kotlin
//https://gdbagooni.tistory.com/11
data class UserInfo(val login:String,val id:Int,val name:String,val public_repos:Int)