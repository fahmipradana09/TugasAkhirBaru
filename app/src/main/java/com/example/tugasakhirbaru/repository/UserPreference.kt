package com.example.tugasakhirbaru.repository

import android.content.ClipData.Item
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//class UserPreference private constructor(private val dataStore :DataStore<Preferences>) {
//    private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
//    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
//    private val USER_NAME_KEY = stringPreferencesKey("user_name")
//    private val MENU_NAME_KEY = stringPreferencesKey("menu_name")
//    private val MENU_PRICE_KEY = stringPreferencesKey("menu_price")
//    private val MENU_DESC_KEY = stringPreferencesKey("menu_desc")
//    private val MENU_INGREDIENT_KEY = stringPreferencesKey("menu_ingredient")
//    private val ITEM_KEY = stringPreferencesKey("item_key")
//
//    fun getItemUser(): Flow<ItemMenu> = dataStore.data.map {
//       it[MENU_NAME_KEY] ?: DEFAULT_VALUE
//    }
//
//    suspend fun saveItemUser(itemUser : ItemUser)
//
//    companion object {
//        @Volatile
//        private var INSTANCE: UserPreference? = null
//        const val DEFAULT_VALUE = "not_set_yet"
//
//        fun getInstance(dataStore: DataStore<Preferences>) : UserPreference {
//            return INSTANCE ?: synchronized(this) {
//                val instance = UserPreference(dataStore)
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//
//}