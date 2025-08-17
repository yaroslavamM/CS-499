package edu.snhu.yaroslavameleshkevich.eventsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.snhu.yaroslavameleshkevich.eventsapp.data.model.Event

@Dao
interface EventDao {
    @get:Query("SELECT * FROM events order by date desc")
    val allEvents: List<Event?>?

    /**
     * Deletes an event from the database by its ID.
     * @param id the ID of the event to delete
     */
    @Query("DELETE FROM events WHERE id = :id")
    fun deleteById(id: Long)

    /**
     * Inserts an event into the database.
     * @param event the event to insert
     */
    @Insert
    fun insert(event: Event?)
}
