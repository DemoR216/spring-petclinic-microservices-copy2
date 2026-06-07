/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maciej Szarlinski
 */
public class OwnerDetails {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private List<PetDetails> pets = new ArrayList<>();

    public OwnerDetails() {
    }

    public OwnerDetails(int id, String firstName, String lastName, String address, String city, String telephone, List<PetDetails> pets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getTelephone() {
        return telephone;
    }

    public List<PetDetails> getPets() {
        return pets;
    }

    public List<Integer> getPetIds() {
        return pets.stream()
            .map(PetDetails::getId)
            .collect(Collectors.toList());
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPets(List<PetDetails> pets) {
        this.pets = pets;
    }

    public static final class OwnerDetailsBuilder {
        private int id;
        private String firstName;
        private String lastName;
        private String address;
        private String city;
        private String telephone;
        private List<PetDetails> pets = new ArrayList<>();

        private OwnerDetailsBuilder() {
        }

        public static OwnerDetailsBuilder anOwnerDetails() {
            return new OwnerDetailsBuilder();
        }

        public OwnerDetailsBuilder id(int id) {
            this.id = id;
            return this;
        }

        public OwnerDetailsBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public OwnerDetailsBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public OwnerDetailsBuilder address(String address) {
            this.address = address;
            return this;
        }

        public OwnerDetailsBuilder city(String city) {
            this.city = city;
            return this;
        }

        public OwnerDetailsBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public OwnerDetailsBuilder pets(List<PetDetails> pets) {
            this.pets = pets;
            return this;
        }

        public OwnerDetails build() {
            return new OwnerDetails(id, firstName, lastName, address, city, telephone, pets);
        }
    }
}
