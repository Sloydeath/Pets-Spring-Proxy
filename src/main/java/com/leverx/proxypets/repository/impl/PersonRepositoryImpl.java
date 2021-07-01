package com.leverx.proxypets.repository.impl;

import com.leverx.proxypets.dto.person.PersonDto;
import com.leverx.proxypets.exception.custom.EntityNotFoundException;
import com.leverx.proxypets.mapper.PersonMapper;
import com.leverx.proxypets.model.Person;
import com.leverx.proxypets.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static com.leverx.proxypets.util.ExceptionMessageUtil.PERSON_ERROR_PATTERN;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PEOPLE_ENDPOINT;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PEOPLE_ID_ENDPOINT;
import static java.lang.String.format;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Repository
@Slf4j
public class PersonRepositoryImpl implements PersonRepository {

    private final WebClient webClient;
    private final PersonMapper personMapper;

    @Autowired
    public PersonRepositoryImpl(WebClient webClient, PersonMapper personMapper) {
        this.webClient = webClient;
        this.personMapper = personMapper;
    }

    @Override
    public Person save(Person person) {
        PersonDto personDto = personMapper.convertToPersonDto(person);

        return webClient.post()
                .uri(PEOPLE_ENDPOINT)
                .body(just(personDto), PersonDto.class)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    @Override
    public List<Person> findAll() {
        return webClient.get()
                .uri(PEOPLE_ENDPOINT)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Person>>() {})
                .block();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return webClient.get()
                .uri(PEOPLE_ID_ENDPOINT, id)
                .retrieve()
                .bodyToMono(Person.class)
                .doOnError(error -> {
                    log.error("An error has occurred in cloud repository: {}", error.getMessage());
                    throw new EntityNotFoundException(format(PERSON_ERROR_PATTERN, id));
                })
                .blockOptional();
    }

    @Override
    public Person update(PersonDto personDto, Long id) throws EntityNotFoundException{
        return webClient.put()
                .uri(PEOPLE_ID_ENDPOINT, id)
                .body(just(personDto), PersonDto.class)
                .retrieve()
                .bodyToMono(Person.class)
                .doOnError(error -> {
                    log.error("An error has occurred in cloud repository: {}", error.getMessage());
                    throw new EntityNotFoundException(format(PERSON_ERROR_PATTERN, id));
                })
                .block();
    }

    @Override
    public Void deleteById(Long id) {
        return webClient.delete()
                .uri(PEOPLE_ID_ENDPOINT, id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(error -> {
                    log.error("An error has occurred in cloud repository: {}", error.getMessage());
                    error(new EntityNotFoundException(format(PERSON_ERROR_PATTERN, id)));
                })
                .block();
    }

}
