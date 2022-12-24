package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.databinding.ActivityRegisterBinding
import com.example.tugasakhirbaru.util.KotlinExt.openLoginActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants.LOGIN_PAGE
import com.example.tugasakhirbaru.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity(), ViewModelListener {
    lateinit var binding: ActivityRegisterBinding

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = viewModel
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        if (param == LOGIN_PAGE) {
            openLoginActivity()
        }
    }
}