package com.example.shop

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject

class SingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        //GEt the preference
        val prefs: SharedPreferences = this.getSharedPreferences("shop", Context.MODE_PRIVATE)

        val prodname = findViewById(R.id.p_name) as TextView
        val prodesc = findViewById(R.id.p_desc) as TextView
        val prodcost = findViewById(R.id.p_cost) as TextView
        val img= findViewById(R.id.img_url) as ImageView



        //get thedata from the preference
        val flashname = prefs.getString("prod_name","")
        val flashdesc = prefs.getString("prod_desc","")
        val flashcost = prefs.getString("prod_cost","")
        val flashimg = prefs.getString("img_url","")

        //replace the current views with the data from the preference
        prodname.text = flashname
        prodesc.text = flashdesc
        prodcost.text = flashcost


        Glide.with(applicationContext).load( flashimg)
            .apply(RequestOptions().centerCrop())
            .into(img)
        val progressbar = findViewById(R.id.progressbar) as ProgressBar
        progressbar.visibility = View.GONE
        val phone = findViewById<EditText>(R.id.phone)
        val pay = findViewById<Button>(R.id.pay)


        pay.setOnClickListener{
            progressbar.visibility = View.VISIBLE
            //initialise loop
            val client = AsyncHttpClient(true,80,443)
            //create a json object
            val json = JSONObject()
            //create the data you are sending to json
            json.put("amount","10000")
            json.put("phone",phone.text.toString())
            val body = StringEntity(json.toString())
            client.post(this,"https://modcom.pythonanywhere.com/mpesa_payment",body,"application/json",object:JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray?
                ) {
                    Toast.makeText(applicationContext,"paid succesfully",Toast.LENGTH_LONG).show()
                    progressbar.visibility = View.GONE

                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONArray?
                ) {
                    Toast.makeText(applicationContext,"error during payment",Toast.LENGTH_LONG).show()
                    progressbar.visibility = View.GONE
                }

            })
        }

    }
}