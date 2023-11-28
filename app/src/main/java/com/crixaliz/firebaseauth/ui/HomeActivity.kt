package com.crixaliz.firebaseauth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.crixaliz.firebaseauth.R
import com.crixaliz.firebaseauth.data.FireBaseRepository
import com.crixaliz.firebaseauth.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.auth.User

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.currentAppUser.observe(this) {
            binding.tvMoney.text = it?.balance.toString()
            binding.tvUser.text = it?.userName
        }

        binding.btLogoutHome.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btAdd.setOnClickListener {
            viewModel.changeMoney(10)
        }

        binding.btRemove.setOnClickListener {
            viewModel.changeMoney(-10)
        }


    }
}