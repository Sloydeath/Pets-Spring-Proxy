package com.leverx.proxypets.dto.pet;

import com.leverx.proxypets.annotations.ValidName;
import com.leverx.proxypets.model.pet.enums.PetType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PetDto {

    private Long id;

    @ValidName
    private String name;

    @NotNull
    private PetType petType;

    @Positive
    private Long personId;
}
