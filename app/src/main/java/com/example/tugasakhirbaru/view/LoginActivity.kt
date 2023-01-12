package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.databinding.ActivityLoginBinding
import com.example.tugasakhirbaru.util.KotlinExt.openHomeActivity
import com.example.tugasakhirbaru.util.KotlinExt.openRegisterActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants
import com.example.tugasakhirbaru.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity(), ViewModelListener {
    lateinit var binding: ActivityLoginBinding

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val viewModel: LoginViewModel by lazy {
        LoginViewModel(auth, database, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        if (auth.currentUser != null) {
            openHomeActivity()
        }

        binding.tvRegisText.setOnClickListener {
            openRegisterActivity()
        }
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        if (param == Constants.HOME_PAGE) {
            openHomeActivity()
        }
    }

}