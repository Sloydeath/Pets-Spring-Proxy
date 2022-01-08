package com.leverx.proxypets.dto.pet;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class SwappingPetsDto {

    @NotNull
    @Positive
    private Long firstPetId;

    @NotNull
    @Positive
    private Long secondPetId;
}
