package com.crixaliz.firebaseauth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crixaliz.firebaseauth.data.FireBaseRepository
import com.crixaliz.firebaseauth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val repo = FireBaseRepository()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo.currentAuthUser.observe(this) {
            Log.d("LOGIN", it?.uid.toString())
            if (it != null) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}