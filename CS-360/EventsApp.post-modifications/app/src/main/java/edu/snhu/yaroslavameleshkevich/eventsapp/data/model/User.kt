package edu.snhu.yaroslavameleshkevich.eventsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User
/**
 * Default constructor.
 *
 * @param email    an email address
 * @param password a password
 */(
    /**
     * User's email address.
     */
    @field:PrimaryKey var email: String,
    /**
     * Hashed password.
     */
    var password: String
)
