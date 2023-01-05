package com.example.tugasakhirbaru.util

import android.app.Activity
import android.content.Intent
import com.example.tugasakhirbaru.view.DetailActivity
import com.example.tugasakhirbaru.view.HomeActivity
import com.example.tugasakhirbaru.view.LoginActivity
import com.example.tugasakhirbaru.view.RegisterActivity

object KotlinExt {
    fun Activity.openLoginActivity() {
        Intent(this, LoginActivity::class.java).run {
            startActivity(this)
        }
        finish()
    }

    fun Activity.openRegisterActivity() {
        Intent(this, RegisterActivity::class.java).run {
            startActivity(this)
        }
    }

    fun Activity.openHomeActivity() {
        Intent(this, HomeActivity::class.java).run {
            startActivity(this)
        }
        finish()
    }

    fun Activity.openDetailActivity() {
        Intent(this, DetailActivity::class.java).run {
            startActivity(this)
        }
    }
}