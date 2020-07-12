package com.emu.apps.qcm.infrastructure.adapters.jpa.entity.settings;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(indexes = {@Index(name = "IDX_NTN_CREATE_BY_IDX", columnList = "created_by"),
        @Index(name = "IDX_NTN_UUID_IDX", columnList = "uuid"),
        @Index(name = "IDX_NTN_ORIGIN_IDX", columnList = "origin")
})
@Getter
@Setter
@NoArgsConstructor
public class WebHookNotification extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_generator")
    @SequenceGenerator(name = "notification_generator", sequenceName = "notification_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationActionEnum action;

    @Column(name = "origin", nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID origin;


}
