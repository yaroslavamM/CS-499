package edu.snhu.yaroslavameleshkevich.grazioso.data.entity;

import edu.snhu.yaroslavameleshkevich.grazioso.data.embeddable.Location;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Document(collection = "animals")
public class Animal {

    /**
     * Represents the unique identifier for an animal entity.
     * Used as the primary key in the "animals" MongoDB collection.
     */
    @Id
    private UUID id = UUID.randomUUID();

    /**
     * Represents the breed of an animal.
     */
    @Field("breed")
    private String breed;

    /**
     * Represents the date on which an animal was checked into the facility.
     */
    @Field("check_in_date")
    private LocalDate checkInDate;

    /**
     * Represents the date on which an animal was checked out of the facility.
     */
    @Field("check_out_date")
    private LocalDate checkOutDate;

    /**
     * Represents the color of an animal.
     */
    @Field("color")
    private String color;

    /**
     * Represents the date of birth of an animal.
     */
    @Field("date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Represents the geographical location of an animal.
     */
    @Field("location")
    private Location location;

    /**
     * Represents the name of an animal.
     */
    @Field("name")
    private String name;

    /**
     * Represents the outcome status of an animal in the system.
     */
    @Field("outcome")
    private AnimalOutcome outcome;

    /**
     * Provides additional context or details regarding the outcome of an animal.
     */
    @Field("outcome_description")
    private String outcomeDescription;

    /**
     * Represents the reproductive status of an animal.
     */
    @Field("sex")
    private AnimalSex sex;

    /**
     * Represents the type of the animal (e.g., CAT, DOG, OTHER).
     */
    @Field("type")
    @Indexed
    private AnimalType type;

}
