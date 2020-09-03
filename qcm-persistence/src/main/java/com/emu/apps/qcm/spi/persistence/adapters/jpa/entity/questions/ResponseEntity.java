package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.converters.BooleanTFConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "response")
public class ResponseEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response_generator")
    @SequenceGenerator(name = "response_generator", sequenceName = "response_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @SuppressWarnings("squid:S1700")
    @Column(name = "RESPONSE", nullable = false, length = 32672)
    private String libelle;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "GOOD")
    @Convert(converter = BooleanTFConverter.class)
    private Boolean good;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionEntity question;

    public ResponseEntity(String response) {
        this.libelle = response;
    }

    @Override
    public String toString() {
        return String.format("Response[id=%d,  response='%s']", getId(), libelle);
    }

}
