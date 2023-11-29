package com.crixaliz.firebaseauth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.crixaliz.firebaseauth.R
import com.crixaliz.firebaseauth.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: UserViewModel by viewModels()
    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        binding.ivProfileImage.setImageURI(it)
        if(it != null) viewModel.uploadImage(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.currentAppUser.observe(this) {
            binding.tvMoney.text = it?.balance.toString()
            binding.tvUser.text = it?.userName
            Glide.with(this).load(it?.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.ivProfileImage)
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

        binding.ivProfileImage.setOnClickListener {
            imagePicker.launch("image/*")
        }


    }
}