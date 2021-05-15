package com.lukitor.projectandroidfundamentalkotlin2

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.lukitor.projectandroidfundamentalkotlin2.database.DatabaseContract
import com.lukitor.projectandroidfundamentalkotlin2.database.FavoriteHelper
import com.lukitor.projectandroidfundamentalkotlin2.databinding.ActivityDetailUserBinding
import com.lukitor.projectandroidfundamentalkotlin2.entity.Favorite
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.progressBar2.visibility = View.INVISIBLE
        var user: String = ""
        val tipe = intent.getStringExtra("tipe")
        binding.imgback.setOnClickListener { view -> finish() }
        if (tipe == "1") {
            var dataobj = intent.getParcelableExtra<Favorite>("data") as User
            binding.txtDusername.text = dataobj.username
            binding.txtDusername.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)
            binding.txtDusername.setOnClickListener { view ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(dataobj.repository)
                startActivity(intent)
            }
            binding.txtDName.text = dataobj.nama
            binding.txtDusername.text = dataobj.username
            binding.txtDLocation.text = dataobj.location
            binding.txtDcompany.text = dataobj.company
            Glide.with(this).load(dataobj.foto).into(binding.imgDProfile)
            Glide.with(this).load(dataobj.foto).into(binding.imageViewDetail)
            binding.imgFav.setOnClickListener { view ->
                var Favoritehelper = FavoriteHelper.getInstance(this)
                val sum = Favoritehelper.cek(dataobj.username.toString())
                if (sum > 0) {
                    Toast.makeText(applicationContext, "USER ALREADY EXITS", Toast.LENGTH_LONG).show()
                } else {
                    val values = ContentValues()
                    values.put(DatabaseContract.FavoriteColumns.USERNAME, dataobj.username)
                    values.put(DatabaseContract.FavoriteColumns.NAME, dataobj.nama)
                    values.put(DatabaseContract.FavoriteColumns.IMAGE, dataobj.foto)
                    val result = Favoritehelper.insert(values)
                    if (result > 0) Toast.makeText(
                            this,
                            "Success add to the Favorite List",
                            Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(this, "Failed add to the Favorite List", Toast.LENGTH_SHORT).show()
                }
            }
            user = dataobj.username.toString()
        }
        else {
            var dataobj = intent.getParcelableExtra<Favorite>("data") as Favorite
            binding.txtDusername.text = dataobj.username
            binding.txtDusername.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)
            binding.txtDName.text = dataobj.nama
            Glide.with(this).load(dataobj.imgURL).into(binding.imgDProfile)
            Glide.with(this).load(dataobj.imgURL).into(binding.imageViewDetail)
            ListData(dataobj.username.toString())
            user = dataobj.username.toString()
            binding.imgFav.setOnClickListener { view ->
                var Favoritehelper = FavoriteHelper.getInstance(this)
                val sum = Favoritehelper.cek(dataobj.username.toString())
                if (sum > 0) {
                    Toast.makeText(applicationContext, "USER ALREADY EXITS", Toast.LENGTH_LONG).show()
                } else {
                    val values = ContentValues()
                    values.put(DatabaseContract.FavoriteColumns.USERNAME, dataobj.username)
                    values.put(DatabaseContract.FavoriteColumns.NAME, dataobj.nama)
                    values.put(DatabaseContract.FavoriteColumns.IMAGE, dataobj.imgURL)
                    val result = Favoritehelper.insert(values)
                    if (result > 0) Toast.makeText(
                            this,
                            "Success add to the Favorite List",
                            Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(this, "Failed add to the Favorite List", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.NavView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.menu_follower -> {
                    fragment = Follower.newInstance(user)
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_following -> {
                    fragment = Following.newInstance(user)
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        if (savedInstanceState == null) {
            binding.NavView.setSelectedItemId(R.id.menu_following)
        }
    }
    private fun ListData(UsernameUser:String) {
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/users/$UsernameUser", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    var company=responseObject.getString("company")
                    var location=responseObject.getString("location")
                    var repository=responseObject.getString("html_url")
                    if (company=="null"){company="-"}
                    if (location=="null"){location="-"}
                    binding.txtDLocation.text = location
                    binding.txtDcompany.text = company
                    binding.txtDusername.setOnClickListener { view ->
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(repository)
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@DetailUser, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {}
        })
    }
}