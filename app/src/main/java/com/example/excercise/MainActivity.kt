package com.example.excercise

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.text.SimpleDateFormat
import java.util.*

object RetrofitBuilder {
    var signUp:SignUp
    var signIn:SignIn

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://54.180.209.151:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //addConvertFactory가  GsonConverter 추가해서 JSON 형식을 DTO클래스 형식으로 변환

        //여기는 약간 레트로핏 인스턴스에 기능추가하는 느낌 인터페이스 추가해줌줌
        signUp=retrofit.create(SignUp::class.java)
        signIn=retrofit.create(SignIn::class.java)
    }
}


interface SignUp{
    @Headers(
        //"accept: application/json", ->뭔역할인지는 모르겠다.
        "Content-Type: application/json")
    @POST("api/auth/signUp")
    fun createUser(@Body user:SignUpInfo):Call<SignUpRes>
}

interface SignIn{
    @Headers(
        //"accept: application/json", ->뭔역할인지는 모르겠다.
        "Content-Type: application/json")
    @POST("api/auth/signUp")
    fun signIn(@Body user:SignInInfo):Call<SignInRes>
}
//interface getQuery{
//    @GET("api/community/postings")
//    fun getQuery():Call<SignUpRes>
//}


class MainActivity : AppCompatActivity() {


    val ID="hello"
    val PASSWORD="android"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sp=getSharedPreferences("token",Context.MODE_PRIVATE)
        val tex=sp.getString("token","")
        val spEditor=sp.edit()

        val text = findViewById<TextView>(R.id.textView)



        val sendSignInInfo=SignInInfo()
        RetrofitBuilder.signIn.signIn(sendSignInInfo).enqueue(object:Callback<SignInRes>{
            override fun onResponse(call: Call<SignInRes>, response: Response<SignInRes>) {
                Log.d("response","eerr :"+response.errorBody()!!.string())
                Log.d("response","body : "+response.body().toString())
                //sp쓰기
                //spEditor.putString("token","tempToken").commit()
                //spEditor.putString("token",response.body()?.access_token).commit()
                //sp읽기
                Log.d("response",sp.getString("token","NO TOKEN").toString())

            }

            override fun onFailure(call: Call<SignInRes>, t: Throwable) {

            }
        })
        //enqueue : 인터페이스로부터 함수호출가능 -> enqueue(callback)하면 백그라운드 쓰레드에서 요청수행한후 콜백은 현제스레드에서 처리

     }

}
//실패시
//body respnse body
data class SignUpInfo(val type:String="email",val email:String="bba@gmail.com",val user_id:String="hsello",val user_pw:String="android888",
    val nickname: String="gogumad",val marketing:Permission=Permission(),val birth: Date = SimpleDateFormat("yyyy-MM-dd").parse("1999-02-21"))
data class Permission(val permission:Boolean=false)
data class SignUpRes(var message:String?=null)
data class SignInInfo(val type:String="eamil",val email:String="bba@gmail.com",val user_pw: String="android888",val firebase_token:String="")
data class SignInRes(val staus:Int,val access_token:String,val refresh_token:String?)