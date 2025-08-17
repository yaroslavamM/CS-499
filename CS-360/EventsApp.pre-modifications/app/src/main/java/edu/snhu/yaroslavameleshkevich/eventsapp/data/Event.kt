package edu.snhu.yaroslavameleshkevich.eventsapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.time.LocalDate

data class Event(val id: Int, val name: String, val date: LocalDate, val location: String, val description: String) {

    companion object {
        val gson = Gson()

        @JvmStatic
        fun loadEvents(context: Context): List<Event> {
            val file = File(context.filesDir, "events.json")
            val loadedJsonString = file.readText()
            val eventListType = object : TypeToken<List<Event>>() {}.type
            return gson.fromJson(loadedJsonString, eventListType)
        }

        @JvmStatic
        fun saveEvents(context: Context, events: List<Event>) {
            val file = File(context.filesDir, "events.json")
            val jsonString = gson.toJson(events)
            file.writeText(jsonString)
        }

    }

}