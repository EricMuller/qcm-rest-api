package com.emu.apps.qcm.services.entity.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BasicEntity extends AbstractPersistable<Long> {

    @Column(unique = true, name = "uuid", nullable = false)
    protected String uuid = UUID.randomUUID().toString().toUpperCase();

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @ApiModelProperty(notes = "The database generated Question ID")
//    private Long id;

    @Version
    private Long version;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date dateCreation;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date dateModification;


    /*// @Column(name = "created_by_user", nullable = false)
    @CreatedBy
    private String createdByUser;

    //
    //@Column(name = "modified_by_user", nullable = false)
    @LastModifiedBy
    private String modifiedByUser;
*/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @PrePersist
    public void prePersist() {
        this.setUuid(UUID.randomUUID().toString());
        if (Objects.isNull(getDateCreation())) {
            this.setDateCreation(new Date());
        } else {
            this.setDateModification(new Date());
        }

//        String createdByUser = getUsernameOfAuthenticatedUser();
//        this.createdByUser = createdByUser;
//        this.modifiedByUser = createdByUser;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    // mapstruct issue
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
//
//    private String getUsernameOfAuthenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return "SYS";
//        }
////        if (authentication.getPrincipal() instanceof UserDetails) {
////            return ((UserDetails) authentication.getPrincipal()).getUsername();
////        } else {
////            return (String) authentication.getPrincipal();
////        }
//        return "SYS";
//    }


}