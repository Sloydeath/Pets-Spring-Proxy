package com.leverx.proxypets.repository;

import com.leverx.proxypets.dto.pet.SwappingPetsDto;
import com.leverx.proxypets.dto.pet.UpdatePetDto;
import com.leverx.proxypets.model.pet.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    Pet save(Pet pet);
    List<Pet> findAll();
    Optional<Pet> findById(Long id);
    Pet update(UpdatePetDto petDto, Long id);
    List<Pet> swap(SwappingPetsDto swappingPetsDto);
    void deleteById(Long id);

}
