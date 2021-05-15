package com.lukitor.projectandroidfundamentalkotlin2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lukitor.projectandroidfundamentalkotlin2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Glide.with(this).load(R.drawable.book).into(binding.imageView2)
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, ListUser::class.java))
            finish()}, 1000)
    }
}