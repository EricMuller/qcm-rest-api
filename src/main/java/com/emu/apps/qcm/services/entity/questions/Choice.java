package com.emu.apps.qcm.services.entity.questions;

import com.emu.apps.qcm.services.entity.common.BasicEntity;
import com.emu.apps.qcm.services.entity.converters.BooleanTFConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
public class Choice extends BasicEntity {

    @Column(name = "MESSAGE", nullable = false, length = 1024)
    private String message;

    @Column(name = "POSITION", nullable = false)
    private Long position;

    @Convert(converter = BooleanTFConverter.class)
    private Boolean good;

    public Choice() {
    }

    public  Choice(String message, Long number, Boolean isGood) {
        this.message = message;
        this.position = number;
        this.good = isGood;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }
}
