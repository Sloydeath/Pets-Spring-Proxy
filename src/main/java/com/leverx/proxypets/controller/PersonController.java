package com.leverx.proxypets.controller;

import com.leverx.proxypets.dto.person.PersonDto;
import com.leverx.proxypets.mapper.PersonMapper;
import com.leverx.proxypets.model.Person;
import com.leverx.proxypets.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.leverx.proxypets.util.ControllerConstantUtil.PERSON_CONTROLLER_ENDPOINT;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(PERSON_CONTROLLER_ENDPOINT)
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @Autowired
    public PersonController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDto>> getAllPeople() {

        List<PersonDto> responsePeopleDto = personService.getAll()
                .stream()
                .map(personMapper::convertToPersonDto)
                .collect(toList());

        return new ResponseEntity<>(responsePeopleDto, OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {

        Person person = personService.getById(id);
        PersonDto responsePersonDto = personMapper.convertToPersonDto(person);

        return new ResponseEntity<>(responsePersonDto, OK);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> savePerson(@Valid @RequestBody PersonDto receivedPersonDto) {

        Person person = personService.create(personMapper.convertToEntity(receivedPersonDto));
        PersonDto responsePersonDto = personMapper.convertToPersonDto(person);

        return new ResponseEntity<>(responsePersonDto, OK);
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> updatePerson(@Valid @RequestBody PersonDto receivedPersonDto,
                                               @PathVariable Long id) {

        Person person = personService.updateName(receivedPersonDto, id);
        PersonDto responsePersonDto = personMapper.convertToPersonDto(person);

        return new ResponseEntity<>(responsePersonDto, OK);
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {

        personService.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}
