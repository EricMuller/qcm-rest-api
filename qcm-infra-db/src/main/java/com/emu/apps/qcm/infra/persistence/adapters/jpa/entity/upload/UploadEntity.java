package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.upload;


import com.emu.apps.qcm.domain.model.imports.ImportStatus;
import com.emu.apps.qcm.domain.model.upload.TypeUpload;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;

import static org.springframework.data.jpa.domain.Specification.where;


/**
 * Created by eric on 05/06/2017.
 */

@Entity
@Table(name = "UPLOAD",
        indexes = {
                @Index(name = "IDX_UPD_CREATE_BY", columnList = "created_by"),
                @Index(name = "IDX_UPD_UUID", columnList = "uuid")}
        , uniqueConstraints = {@UniqueConstraint(name = "UK_UPD_UUID", columnNames = {"uuid"})}
)
@Getter
@Setter
@NoArgsConstructor
public class UploadEntity extends AuditableEntity <Long, String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public UploadEntity(String fileName, String contentType, byte[] data) {
        this.fileName = fileName;
        this.data = data;
        this.contentType = contentType;
    }

    public static final class SpecificationBuilder extends BaseSpecification <UploadEntity> {

        private String principal;

        public SpecificationBuilder() {
            // nope
        }

        public SpecificationBuilder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        @Override
        public Specification <UploadEntity> build() {
            Specification where = fieldEquals(CREATED_BY, principal);
            return (root, query, cb) -> where(where).toPredicate(root, query, cb);
        }


    }
}
