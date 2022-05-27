package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Cacheable
@Table(name = "webhooks")
public class WebHookEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Column(name ="content_type", nullable = false)
    private String contentType = MediaType.APPLICATION_JSON_VALUE;

    @Column(name = "secret", nullable = false)
    private String secret;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private AccountEntity owner;

    @Column(name = "DEFAULT_TIME_OUT")
    private Long defaultTimeOut;

}
