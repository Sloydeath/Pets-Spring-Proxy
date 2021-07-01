package com.leverx.proxypets.repository.impl;

import com.leverx.proxypets.dto.pet.PetDto;
import com.leverx.proxypets.dto.pet.ResponsePetDto;
import com.leverx.proxypets.dto.pet.SwappingPetsDto;
import com.leverx.proxypets.dto.pet.UpdatePetDto;
import com.leverx.proxypets.exception.custom.EntityNotFoundException;
import com.leverx.proxypets.exception.custom.SimilarPersonException;
import com.leverx.proxypets.mapper.PetMapper;
import com.leverx.proxypets.model.pet.Pet;
import com.leverx.proxypets.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static com.leverx.proxypets.util.ExceptionMessageUtil.PET_ERROR_PATTERN;
import static com.leverx.proxypets.util.ExceptionMessageUtil.SIMILAR_PEOPLE_MESSAGE;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PETS_ENDPOINT;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PETS_ID_ENDPOINT;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PETS_SWAPPING_ENDPOINT;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static reactor.core.publisher.Mono.just;

@Repository
@Slf4j
public class PetRepositoryImpl implements PetRepository {

    private final WebClient webClient;
    private final PetMapper petMapper;

    @Autowired
    public PetRepositoryImpl(WebClient webClient, PetMapper petMapper) {
        this.webClient = webClient;
        this.petMapper = petMapper;
    }

    @Override
    public Pet save(Pet pet) {
        PetDto petDto = petMapper.convertToPetDto(pet);

        return webClient.post()
                .uri(PETS_ENDPOINT)
                .body(just(petDto), PetDto.class)
                .retrieve()
                .bodyToMono(Pet.class)
                .block();
    }

    @Override
    public List<Pet> findAll() {
        return webClient.get()
                .uri(PETS_ENDPOINT)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Pet>>() {})
                .block();
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return webClient.get()
                .uri(PETS_ID_ENDPOINT, id)
                .retrieve()
                .bodyToMono(Pet.class)
                .doOnError(error -> {
                    log.error("An error has occurred in cloud repository: {}", error.getMessage());
                    throw new EntityNotFoundException(format(PET_ERROR_PATTERN, id));
                })
                .blockOptional();
    }

    @Override
    public Pet update(UpdatePetDto updatePetDto, Long id) throws EntityNotFoundException{
        ResponsePetDto petDto = webClient.put()
                .uri(PETS_ID_ENDPOINT, id)
                .body(just(updatePetDto), UpdatePetDto.class)
                .retrieve()
                .bodyToMono(ResponsePetDto.class)
                .doOnError(error -> log.error("An error has occurred in cloud repository: {}", error.getMessage()))
                .block();

        if (nonNull(petDto)) {
            return petMapper.convertToEntity(petDto);
        }
        else {
            throw new EntityNotFoundException(format(PET_ERROR_PATTERN, id));
        }
    }

    @Override
    public List<Pet> swap(SwappingPetsDto swappingPetsDto) {
        List<ResponsePetDto> petsDto = webClient.patch()
                .uri(PETS_SWAPPING_ENDPOINT)
                .body(just(swappingPetsDto), SwappingPetsDto.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ResponsePetDto>>() {})
                .doOnError(error -> {
                    log.error("An error has occurred in cloud repository: {}", error.getMessage());
                    throw new SimilarPersonException(SIMILAR_PEOPLE_MESSAGE);
                })
                .block();

        if (nonNull(petsDto) ) {
            return petsDto.stream()
                    .map(petMapper::convertToEntity)
                    .collect(toList());
        }
        else {
            throw new IllegalArgumentException("An error has occurred in cloud repository");
        }
    }

    @Override
    public Void deleteById(Long id) throws EntityNotFoundException{
        return webClient.delete()
                .uri(PETS_ID_ENDPOINT, id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(error -> {
                    log.error("An error has occurred in cloud repository: {}", error.getMessage());
                    throw new EntityNotFoundException(format(PET_ERROR_PATTERN, id));
                })
                .block();
    }

}
