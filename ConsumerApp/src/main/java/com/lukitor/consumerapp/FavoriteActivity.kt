package com.lukitor.consumerapp

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lukitor.consumerapp.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.lukitor.consumerapp.databinding.ActivityFavoriteBinding
import com.lukitor.consumerapp.entity.Favorite
import com.lukitor.consumerapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    lateinit var listData: ArrayList<Favorite>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Glide.with(this).load(R.drawable.favoritegif).into(binding.imageLove)
        binding.imgback3.setOnClickListener{ view ->finish()}
        binding.progressBar3.visibility= View.INVISIBLE
        listData= ArrayList()
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager= LinearLayoutManager(this)
        adapter = FavoriteAdapter(listData)
        binding.rvList.adapter=adapter
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {override fun onChange(self: Boolean) {loadUserFavAsync()}}
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        loadUserFavAsync()
    }
    private fun loadUserFavAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar3.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressBar3.visibility = View.INVISIBLE
            val favUser = deferredNotes.await()
            if (favUser.size > 0) {
                adapter = FavoriteAdapter(favUser)
                binding.rvList.adapter=adapter
            }
            else {
                adapter.listData = ArrayList()
                showSnackbarMessage("There is None Favourite User")
            }
        }
    }
    private fun showSnackbarMessage(message: String) {Snackbar.make(binding.rvList, message, Snackbar.LENGTH_SHORT).show()}
}