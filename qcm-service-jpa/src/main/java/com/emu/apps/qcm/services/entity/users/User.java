package com.emu.apps.qcm.services.entity.users;

import com.emu.apps.qcm.services.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account",indexes = { @Index(name = "IDX_USR_EMAIL_IDX", columnList = "email") })
public class User extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "user_seq", allocationSize=50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String company;


}
