package com.emu.apps.qcm.services.entity.questions;

import com.emu.apps.qcm.services.entity.common.AuditableEntity;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Response extends AuditableEntity<String> {

    @Column(name = "RESPONSE", nullable = false, length = 32672)
    private String response;

    @Column(name = "NUMBER")
    private Long number;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Choice> choices;

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

    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = choices;
    }
}
