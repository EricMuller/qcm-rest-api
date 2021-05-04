package com.emu.apps.qcm.rest.controllers.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResources {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    private ZonedDateTime dateModification;

    @JsonProperty("email")
    private String email;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("company")
    private String company;

    public UserResources(String email) {
        this.email = email;
    }

}
