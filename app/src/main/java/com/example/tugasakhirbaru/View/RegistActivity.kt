package com.example.tugasakhirbaru.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.databinding.RegistrationActivityBinding
import com.example.tugasakhirbaru.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistActivity : AppCompatActivity(), RegisterViewModel.Listener {
    lateinit var binding: RegistrationActivityBinding

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val viewModel: RegisterViewModel by lazy {
        RegisterViewModel(auth, database, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.registration_activity)
        binding.viewModel = viewModel
    }

    override fun showMessage(message: String, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        if (param == RegisterViewModel.LOGIN_PAGE) {
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}