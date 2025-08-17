package edu.snhu.yaroslavameleshkevich.grazioso.controller;

import edu.snhu.yaroslavameleshkevich.grazioso.controller.dto.AnimalTypeFilter;
import edu.snhu.yaroslavameleshkevich.grazioso.data.entity.Animal;
import edu.snhu.yaroslavameleshkevich.grazioso.data.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
@Slf4j
public class AnimalsController {

    /**
     * AnimalRepository.
     */
    private final AnimalRepository animalRepository;

    /**
     * MongoTemplate.
     */
    private final MongoTemplate mongoTemplate;

    @GetMapping
    public Page<Animal> getAnimals(final Optional<AnimalTypeFilter> typeFilter, final int page, final int size) {
        final PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        if (typeFilter.isPresent()) {
            final AnimalTypeFilter type = typeFilter.get();
            final LocalDate from = LocalDate.now().minus(type.getAgeFrom());
            final LocalDate to = LocalDate.now().minus(type.getAgeTo());
            log.info("Filtering animals by type: {}, from={}, to={}", type, from, to);

            final Query query = new Query();
            query.addCriteria(Criteria.where("dateOfBirth").gte(from).lt(to));
            query.addCriteria(Criteria.where("sex").is(type.getSex()));
            query.addCriteria(Criteria.where("breed").in(type.getBreeds()));

            var animals = mongoTemplate.find(query, Animal.class, "animals");

            return PageableExecutionUtils.getPage(
                    animals,
                    pageRequest,
                    () -> mongoTemplate.count(query, Animal.class));

        }
        return animalRepository.findAll(pageRequest);
    }

}
