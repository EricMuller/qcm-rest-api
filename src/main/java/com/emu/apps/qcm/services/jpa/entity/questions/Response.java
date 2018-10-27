package com.emu.apps.qcm.services.jpa.entity.questions;

import com.emu.apps.qcm.services.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.services.jpa.entity.converters.BooleanTFConverter;

import javax.persistence.*;

@Entity
public class Response extends AuditableEntity<String> {

    @Column(name = "RESPONSE", nullable = false, length = 32672)
    private String response;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "GOOD")
    @Convert(converter = BooleanTFConverter.class)
    private Boolean good;

    public Response() {
        //
    }

    public Response(Type type, String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("Response[id=%d,  response='%s']", getId(), response);
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }
}
