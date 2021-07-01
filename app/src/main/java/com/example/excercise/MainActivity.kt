package com.example.excercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val text=findViewById<TextView>(R.id.textView)
        CoroutineScope(IO).launch{
            val url= URL("http://54.180.209.151:3000/api-docs/")
            val http=url.openConnection() as HttpURLConnection
            val inputs=http.getInputStream()

            val builder=StringBuilder()
            val reader= BufferedReader(InputStreamReader(inputs,"UTF-8"))
            val line:String=reader.readLine()
            while(line!=null){
                builder.append(line)
            }

            val result=builder.toString()
            withContext(Main){
                text.setText(result)
            }
        }
    }

}