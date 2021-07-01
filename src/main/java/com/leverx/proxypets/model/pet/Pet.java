package com.leverx.proxypets.model.pet;

import com.leverx.proxypets.annotations.ValidName;
import com.leverx.proxypets.model.Person;
import com.leverx.proxypets.model.pet.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    private Long id;

    @ValidName
    private String name;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PetType petType;

    @NotNull
    private Person person;

}
