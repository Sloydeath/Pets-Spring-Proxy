package com.leverx.proxypets.mapper;

import com.leverx.proxypets.dto.person.PersonDto;
import com.leverx.proxypets.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public PersonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PersonDto convertToPersonDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    public Person convertToEntity(PersonDto personDto) {

        Person person = new Person();
        person.setName(personDto.getName());

        return person;
    }
}
