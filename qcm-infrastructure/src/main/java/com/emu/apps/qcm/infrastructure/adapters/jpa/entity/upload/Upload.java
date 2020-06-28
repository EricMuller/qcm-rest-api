package com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload;


import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by eric on 05/06/2017.
 */

@Entity
@Table(indexes = {@Index(name = "IDX_UPD_CREATE_BY_IDX", columnList = "created_by")})
@Getter
@Setter
@NoArgsConstructor
public class Upload extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "upload_generator")
    @SequenceGenerator(name = "upload_generator", sequenceName = "upload_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "PATH_FILE_NAME")
    private String pathfileName;

    @Lob
    @Column(name = "DATA")
    private byte[] data;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ImportStatus status;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    @Column(name = "LIBELLE")
    private String libelle;

    @Enumerated(EnumType.STRING)
    private TypeUpload type = TypeUpload.EXPORT_JSON;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Upload(String fileName, String contentType, byte[] data) {
        this.fileName = fileName;
        this.data = data;
        this.contentType = contentType;
    }

}
