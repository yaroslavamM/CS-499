package edu.snhu.yaroslavameleshkevich.eventsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import edu.snhu.yaroslavameleshkevich.eventsapp.data.converter.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "events")
class Event {

    /**
     * The ID of the event.
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    /**
     * The name of the event.
     */
    var name: String? = null

    /**
     * The date of the event.
     */
    @TypeConverters(LocalDateConverter::class)
    var date: LocalDate? = null

    /**
     * The location of the event.
     */
    var location: String? = null

    /**
     * The description of the event.
     */
    var description: String? = null

    constructor()

    constructor(name: String?, date: LocalDate?, location: String?, description: String?) {
        this.name = name
        this.date = date
        this.location = location
        this.description = description
    }
}
