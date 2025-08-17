package edu.snhu.yaroslavameleshkevich.eventsapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

data class User(val id: Int, val email: String, val password: String) {

    companion object {
        val gson = Gson()

        private fun loadUsers(context: Context): List<User> {
            val file = File(context.filesDir, "users.json")
            val loadedJsonString = file.readText()
            val userListType = object : TypeToken<List<User>>() {}.type
            return gson.fromJson(loadedJsonString, userListType)
        }

        private fun saveUsers(context: Context, users: List<User>) {
            val file = File(context.filesDir, "users.json")
            val jsonString = gson.toJson(users)
            file.writeText(jsonString)
        }

        @JvmStatic
        fun authenticate(context: Context, email: String, password: String): User? {
            val users = loadUsers(context)
            for (user in users) {
                if (user.email == email && user.password == password) {
                    return user
                }
            }
            return null
        }

        @JvmStatic
        fun register(context: Context, email: String, password: String) {
            val users = mutableListOf<User>()
            users.addAll(loadUsers(context))
            val newUser = User(users.size + 1, email, password)
            users.add(newUser)
            saveUsers(context, users)
        }

    }

}