package com.leverx.proxypets.dto.pet;

import com.leverx.proxypets.annotations.ValidName;
import com.leverx.proxypets.model.Person;
import com.leverx.proxypets.model.pet.enums.PetType;
import lombok.Data;

@Data
public class UpdatePetDto {

    private Long id;

    @ValidName
    private String name;

    private PetType petType;
    private Person person;
}
