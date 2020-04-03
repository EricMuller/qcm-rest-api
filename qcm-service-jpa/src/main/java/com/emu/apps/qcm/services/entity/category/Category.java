package com.emu.apps.qcm.services.entity.category;

import com.emu.apps.qcm.services.entity.common.AuditableEntity;
import com.emu.apps.qcm.services.entity.mptt.MpttHierarchicalEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by eric on 05/06/2017.
 */

@Entity
@Table(indexes = {@Index(name = "IDX_CTG_CREATE_BY_IDX", columnList = "type,created_by")})
@NoArgsConstructor
@Getter
@Setter
@NamedQueries(value = {

        @NamedQuery(name = "deleteAll", query = "DELETE FROM Category"),

        @NamedQuery(name = "findAncestors", query =
                "SELECT node" +
                        " FROM Category node" +
                        " WHERE :hierarchyId IS NOT NULL AND :hierarchyId = node.userId" +
                        " AND node.lft < :lft AND :rgt < node.rgt" +
                        " ORDER BY node.lft ASC"),

        @NamedQuery(name = "findSubtree", query =
                "SELECT node" +
                        " FROM Category node" +
                        " WHERE :hierarchyId IS NOT NULL AND :hierarchyId = node.userId" +
                        " AND node.lft > :lft  AND node.rgt <= :rgt " +
                        " AND :level  = node.level "
        ),

        @NamedQuery(name = "findEntitiesWithLeftGreaterThan", query =
                "SELECT node" +
                        " FROM Category node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.lft > :value  "),

        @NamedQuery(name = "findEntitiesWithLeftGreaterThanOrEqual", query =
                "SELECT node" +
                        " FROM Category node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.lft >= :value "),

        @NamedQuery(name = "findEntitiesWithRightGreaterThan", query =
                "SELECT node" +
                        " FROM Category node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.rgt > :value "),

        @NamedQuery(name = "findEntitiesWithRightGreaterThanOrEqual", query =
                "SELECT node" +
                        " FROM Category node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.rgt >= :value "),

        @NamedQuery(name = "findEntityByRightValue", query =
                "SELECT node" +
                        " FROM Category node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.rgt = :value"),

        @NamedQuery(name = "findHierarchyRoot", query =
                "SELECT node" +
                        " FROM Category node" +
                        " WHERE :hierarchyId IS NOT NULL AND node.userId = :hierarchyId  " +
                        " AND node.lft = 1"),

        @NamedQuery(name = "findChildren", query =
                "SELECT child" +
                        " FROM Category child" +
                        " WHERE :hierarchyId IS NOT NULL" +
                        " AND child.userId = :hierarchyId" +
                        " AND :lft < child.lft AND child.rgt < :rgt" +
                        " AND :lft =" +
                        " (SELECT MAX(ancestor.lft)" +
                        "   FROM Category ancestor" +
                        "   WHERE ancestor.userId = child.userId" +
                        "   AND ancestor.lft < child.lft" +
                        "   AND child.rgt < ancestor.lft)" +
                        " ORDER BY child.lft ASC")
})
public class Category extends AuditableEntity <String> implements MpttHierarchicalEntity <Category> {

    public static final String ROOT_NAME = "root";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "category_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    protected String libelle;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long lft;

    @Column(nullable = false)
    private Long rgt;

    @Column(nullable = false)
    private Integer level;

    public Category(String libelle) {
        this.libelle = libelle;
        this.setLevel(0);
    }

    public Category(Type type, String libelle) {
        this.type = type;
        this.libelle = libelle;
    }

}
