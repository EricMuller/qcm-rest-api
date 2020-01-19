package com.emu.apps.qcm.webmvc.services.jpa.entity.questions;

import com.emu.apps.qcm.webmvc.services.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.webmvc.services.jpa.entity.converters.BooleanTFConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Response extends AuditableEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response_generator")
    @SequenceGenerator(name="response_generator", sequenceName = "response_seq", allocationSize=50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @SuppressWarnings("squid:S1700")
    @Column(name = "RESPONSE", nullable = false, length = 32672)
    private String response;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "GOOD")
    @Convert(converter = BooleanTFConverter.class)
    private Boolean good;

    public Response(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return String.format("Response[id=%d,  response='%s']", getId(), response);
    }


}
