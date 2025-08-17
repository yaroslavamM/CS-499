package edu.snhu.yaroslavameleshkevich.grazioso.data.repository;

import edu.snhu.yaroslavameleshkevich.grazioso.data.entity.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimalRepository extends MongoRepository<Animal, String> {

}
