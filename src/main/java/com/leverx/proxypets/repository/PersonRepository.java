package com.leverx.proxypets.repository;

import com.leverx.proxypets.dto.person.PersonDto;
import com.leverx.proxypets.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person save(Person person);
    List<Person> findAll();
    Optional<Person> findById(Long id);
    Person update(PersonDto personDto, Long id);
    Void deleteById(Long id);

}
