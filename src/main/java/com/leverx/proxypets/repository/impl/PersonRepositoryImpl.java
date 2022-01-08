package com.leverx.proxypets.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.proxypets.dto.person.PersonDto;
import com.leverx.proxypets.exception.custom.EntityNotFoundException;
import com.leverx.proxypets.mapper.PersonMapper;
import com.leverx.proxypets.model.Person;
import com.leverx.proxypets.repository.PersonRepository;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.leverx.proxypets.util.ExceptionMessageUtil.PERSON_ERROR_PATTERN;
import static com.leverx.proxypets.util.HttpJsonUtil.jsonWrapper;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PEOPLE_ENDPOINT;
import static com.leverx.proxypets.util.PetsApiConstraintUtil.PEOPLE_ID_ENDPOINT;
import static java.lang.String.format;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@Repository
@Slf4j
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonMapper personMapper;
    private final ObjectMapper objectMapper;
    private final ResponseHandler<String> responseHandler;
    private final HttpDestination httpDestination;
    private final HttpClient httpClient;

    @Autowired
    public PersonRepositoryImpl(PersonMapper personMapper, ObjectMapper objectMapper, ResponseHandler<String> responseHandler, HttpDestination httpDestination, HttpClient httpClient) {
        this.personMapper = personMapper;
        this.objectMapper = objectMapper;
        this.responseHandler = responseHandler;
        this.httpDestination = httpDestination;
        this.httpClient = httpClient;
    }

    @Override
    public Person save(Person person) {

        try {

            String requestUrl = format(PEOPLE_ENDPOINT, httpDestination.getUri());

            HttpPost httpPost = new HttpPost(requestUrl);

            PersonDto personDto = personMapper.convertToPersonDto(person);
            String requestBodyJson = jsonWrapper(personDto);
            StringEntity requestEntity = new StringEntity(requestBodyJson, APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            String response = httpClient.execute(httpPost, responseHandler);

            return objectMapper.readValue(response, Person.class);

        } catch (IOException exception) {
            log.error("Connection was aborted", exception);
            throw new EntityNotFoundException("Person wasn't saved");
        }
    }

    @Override
    public List<Person> findAll() {

        List<Person> people = new ArrayList<>();

        try {

            String requestUrl = format(PEOPLE_ENDPOINT, httpDestination.getUri());
            String response = httpClient.execute(new HttpGet(requestUrl), responseHandler);
            people = objectMapper.readValue(response, new TypeReference<List<Person>>() {});

        } catch (IOException exception) {
            log.error("Connection was aborted", exception);
        }
        return people;
    }

    @Override
    public Optional<Person> findById(Long id) {

        Person person = null;

        try {

            String requestUrl = format(PEOPLE_ID_ENDPOINT, httpDestination.getUri(), id);
            String response = httpClient.execute(new HttpGet(requestUrl), responseHandler);
            person = objectMapper.readValue(response, Person.class);

        } catch (IOException exception) {
            log.error("Connection was aborted", exception);
        }
        return Optional.ofNullable(person);
    }

    @Override
    public Person update(PersonDto personDto, Long id) {

        try {

            String requestUrl = format(PEOPLE_ID_ENDPOINT, httpDestination.getUri(), id);

            HttpPut httpPut = new HttpPut(requestUrl);

            String requestBodyJson = jsonWrapper(personDto);
            StringEntity requestEntity = new StringEntity(requestBodyJson, APPLICATION_JSON);
            httpPut.setEntity(requestEntity);

            String response = httpClient.execute(httpPut, responseHandler);

            return objectMapper.readValue(response, Person.class);

        } catch (IOException exception) {
            log.error("An error has occurred in cloud repository: {}", exception.getMessage());
            throw new EntityNotFoundException(format(PERSON_ERROR_PATTERN, id));
        }
    }

    @Override
    public void deleteById(Long id) {
        try {

            String requestUrl = format(PEOPLE_ID_ENDPOINT, httpDestination.getUri(), id);
            HttpDelete httpDelete = new HttpDelete(requestUrl);
            httpClient.execute(httpDelete, responseHandler);

        } catch (IOException exception) {
            log.error("An error has occurred in cloud repository: {}", exception.getMessage());
            throw new EntityNotFoundException(format(PERSON_ERROR_PATTERN, id));
        }
    }

}
