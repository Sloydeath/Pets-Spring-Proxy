package com.leverx.proxypets.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leverx.proxypets.dto.pet.SwappingPetsDto;
import com.leverx.proxypets.dto.pet.UpdatePetDto;
import com.leverx.proxypets.model.pet.Pet;

import java.util.List;

public interface PetService {

    Pet create(Pet pet) throws JsonProcessingException;
    List<Pet> getAll();
    Pet getById(Long id);
    void deleteById(Long id);
    Pet updateName(UpdatePetDto petDto, Long id);
    List<Pet> swappingPets(SwappingPetsDto swappingPetsDto);
}
