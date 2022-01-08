package com.leverx.proxypets.factory;

import com.leverx.proxypets.exception.custom.PetNotFoundException;
import com.leverx.proxypets.model.pet.Cat;
import com.leverx.proxypets.model.pet.Dog;
import com.leverx.proxypets.model.pet.Pet;
import com.leverx.proxypets.model.pet.enums.PetType;

public class PetFactory {

    public static Pet getPet(PetType petType) {

        switch (petType) {
            case CAT:
                return new Cat();
            case DOG:
                return new Dog();
            default:
                throw new PetNotFoundException("Pet not found");
        }
    }
}
