package com.leverx.proxypets.controller;

import com.leverx.proxypets.dto.pet.PetDto;
import com.leverx.proxypets.dto.pet.ResponsePetDto;
import com.leverx.proxypets.dto.pet.SwappingPetsDto;
import com.leverx.proxypets.dto.pet.UpdatePetDto;
import com.leverx.proxypets.mapper.PetMapper;
import com.leverx.proxypets.model.pet.Pet;
import com.leverx.proxypets.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.leverx.proxypets.util.ControllerConstantUtil.PET_CONTROLLER_ENDPOINT;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(PET_CONTROLLER_ENDPOINT)
public class PetController {

    private final PetService petService;
    private final PetMapper petMapper;

    @Autowired
    public PetController(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponsePetDto>> getAllPets() {

        List<ResponsePetDto> responsePetsDto = petService.getAll()
                .stream()
                .map(petMapper::convertToResponsePetDto)
                .collect(toList());

        return new ResponseEntity<>(responsePetsDto, OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDto> getPetById(@PathVariable Long id) {

        Pet pet = petService.getById(id);
        ResponsePetDto responsePetDto = petMapper.convertToResponsePetDto(pet);

        return new ResponseEntity<>(responsePetDto, OK);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDto> savePet(@Valid @RequestBody PetDto receivedPetDto) {

        Pet pet = petService.create(petMapper.convertToEntity(receivedPetDto));
        ResponsePetDto responsePetDto = petMapper.convertToResponsePetDto(pet);

        return new ResponseEntity<>(responsePetDto, OK);
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDto> updatePet(@Valid @RequestBody UpdatePetDto updatePetDto,
                                               @PathVariable Long id) {

        Pet pet = petService.updateName(updatePetDto, id);
        ResponsePetDto responsePetDto = petMapper.convertToResponsePetDto(pet);

        return new ResponseEntity<>(responsePetDto, OK);
    }

    @PatchMapping(value = "/swapping", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponsePetDto>> swapPets(@Valid @RequestBody SwappingPetsDto swappingPetsDto) {

        List<ResponsePetDto> responsePetsDto = petService.swappingPets(swappingPetsDto)
                .stream()
                .map(petMapper::convertToResponsePetDto)
                .collect(toList());

        return new ResponseEntity<>(responsePetsDto, OK);
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        petService.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}
