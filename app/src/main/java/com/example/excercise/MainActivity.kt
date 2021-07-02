package com.example.excercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


object RetrofitBuilder {
    var signUp:SignUp

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://54.180.209.151:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //addConvertFactory가  GsonConverter 추가해서 JSON 형식을 DTO클래스 형식으로 변환

        //여기는 약간 레트로핏 인스턴스에 기능추가하는 느낌 인터페이스 추가해줌줌
        signUp=retrofit.create(SignUp::class.java)
    }
}


interface SignUp{
    @POST("api/auth/signUp")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun createUser(@Body user:SignUpInfo):Call<SignUpRes>
}


class MainActivity : AppCompatActivity() {
    val ID="hello"
    val PASSWORD="android"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val text = findViewById<TextView>(R.id.textView)

        val sendSignUpInfo=SignUpInfo()
        RetrofitBuilder.signUp.createUser(sendSignUpInfo).enqueue(object:Callback<SignUpRes>{
            override fun onResponse(call: Call<SignUpRes>, response: Response<SignUpRes>) {
               // Log.d("response","sign in successed")
                Log.d("response",response.toString())
                Log.d("response",response.body().toString())
                Log.d("response",response.message())
               //Log.d("response",response.message())
                if(!response.body().toString().isEmpty()){
                    text.setText(response.body()?.message)
                }

            }

            override fun onFailure(call: Call<SignUpRes>, t: Throwable) {
                Log.d("response","sign in failed")
            }
        })
        //enqueue : 인터페이스로부터 함수호출가능 -> enqueue(callback)하면 백그라운드 쓰레드에서 요청수행한후 콜백은 현제스레드에서 처리



     }

}


data class SignUpInfo(val type:String="email",val email:String="aaa@gmail.com",val user_id:String="hello",val user_pw:String="android",
    val nickname: String="gogumac",val marketing:Marketing=Marketing(),val birth:String="")
data class Marketing(val permission:Boolean=false)
data class SignUpRes(var message:String?=null)
