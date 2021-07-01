package com.leverx.proxypets.dto.person;

import com.leverx.proxypets.annotations.ValidName;
import lombok.Data;

@Data
public class PersonDto {

    private Long id;

    @ValidName
    private String name;
}
