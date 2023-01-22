package com.example.tugasakhirbaru.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.tugasakhirbaru.model.ComponentChecklist
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.MENU_EXTRA
import com.example.tugasakhirbaru.view.*

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

    fun Context.openDetailActivity(itemExtra: Menu) {
        Intent(this, DetailMenuActivity::class.java).run {
            putExtra(MENU_EXTRA, itemExtra)
            startActivity(this)
        }
    }


    fun Activity.openProfileActivity() {
        Intent(this, ProfileActivity::class.java).run {
            startActivity(this)
        }
    }

    fun Activity.openEditProfileActivity() {
        Intent(this, EditProfileActivity::class.java).run {
            startActivity(this)
        }
    }

    fun Context.openEditIngredient(itemExtra: Menu) {
        Intent(this, EditMenuActivity::class.java).run {
            putExtra(MENU_EXTRA,itemExtra)
            startActivity(this)
        }
    }

    fun Context.openCheckoutActivity(itemExtra: Menu) {
        Intent(this, CheckoutActivity::class.java).run {
            putExtra(MENU_EXTRA,itemExtra)
            startActivity(this)
        }
    }
}