package edu.snhu.yaroslavameleshkevich.eventsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.snhu.yaroslavameleshkevich.eventsapp.data.model.User

@Dao
interface UserDao {
    /**
     * Authenticates a user by finding a user with the given email and password.
     *
     * @param email    an email address
     * @param password a password
     * @return a user with the given email and password or null if not found
     */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun authenticate(email: String?, password: String?): User?

    @get:Query("SELECT * FROM users")
    val allUsers: List<User?>?

    /**
     * Inserts a user into the database.
     *
     * @param user a user to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User?)
}
