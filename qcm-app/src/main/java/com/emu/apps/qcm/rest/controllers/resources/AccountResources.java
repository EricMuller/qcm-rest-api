package com.emu.apps.qcm.rest.controllers.resources;

import com.emu.apps.qcm.rest.controllers.resources.openui.AccountView.Public;
import com.emu.apps.qcm.rest.controllers.resources.openui.AccountView.Update;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "Account")
public class AccountResources {

    @JsonView({Public.class, Update.class})
    @JsonProperty("uuid")
    private String uuid;

    @JsonView({Update.class})
    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    private ZonedDateTime dateModification;

    @JsonProperty("email")
    private String email;

    @JsonView({Public.class, Update.class})
    @JsonProperty("userName")
    private String userName;

    @JsonView({Update.class})
    @JsonProperty("firstName")
    private String firstName;

    @JsonView({Update.class})
    @JsonProperty("lastName")
    private String lastName;

    @JsonView({Update.class})
    @JsonProperty("company")
    private String company;

    public AccountResources(String email) {
        this.email = email;
    }

}
