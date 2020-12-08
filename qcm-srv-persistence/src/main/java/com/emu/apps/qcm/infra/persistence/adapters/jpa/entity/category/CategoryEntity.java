package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.MpttHierarchicalEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Created by eric on 05/06/2017.
 */

@Entity
@Table(name = "category",
        indexes = {@Index(name = "IDX_CTG_CREATE_BY_IDX", columnList = "type,created_by"),
                @Index(name = "IDX_CTG_UUID_IDX", columnList = "uuid")})
@NoArgsConstructor
@Getter
@Setter
@NamedQueries(value = {

        @NamedQuery(name = "deleteAll", query = "DELETE FROM CategoryEntity"),

        @NamedQuery(name = "findByUuid", query = "SELECT node FROM CategoryEntity node WHERE node.uuid = :uuid "),
        @NamedQuery(name = "findAncestors", query =
                " SELECT node" +
                        " FROM CategoryEntity node" +
                        " WHERE :hierarchyId IS NOT NULL AND :hierarchyId = node.userId" +
                        " AND node.lft < :lft AND :rgt < node.rgt" +
                        " ORDER BY node.lft ASC"),

        @NamedQuery(name = "findSubtree", query =
                " SELECT node" +
                        " FROM CategoryEntity node" +
                        " WHERE :hierarchyId IS NOT NULL AND :hierarchyId = node.userId" +
                        " AND node.lft > :lft  AND node.rgt <= :rgt " +
                        " AND :level  = node.level "
        ),

        @NamedQuery(name = "findEntitiesWithLeftGreaterThan", query =
                " SELECT node" +
                        " FROM CategoryEntity node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.lft > :value  "),

        @NamedQuery(name = "findEntitiesWithLeftGreaterThanOrEqual", query =
                " SELECT node" +
                        " FROM CategoryEntity node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.lft >= :value "),

        @NamedQuery(name = "findEntitiesWithRightGreaterThan", query =
                " SELECT node" +
                        " FROM CategoryEntity node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.rgt > :value "),

        @NamedQuery(name = "findEntitiesWithRightGreaterThanOrEqual", query =
                " SELECT node" +
                        " FROM CategoryEntity node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.rgt >= :value "),

        @NamedQuery(name = "findEntityByRightValue", query =
                "SELECT node" +
                        " FROM CategoryEntity node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.rgt = :value"),

        @NamedQuery(name = "findHierarchyRoot", query =
                "SELECT node" +
                        " FROM CategoryEntity node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.lft = 1"),

        @NamedQuery(name = "findChildren", query =
                "SELECT child" +
                        " FROM CategoryEntity child" +
                        " WHERE :hierarchyId IS NOT NULL" +
                        " AND child.userId = :hierarchyId" +
                        " AND :lft < child.lft AND child.rgt < :rgt" +
                        " AND :lft =" +
                        " (SELECT MAX(ancestor.lft)" +
                        "   FROM CategoryEntity ancestor" +
                        "   WHERE ancestor.userId = child.userId" +
                        "   AND ancestor.lft < child.lft" +
                        "   AND child.rgt < ancestor.lft)" +
                        " ORDER BY child.lft ASC")
})
public class CategoryEntity extends AuditableEntity <String> implements MpttHierarchicalEntity <CategoryEntity> {

    public static final String ROOT_NAME = "root";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "category_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    protected String libelle;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long lft;

    @Column(nullable = false)
    private Long rgt;

    @Column(nullable = false)
    private Integer level;

    public CategoryEntity(String libelle) {
        this.libelle = libelle;
        this.setLevel(0);
    }

    public CategoryEntity(Type type, String libelle) {
        this.type = type;
        this.libelle = libelle;
    }

    public static final class CategorySpecificationBuilder<T> extends AuditableEntity.BaseSpecification <T> {

        private static final String TYPE_FIELD_NAME = "type";

        private String principal;

        private Type type;

        public CategorySpecificationBuilder <T> setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public CategorySpecificationBuilder <T> setType(Type type) {
            this.type = type;
            return this;
        }

        protected Specification <T> fieldEquals(String attribute, Type value) {
            return (root, query, cb) -> Objects.isNull(value) ? null : cb.equal(root.get(attribute), value);
        }

        @Override
        public Specification <T> build() {
            return (root, query, cb) -> where(
                    fieldEquals(TYPE_FIELD_NAME, type)).and(fieldEquals(CREATED_BY, principal)
            ).toPredicate(root, query, cb);
        }
    }

    }
