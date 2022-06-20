package com.emu.apps.qcm.rest.controllers.management.resources;

import com.emu.apps.qcm.rest.controllers.management.openui.AccountView.PublicAccount;
import com.emu.apps.qcm.rest.controllers.management.openui.AccountView.UpdateAccount;
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

    @JsonView({PublicAccount.class, UpdateAccount.class})
    @JsonProperty("uuid")
    private String uuid;

    @JsonView({UpdateAccount.class})
    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @JsonProperty("email")
    @JsonView({UpdateAccount.class})
    private String email;

    @JsonView({PublicAccount.class, UpdateAccount.class})
    @JsonProperty("userName")
    private String userName;

    @JsonView({UpdateAccount.class})
    @JsonProperty("firstName")
    private String firstName;

    @JsonView({UpdateAccount.class})
    @JsonProperty("lastName")
    private String lastName;

    @JsonView({UpdateAccount.class})
    @JsonProperty("company")
    private String company;


}
