package edu.snhu.yaroslavameleshkevich.grazioso.listener;

import edu.snhu.yaroslavameleshkevich.grazioso.data.repository.AnimalRepository;
import edu.snhu.yaroslavameleshkevich.grazioso.data.entity.Animal;
import edu.snhu.yaroslavameleshkevich.grazioso.data.entity.AnimalType;
import edu.snhu.yaroslavameleshkevich.grazioso.data.entity.AnimalSex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import edu.snhu.yaroslavameleshkevich.grazioso.data.embeddable.Location;

@Component
@RequiredArgsConstructor
@Slf4j
public class OnStartup implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * AnimalRepository.
     */
    private final AnimalRepository animalRepository;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (animalRepository.count() == 0) {
            log.info("Loading animal data...");
            List<Animal> animals = createSampleAnimals();
            animalRepository.saveAll(animals);
            log.info("Finished loading {} animals", animals.size());
        }
    }

    private List<Animal> createSampleAnimals() {
        List<Animal> animals = new ArrayList<>();
        Location minneapolisLocation = new Location(44.9778, -93.2650);

        Animal dog = new Animal();
        dog.setName("Max");
        dog.setType(AnimalType.DOG);
        dog.setBreed("German Shepherd");
        dog.setColor("Black and Tan");
        dog.setSex(AnimalSex.INTACT_MALE);
        dog.setDateOfBirth(LocalDate.of(2023, 1, 1));
        dog.setCheckInDate(LocalDate.now());
        dog.setLocation(minneapolisLocation);

        Animal cat = new Animal();
        cat.setName("Luna");
        cat.setType(AnimalType.CAT);
        cat.setBreed("Siamese");
        cat.setColor("Cream Point");
        cat.setSex(AnimalSex.INTACT_FEMALE);
        cat.setDateOfBirth(LocalDate.of(2023, 3, 15));
        cat.setCheckInDate(LocalDate.now());
        cat.setLocation(minneapolisLocation);

        Animal doberman = new Animal();
        doberman.setName("Duke");
        doberman.setType(AnimalType.DOG);
        doberman.setBreed("Doberman Pinscher");
        doberman.setColor("Black and Rust");
        doberman.setSex(AnimalSex.INTACT_MALE);
        doberman.setDateOfBirth(LocalDate.of(2023, 2, 15));
        doberman.setCheckInDate(LocalDate.now());
        doberman.setLocation(minneapolisLocation);

        Animal shepherd = new Animal();
        shepherd.setName("Bear");
        shepherd.setType(AnimalType.DOG);
        shepherd.setBreed("German Shepherd");
        shepherd.setColor("Sable");
        shepherd.setSex(AnimalSex.INTACT_MALE);
        shepherd.setDateOfBirth(LocalDate.of(2023, 4, 10));
        shepherd.setCheckInDate(LocalDate.now());
        shepherd.setLocation(minneapolisLocation);

        Animal retriever = new Animal();
        retriever.setName("Bella");
        retriever.setType(AnimalType.DOG);
        retriever.setBreed("Golden Retriever");
        retriever.setColor("Golden");
        retriever.setSex(AnimalSex.INTACT_FEMALE);
        retriever.setDateOfBirth(LocalDate.of(2023, 5, 20));
        retriever.setCheckInDate(LocalDate.now());
        retriever.setLocation(minneapolisLocation);

        Animal bloodhound = new Animal();
        bloodhound.setName("Cooper");
        bloodhound.setType(AnimalType.DOG);
        bloodhound.setBreed("Bloodhound");
        bloodhound.setColor("Red");
        bloodhound.setSex(AnimalSex.INTACT_MALE);
        bloodhound.setDateOfBirth(LocalDate.of(2023, 6, 1));
        bloodhound.setCheckInDate(LocalDate.now());
        bloodhound.setLocation(minneapolisLocation);

        Animal rottweiler = new Animal();
        rottweiler.setName("Zeus");
        rottweiler.setType(AnimalType.DOG);
        rottweiler.setBreed("Rottweiler");
        rottweiler.setColor("Black and Mahogany");
        rottweiler.setSex(AnimalSex.INTACT_MALE);
        rottweiler.setDateOfBirth(LocalDate.of(2023, 7, 5));
        rottweiler.setCheckInDate(LocalDate.now());
        rottweiler.setLocation(minneapolisLocation);

        Animal malamute = new Animal();
        malamute.setName("Storm");
        malamute.setType(AnimalType.DOG);
        malamute.setBreed("Alaskan Malamute");
        malamute.setColor("Gray and White");
        malamute.setSex(AnimalSex.INTACT_MALE);
        malamute.setDateOfBirth(LocalDate.of(2023, 8, 15));
        malamute.setCheckInDate(LocalDate.now());
        malamute.setLocation(minneapolisLocation);

        Animal sheepdog = new Animal();
        sheepdog.setName("Shaggy");
        sheepdog.setType(AnimalType.DOG);
        sheepdog.setBreed("Old English Sheepdog");
        sheepdog.setColor("Gray and White");
        sheepdog.setSex(AnimalSex.INTACT_FEMALE);
        sheepdog.setDateOfBirth(LocalDate.of(2023, 9, 1));
        sheepdog.setCheckInDate(LocalDate.now());
        sheepdog.setLocation(minneapolisLocation);

        Animal husky = new Animal();
        husky.setName("Nova");
        husky.setType(AnimalType.DOG);
        husky.setBreed("Siberian Husky");
        husky.setColor("Black and White");
        husky.setSex(AnimalSex.INTACT_FEMALE);
        husky.setDateOfBirth(LocalDate.of(2023, 10, 5));
        husky.setCheckInDate(LocalDate.now());
        husky.setLocation(minneapolisLocation);

        Animal labMix = new Animal();
        labMix.setName("Charlie");
        labMix.setType(AnimalType.DOG);
        labMix.setBreed("Labrador Retriever Mix");
        labMix.setColor("Chocolate");
        labMix.setSex(AnimalSex.INTACT_MALE);
        labMix.setDateOfBirth(LocalDate.of(2023, 11, 15));
        labMix.setCheckInDate(LocalDate.now());
        labMix.setLocation(minneapolisLocation);

        Animal chesapeake = new Animal();
        chesapeake.setName("River");
        chesapeake.setType(AnimalType.DOG);
        chesapeake.setBreed("Chesapeake Bay Retriever");
        chesapeake.setColor("Brown");
        chesapeake.setSex(AnimalSex.INTACT_FEMALE);
        chesapeake.setDateOfBirth(LocalDate.of(2023, 12, 1));
        chesapeake.setCheckInDate(LocalDate.now());
        chesapeake.setLocation(minneapolisLocation);

        Animal newfoundland = new Animal();
        newfoundland.setName("Atlas");
        newfoundland.setType(AnimalType.DOG);
        newfoundland.setBreed("Newfoundland");
        newfoundland.setColor("Black");
        newfoundland.setSex(AnimalSex.INTACT_MALE);
        newfoundland.setDateOfBirth(LocalDate.of(2024, 1, 5));
        newfoundland.setCheckInDate(LocalDate.now());
        newfoundland.setLocation(minneapolisLocation);

        animals.add(dog);
        animals.add(cat);
        animals.add(doberman);
        animals.add(shepherd);
        animals.add(retriever);
        animals.add(bloodhound);
        animals.add(rottweiler);
        animals.add(malamute);
        animals.add(sheepdog);
        animals.add(husky);
        animals.add(labMix);
        animals.add(chesapeake);
        animals.add(newfoundland);

        return animals;
    }

}
