package com.leverx.proxypets.service.impl;

import com.leverx.proxypets.dto.person.PersonDto;
import com.leverx.proxypets.exception.custom.PersonNotFoundException;
import com.leverx.proxypets.model.Person;
import com.leverx.proxypets.repository.PersonRepository;
import com.leverx.proxypets.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.leverx.proxypets.util.ExceptionMessageUtil.PERSON_ERROR_PATTERN;
import static java.lang.String.format;

@Service
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person create(Person person) {

        personRepository.save(person);
        return person;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Person getById(Long id) {

        return personRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.info(format(PERSON_ERROR_PATTERN, id));
                    return new PersonNotFoundException(format(PERSON_ERROR_PATTERN, id));
                });
    }

    @Override
    public Person updateName(PersonDto personDto, Long id) {
        return personRepository.update(personDto, id);
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

}
