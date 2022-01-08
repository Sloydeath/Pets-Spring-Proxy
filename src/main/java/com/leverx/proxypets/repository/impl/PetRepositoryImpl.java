package com.leverx.proxypets.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.proxypets.dto.pet.PetDto;
import com.leverx.proxypets.dto.pet.ResponsePetDto;
import com.leverx.proxypets.dto.pet.SwappingPetsDto;
import com.leverx.proxypets.dto.pet.UpdatePetDto;
import com.leverx.proxypets.exception.custom.EntityNotFoundException;
import com.leverx.proxypets.mapper.PetMapper;
import com.leverx.proxypets.model.pet.Pet;
import com.leverx.proxypets.repository.PetRepository;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.leverx.proxypets.util.ExceptionMessageUtil.PET_ERROR_PATTERN;
import static com.leverx.proxypets.util.HttpJsonUtil.jsonWrapper;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PETS_ENDPOINT;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PETS_ID_ENDPOINT;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PETS_SWAPPING_ENDPOINT;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@Repository
@Slf4j
public class PetRepositoryImpl implements PetRepository {

    private final PetMapper petMapper;
    private final ObjectMapper objectMapper;
    private final ResponseHandler<String> responseHandler;
    private final HttpDestination httpDestination;
    private final HttpClient httpClient;

    @Autowired
    public PetRepositoryImpl(PetMapper petMapper, ObjectMapper objectMapper, ResponseHandler<String> responseHandler, HttpDestination httpDestination, HttpClient httpClient) {
        this.petMapper = petMapper;
        this.objectMapper = objectMapper;
        this.responseHandler = responseHandler;
        this.httpDestination = httpDestination;
        this.httpClient = httpClient;
    }

    @Override
    public Pet save(Pet pet) {

        try {
            String requestUrl = format(PETS_ENDPOINT, httpDestination.getUri());

            HttpPost httpPost = new HttpPost(requestUrl);

            PetDto petDto = petMapper.convertToPetDto(pet);
            String requestBodyJson = jsonWrapper(petDto);
            StringEntity requestEntity = new StringEntity(requestBodyJson, APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            String response = httpClient.execute(httpPost, responseHandler);

            return objectMapper.readValue(response, Pet.class);

        } catch (IOException exception) {
            log.error("Connection was aborted", exception);
            throw new EntityNotFoundException("Pet wasn't saved");
        }
    }

    @Override
    public List<Pet> findAll() {

        List<Pet> pets = new ArrayList<>();

        try {

            String requestUrl = format(PETS_ENDPOINT, httpDestination.getUri());
            String response = httpClient.execute(new HttpGet(requestUrl), responseHandler);
            pets = objectMapper.readValue(response, new TypeReference<List<Pet>>() {});

        } catch (IOException exception) {
            log.error("Connection was aborted", exception);
        }
        return pets;
    }

    @Override
    public Optional<Pet> findById(Long id) {

        Pet pet = null;

        try {

            String requestUrl = format(PETS_ID_ENDPOINT, httpDestination.getUri(), id);
            String response = httpClient.execute(new HttpGet(requestUrl), responseHandler);
            pet = objectMapper.readValue(response, Pet.class);

        } catch (IOException exception) {
            log.error("Connection was aborted", exception);
        }
        return Optional.ofNullable(pet);
    }

    @Override
    public Pet update(UpdatePetDto updatePetDto, Long id) {

        try {

            String requestUrl = format(PETS_ID_ENDPOINT, httpDestination.getUri(), id);

            HttpPut httpPut = new HttpPut(requestUrl);

            String requestBodyJson = jsonWrapper(updatePetDto);
            StringEntity requestEntity = new StringEntity(requestBodyJson, APPLICATION_JSON);
            httpPut.setEntity(requestEntity);

            String response = httpClient.execute(httpPut, responseHandler);
            ResponsePetDto petDto = objectMapper.readValue(response, ResponsePetDto.class);
            return petMapper.convertToEntity(petDto);

        } catch (IOException exception) {
            log.error("Connection was aborted", exception);
            throw new EntityNotFoundException(format(PET_ERROR_PATTERN, id));
        }
    }

    @Override
    public List<Pet> swap(SwappingPetsDto swappingPetsDto) {

        try {

            String requestUrl = format(PETS_SWAPPING_ENDPOINT, httpDestination.getUri());

            HttpPatch httpPatch = new HttpPatch(requestUrl);

            String requestBodyJson = jsonWrapper(swappingPetsDto);
            StringEntity requestEntity = new StringEntity(requestBodyJson, APPLICATION_JSON);
            httpPatch.setEntity(requestEntity);

            String response = httpClient.execute(httpPatch, responseHandler);
            List<ResponsePetDto> petsDto = objectMapper.readValue(response, new TypeReference<List<ResponsePetDto>>() {});

            return petsDto.stream()
                    .map(petMapper::convertToEntity)
                    .collect(toList());

        } catch (IOException exception) {
            log.error("Connection was aborted", exception);
            throw new IllegalArgumentException("An error has occurred in cloud repository");
        }
    }

    @Override
    public void deleteById(Long id) {

        try {

            String requestUrl = format(PETS_ID_ENDPOINT, httpDestination.getUri(), id);
            HttpDelete httpDelete = new HttpDelete(requestUrl);
            httpClient.execute(httpDelete, responseHandler);

        } catch (IOException exception) {
            log.error("An error has occurred in cloud repository: {}", exception.getMessage());
            throw new EntityNotFoundException(format(PET_ERROR_PATTERN, id));
        }
    }

}
