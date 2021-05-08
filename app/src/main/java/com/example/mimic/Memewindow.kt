package com.example.mimic

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_memewindow.*

class Memewindow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memewindow)
        memeload()
    }
    var currentMemeUrl: String?=null
    private fun memeload()
    {
        // Instantiate the RequestQueue.
        progressBar.visibility= View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
            currentMemeUrl = response.getString("url")
            Glide.with(this).load(currentMemeUrl).listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility=View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility=View.GONE
                    return false
                }
            }
            ).into(image)
            }, {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show() }
        )

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun share(view: View)
    {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hii there checkout this link $currentMemeUrl")
        startActivity(Intent.createChooser(intent, "Choose any of the Following..."))
    }
    fun next(view: View) {
        memeload()
    }
}