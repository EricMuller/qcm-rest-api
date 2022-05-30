package com.emu.apps.qcm.domain.model.base;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Aggregate<T extends DomainObject> {

    private T root;

    protected Aggregate(@NonNull T root) {
        this.root = Objects.requireNonNull(root, "root must not be null");
    }

}
