package com.example.tugasakhirbaru.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.MENU_EXTRA
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.TRANSACTION_EXTRA
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.UPLOAD_EXTRA
import com.example.tugasakhirbaru.view.*
import java.text.SimpleDateFormat
import java.util.*

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

    fun Context.openEditProfileActivity() {
        Intent(this, EditProfileActivity::class.java).run {
            startActivity(this)
        }
    }

    fun Context.openEditIngredient(itemExtra: Menu) {
        Intent(this, EditMenuActivity::class.java).run {
            putExtra(MENU_EXTRA, itemExtra)
            startActivity(this)
        }
    }

    fun Context.openCheckoutActivity() {
        Intent(this, CheckoutActivity::class.java).run {
            startActivity(this)
        }
    }


    fun Context.openHistory() {
        Intent(this, PurchaseHistoryActivity::class.java).run {
            startActivity(this)
        }

    }

    fun Activity.openAdminActivity(){
        Intent(this, AdminActivity::class.java).run {
            startActivity(this)
        }
        finish()
    }

    fun Context.openAdminDetailActivity(itemExtra:TransactionMenu){
        Intent(this,AdminDetailMenuActivity::class.java).run {
            putExtra(TRANSACTION_EXTRA, itemExtra)
            startActivity(this)
        }
    }

    fun Context.openAdminListTransactionActivity(){
        Intent(this,AdminHistoryTransactionActivity::class.java).run {
            startActivity(this)
        }
    }

    fun Context.openConfirmPaymentActivity(itemExtra:TransactionMenu){
        Intent(this,ConfirmPaymentActivity::class.java).run{
            putExtra(UPLOAD_EXTRA, itemExtra)
            startActivity(this)
        }
    }

    fun Context.openAdminConfirmPaymentActivity(itemExtra: TransactionMenu){
        Intent(this, AdminConfirmActivity::class.java).run {
            putExtra(UPLOAD_EXTRA, itemExtra)
            startActivity(this)
        }
    }

    fun Array<String>.toHashMap(): HashMap<Int, String> {
        val hashMap = hashMapOf<Int, String>()
        for ((index, string) in this.withIndex()) {
            hashMap[index] = string
        }
        return hashMap
    }

    fun <T, E> Map<T,E>.getByValue(find: E): T? {
        for ((key, value) in this) {
            if (find == value) {
                return key
            }
        }
        return null
    }

    fun Calendar.convertToFormat(newFormat: String): String {
        return try {
            if (newFormat.isBlank()) {
                ""
            } else SimpleDateFormat(newFormat, getLocale()).format(time)
        } catch (e: NullPointerException) {
            Log.e("Utils", "Error convert calendar: " + e.localizedMessage)
            ""
        }
    }

    fun getLocale(): Locale {
        return Locale("id", "ID")
    }

}