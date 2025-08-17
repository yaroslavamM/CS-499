package edu.snhu.yaroslavameleshkevich.grazioso.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "users")
public class User {

    /**
     * Represents the unique identifier for a user entity.
     * Used as the primary key in the "users" MongoDB collection.
     */
    @Id
    private UUID id = UUID.randomUUID();

}
