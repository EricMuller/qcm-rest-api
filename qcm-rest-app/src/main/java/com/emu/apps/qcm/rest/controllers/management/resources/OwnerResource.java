package com.emu.apps.qcm.rest.controllers.management.resources;

import com.emu.apps.qcm.rest.controllers.management.openui.AccountView.Public;
import com.emu.apps.qcm.rest.controllers.management.openui.AccountView.Update;
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
@JsonRootName(value = "Owner")
public class OwnerResource {

    @JsonView({Public.class, Update.class})
    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonView({Public.class, Update.class})
    @JsonProperty("userName")
    private String userName;

}
