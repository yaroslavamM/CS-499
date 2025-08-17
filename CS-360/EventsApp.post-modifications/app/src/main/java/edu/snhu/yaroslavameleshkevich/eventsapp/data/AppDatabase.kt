package edu.snhu.yaroslavameleshkevich.eventsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import edu.snhu.yaroslavameleshkevich.eventsapp.data.dao.EventDao
import edu.snhu.yaroslavameleshkevich.eventsapp.data.dao.UserDao
import edu.snhu.yaroslavameleshkevich.eventsapp.data.model.Event
import edu.snhu.yaroslavameleshkevich.eventsapp.data.model.User
import kotlin.concurrent.Volatile

@Database(entities = [Event::class, User::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Returns the DAO for the Event entity.
     *
     * @return the DAO for the Event entity
     */
    abstract fun eventDao(): EventDao

    /**
     * Returns the DAO for the User entity.
     *
     * @return the DAO for the User entity
     */
    abstract fun userDao(): UserDao

    companion object {
        /**
         * Returns the singleton instance of the database.
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Returns the singleton instance of the database.
         *
         * @param context the context
         * @return the singleton instance of the database
         */
        @JvmStatic
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "events_app_database"
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
