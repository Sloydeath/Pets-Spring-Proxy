package com.leverx.proxypets.service;

import com.leverx.proxypets.dto.person.PersonDto;
import com.leverx.proxypets.model.Person;

import java.util.List;

public interface PersonService {

    Person create(Person person);
    List<Person> getAll();
    Person getById(Long id);
    Person updateName(PersonDto personDto, Long id);
    void deleteById(Long id);

}
