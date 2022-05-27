package com.emu.apps.qcm.rest.controllers.management.resources;

import com.emu.apps.qcm.rest.controllers.management.openui.AccountView.Public;
import com.emu.apps.qcm.rest.controllers.management.openui.AccountView.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

import static com.emu.apps.qcm.rest.controllers.management.resources.Constants.ZONED_DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "Account")
public class AccountResource {

    @JsonView({Public.class, Update.class})
    @JsonProperty("uuid")
    private String uuid;

    @JsonView({Update.class})
    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @JsonProperty("email")
    @JsonView({Update.class})
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


}
