package edu.snhu.yaroslavameleshkevich.grazioso.data.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Location class is a data model that represents a geographical location
 * using latitude and longitude coordinates.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Location {

    /**
     * Represents the latitude coordinate of a geographical location.
     */
    private Double latitude;

    /**
     * Represents the longitude coordinate of a geographical location.
     */
    private Double longitude;

}
