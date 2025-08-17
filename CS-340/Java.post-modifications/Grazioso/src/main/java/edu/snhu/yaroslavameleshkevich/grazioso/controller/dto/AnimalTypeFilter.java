package edu.snhu.yaroslavameleshkevich.grazioso.controller.dto;

import edu.snhu.yaroslavameleshkevich.grazioso.data.entity.AnimalSex;
import lombok.Getter;

import java.time.Period;
import java.util.List;

@Getter
public enum AnimalTypeFilter {

    DISASTER_RESCUE(
            Period.ofWeeks(300), Period.ofWeeks(20),
            AnimalSex.INTACT_MALE,
            "Doberman Pinscher", "German",
            "Shepherd", "Golden Retriever",
            "Bloodhound", "Rottweiler"
    ),

    MOUNTAIN_RESCUE(
            Period.ofWeeks(156), Period.ofWeeks(26),
            AnimalSex.INTACT_MALE,
            "German Shepherd", "Alaskan",
            "Malamute", "Old English",
            "Sheepdog", "Siberian Husky",
            "Rottweiler"
    ),

    WATER_RESCUE(
            Period.ofWeeks(156), Period.ofWeeks(26),
            AnimalSex.INTACT_FEMALE,
            "Labrador Retriever Mix",
            "Chesapeake Bay Retriever",
            "Newfoundland"
    );

    /**
     * Training Age From.
     */
    private final Period ageFrom;

    /**
     * Training Age To.
     */
    private final Period ageTo;

    /**
     * Preferred Sex.
     */
    private final AnimalSex sex;

    /**
     * Preferred Breeds.
     */
    private final List<String> breeds;

    AnimalTypeFilter(final Period ageFrom, final Period ageTo, final AnimalSex sex, final String... breeds) {
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.sex = sex;
        this.breeds = List.of(breeds);
    }

}
