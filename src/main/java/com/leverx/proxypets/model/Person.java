package com.leverx.proxypets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.proxypets.annotations.ValidName;
import com.leverx.proxypets.model.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Long id;

    @ValidName
    private String name;

    @JsonIgnore
    private List<Pet> pets;

}
