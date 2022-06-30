package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.converters.BooleanTFConverter;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "RESPONSE")
public class ResponseEntity extends AuditableEntity <Long, String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @SuppressWarnings("squid:S1700")
    @Column(name = "RESPONSE", nullable = false, length = 32672)
    private String responseText;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "GOOD")
    @Convert(converter = BooleanTFConverter.class)
    private Boolean good;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionEntity question;

    public ResponseEntity(String response) {
        this.responseText = response;
    }

    @Override
    public String toString() {
        return String.format("Response[id=%d,  response='%s']", getId(), responseText);
    }

}
