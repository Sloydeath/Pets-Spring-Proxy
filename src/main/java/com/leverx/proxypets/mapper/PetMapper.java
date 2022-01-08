package com.leverx.proxypets.mapper;

import com.leverx.proxypets.dto.pet.PetDto;
import com.leverx.proxypets.dto.pet.ResponsePetDto;
import com.leverx.proxypets.model.Person;
import com.leverx.proxypets.model.pet.Pet;
import com.leverx.proxypets.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static com.leverx.proxypets.factory.PetFactory.getPet;

@Component
public class PetMapper {

    private final ModelMapper modelMapper;
    private final PersonService personService;

    @Autowired
    public PetMapper(ModelMapper modelMapper, PersonService personService) {
        this.modelMapper = modelMapper;
        this.personService = personService;
    }

    public ResponsePetDto convertToResponsePetDto(Pet pet) {

        ResponsePetDto responsePetDto = modelMapper.map(pet, ResponsePetDto.class);
        responsePetDto.setPetType(pet.getPetType());

        return responsePetDto;
    }

    public PetDto convertToPetDto(Pet pet) {

        PetDto petDto = modelMapper.map(pet, PetDto.class);
        petDto.setPetType(pet.getPetType());

        return petDto;
    }

    public Pet convertToEntity(PetDto petDto) {

        Pet pet = getPet(petDto.getPetType());

        Person person = personService.getById(petDto.getPersonId());

        pet.setName(petDto.getName());
        pet.setPetType(petDto.getPetType());
        pet.setPerson(person);

        return pet;
    }

    public Pet convertToEntity(ResponsePetDto petDto) {

        Pet pet = getPet(petDto.getPetType());

        pet.setName(petDto.getName());
        pet.setPetType(petDto.getPetType());
        pet.setPerson(petDto.getPerson());

        return pet;
    }

}
