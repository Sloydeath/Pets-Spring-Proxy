package com.leverx.proxypets.service.impl;

import com.leverx.proxypets.dto.pet.SwappingPetsDto;
import com.leverx.proxypets.dto.pet.UpdatePetDto;
import com.leverx.proxypets.exception.custom.PetNotFoundException;
import com.leverx.proxypets.exception.custom.SimilarPersonException;
import com.leverx.proxypets.model.pet.Pet;
import com.leverx.proxypets.repository.PetRepository;
import com.leverx.proxypets.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.leverx.proxypets.util.ExceptionMessageUtil.PET_ERROR_PATTERN;
import static com.leverx.proxypets.util.ExceptionMessageUtil.SIMILAR_PEOPLE_MESSAGE;
import static java.lang.String.format;

@Service
@Transactional
@Slf4j
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet create(Pet pet) {
        petRepository.save(pet);
        return pet;
    }

    @Override
    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    @Override
    public Pet getById(Long id) {
        return petRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.info(format(PET_ERROR_PATTERN, id));
                    return new PetNotFoundException(format(PET_ERROR_PATTERN, id));
                });
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public Pet updateName(UpdatePetDto petDto, Long id) {
        return petRepository.update(petDto, id);
    }

    @Override
    public List<Pet> swappingPets(SwappingPetsDto swappingPetsDto) {

        Pet firstPet = getById(swappingPetsDto.getFirstPetId());
        Pet secondPet = getById(swappingPetsDto.getSecondPetId());

        if (!Objects.equals(firstPet.getPerson(), secondPet.getPerson())) {
            return petRepository.swap(swappingPetsDto);
        }
        else {
            log.info("Exception while executing method swappingPets: {}", SIMILAR_PEOPLE_MESSAGE);
            throw new SimilarPersonException(SIMILAR_PEOPLE_MESSAGE);
        }
    }

}