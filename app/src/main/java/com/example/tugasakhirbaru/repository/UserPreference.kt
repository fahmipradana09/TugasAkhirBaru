package com.example.tugasakhirbaru.repository

import android.content.Context
import com.example.tugasakhirbaru.model.Cart
import com.example.tugasakhirbaru.model.ComponentChecklist
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserPreference private constructor(context: Context) {
    private val sharedPref = context.getSharedPreferences(DatabasePath.LOCALPREF, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveCart(cart: Cart) {
        val json = gson.toJson(cart)
        val editor = sharedPref.edit()
        editor.putString("cart", json)
        editor.apply()
    }

    fun getCart(): Cart {
        val json = sharedPref.getString("cart", "")
        return gson.fromJson(json, Cart::class.java)
    }

    fun saveDetailIngredient(list: ArrayList<ComponentChecklist>) {
        val json = gson.toJson(list)
        val editor = sharedPref.edit()
        editor.putString("detailIngredient", json)
        editor.apply()
    }

    fun getDetailIngredient(): ArrayList<ComponentChecklist> {
        val json = sharedPref.getString("detailIngredient", "")
        val type = object : TypeToken<ArrayList<ComponentChecklist>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveUser(user: Users) {
        val json = gson.toJson(user)
        val editor = sharedPref.edit()
        editor.putString("user", json)
        editor.apply()
    }

    fun getUser(): Users {
        val json = sharedPref.getString("user", "")
        return gson.fromJson(json, Users::class.java)
    }

    companion object {
        fun getInstance(context: Context): UserPreference {
            return UserPreference(context)
        }
    }
}
