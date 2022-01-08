package com.leverx.proxypets.dto.pet;

import com.leverx.proxypets.annotations.ValidName;
import com.leverx.proxypets.model.Person;
import com.leverx.proxypets.model.pet.enums.PetType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResponsePetDto {

    private Long id;

    @ValidName
    private String name;

    @NotNull
    private PetType petType;

    @NotNull
    private Person person;
}
