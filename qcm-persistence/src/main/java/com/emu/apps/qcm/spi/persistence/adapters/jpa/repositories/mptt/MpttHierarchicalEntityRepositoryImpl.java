package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.mptt;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.common.MpttHierarchicalEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MpttHierarchicalEntityRepositoryImpl<T extends MpttHierarchicalEntity <T>>
        implements MpttHierarchicalEntityRepository <T> {

    public static final String HIERARCHY_ID = "hierarchyId";

    public static final String VALUE = "value";

    private final Class <T> clazz;

    @PersistenceContext
    public EntityManager entityManager;

    public MpttHierarchicalEntityRepositoryImpl(Class <T> clazz) {
        this.clazz = clazz;
    }

    public void save(T t) {
        entityManager.persist(t);
    }

    public T findById(Long id) {
        return entityManager.find(clazz, id);
    }

    public T findByUuid(UUID uuid) {
        return entityManager
                .createNamedQuery("findByUuid", clazz)
                .setParameter("uuid", uuid)
                .getSingleResult();

    }

    @Override
    @Transactional
    public void setAsUserRoot(T node, String userId)
            throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyRootExistsException {
        ensureNodeDoesNotBelongToAnyHierarchy(node);
        ensureHierarchyRootIsNotSet(userId);

        node.setUserId(userId);
        node.setLft(1L);
        node.setRgt(2L);

        entityManager.persist(node);
        entityManager.flush();
    }

    @Override
    @Transactional
    public T createChild(T parent, T child) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyIdNotSetException {

        ensureNodeBelongsToAHierarchy(parent);
        ensureNodeDoesNotBelongToAnyHierarchy(child);

        Long parentRght = parent.getRgt();
        String userId = parent.getUserId();
        // Add  2 at parent R Node
        Set <T> parentsRght = findEntitiesWhichRgtIsGreaterThanOrEqual(userId, parentRght);
        incrementEntitiesRgt(parentsRght, 2L);

        Set <T> parentsLft = findEntitiesWhichLftIsGreaterThanOrEqualTo(userId, parentRght);
        incrementEntitiesLft(parentsLft, 2L);

        child.setLft(parentRght);
        child.setRgt(parentRght + 1);
        child.setLevel(parent.getLevel() + 1);
        child.setUserId(userId);

        entityManager.persist(child);
        entityManager.flush();
        return child;
    }

    @Override
    @Transactional
    public void removeChild(T parent, T child) throws MpttExceptions.NotADescendantException, MpttExceptions.HierarchyIdNotSetException,
            MpttExceptions.NotInTheSameHierarchyException {

        Set <T> childSubTree = findSubTree(child);

        ensureNodeBelongsToAHierarchy(parent);
        ensureNodeIsDescendant(parent, child);

        Long decrement = child.getRgt() - child.getLft() + 1;
        decrementEntitiesLftWith(findEntitiesWhichLftIsGreaterThan(parent, child.getRgt()), decrement);
        decrementEntitiesRgtWith(findEntitiesWhichRgtIsGreaterThan(parent.getUserId(), child.getRgt()), decrement);

        resetEntities(childSubTree);

        removeSubTree(childSubTree);
    }

    private void addValueToEntitiesLft(Set <T> entities, Long value) {
        for (T entity : entities) {
            entity.setLft(entity.getLft() + value);
        }
    }

    private void addValueToEntitiesRgt(Set <T> entities, Long value) {
        for (T entity : entities) {
            entity.setRgt(entity.getRgt() + value);
        }
    }

    private void incrementEntitiesLft(Set <T> entities, Long value) {
        addValueToEntitiesLft(entities, value);
    }

    private void incrementEntitiesRgt(Set <T> entities, Long value) {
        addValueToEntitiesRgt(entities, value);
    }

    private void decrementEntitiesLftWith(Set <T> entities, Long value) {
        addValueToEntitiesLft(entities, -value);
    }

    private void decrementEntitiesRgtWith(Set <T> entities, Long value) {
        addValueToEntitiesRgt(entities, -value);
    }

    private void resetEntities(Set <T> entities) {
        for (T entity : entities) {
            entity.setLft(null);
            entity.setRgt(null);
            entity.setUserId(null);
        }
    }

    private void ensureNodeBelongsToAHierarchy(T node)
            throws MpttExceptions.HierarchyIdNotSetException {
        if (node.getUserId() == null) {
            throw new MpttExceptions.HierarchyIdNotSetException();
        }
    }

    private void ensureNodeDoesNotBelongToAnyHierarchy(T node)
            throws MpttExceptions.HierarchyIdAlreadySetException {
        if (node.getUserId() != null) {
            throw new MpttExceptions.HierarchyIdAlreadySetException();
        }
    }

    private void ensureNodeIsInTheSameHierarchy(T thisNode, T node)
            throws MpttExceptions.NotInTheSameHierarchyException {
        if (thisNode.getUserId() == null
                || thisNode.getUserId().equals(node.getUserId()))
            throw new MpttExceptions.NotInTheSameHierarchyException();
    }

    private void ensureNodeIsDescendant(T thisNode, T node)
            throws MpttExceptions.NotADescendantException, MpttExceptions.NotInTheSameHierarchyException {
        ensureNodeIsInTheSameHierarchy(thisNode, node);
        if (!(thisNode.getLft() < node.getLft()
                && node.getRgt() < thisNode.getRgt()))
            throw new MpttExceptions.NotADescendantException();
    }

    private void ensureHierarchyRootIsNotSet(String hierarchyId)
            throws MpttExceptions.HierarchyRootExistsException {
        try {
            findHierarchyRoot(hierarchyId);

            throw new MpttExceptions.HierarchyRootExistsException();
        } catch (MpttExceptions.HierarchyRootDoesNotExistException e) {
                // nope
        }
    }

    @Override
    public T findHierarchyRoot(String hierarchyId)
            throws MpttExceptions.HierarchyRootDoesNotExistException {
        try {
            return entityManager
                    .createNamedQuery("findHierarchyRoot", clazz)
                    .setParameter(HIERARCHY_ID, hierarchyId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new MpttExceptions.HierarchyRootDoesNotExistException();
        }
    }

    @Override
    public T findRightMostChild(T node) {
        return getSingleResultWithoutException(entityManager
                .createNamedQuery("findEntityByRightValue", clazz)
                .setParameter(HIERARCHY_ID, node.getUserId())
                .setParameter(VALUE, node.getRgt() - 1)
        );
    }

    @Override
    public Set <T> findEntitiesWhichLftIsGreaterThanOrEqualTo(String userId, Long value) {
        return new HashSet <>(entityManager
                .createNamedQuery("findEntitiesWithLeftGreaterThanOrEqual", clazz)
                .setParameter(HIERARCHY_ID, userId)
                .setParameter(VALUE, value)
                .getResultList()
        );
    }

    @Override
    public Set <T> findEntitiesWhichLftIsGreaterThan(T node, Long value) {
        return new HashSet <>(entityManager
                .createNamedQuery("findEntitiesWithLeftGreaterThan", clazz)
                .setParameter(HIERARCHY_ID, node.getUserId())
                .setParameter(VALUE, value)
                .getResultList()
        );
    }


    @Override
    public Set <T> findEntitiesWhichRgtIsGreaterThan(String userID, Long value) {
        return new HashSet <>(entityManager
                .createNamedQuery("findEntitiesWithRightGreaterThan", clazz)
                .setParameter(HIERARCHY_ID, userID)
                .setParameter(VALUE, value)
                .getResultList()
        );
    }

    @Override
    public Set <T> findEntitiesWhichRgtIsGreaterThanOrEqual(String userID, Long value) {
        return new HashSet <>(entityManager
                .createNamedQuery("findEntitiesWithRightGreaterThanOrEqual", clazz)
                .setParameter(HIERARCHY_ID, userID)
                .setParameter(VALUE, value)
                .getResultList()
        );
    }

    @Override
    public Set <T> findSubTree(T node) {
        return new HashSet <>(entityManager
                .createNamedQuery("findSubtree", clazz)
                .setParameter(HIERARCHY_ID, node.getUserId())
                .setParameter("lft", node.getLft())
                .setParameter("rgt", node.getRgt())
                .setParameter("level", node.getLevel() + 1)
                .getResultList()
        );
    }

    @Override
    public List <T> findAncestors(T node) {
        return entityManager
                .createNamedQuery("findAncestors", clazz)
                .setParameter(HIERARCHY_ID, node.getUserId())
                .setParameter("lft", node.getLft())
                .setParameter("rgt", node.getRgt())
                .getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager
                .createNamedQuery("deleteAll").executeUpdate();
    }


    @Override
    public List <T> findChildren(T node) {
        return entityManager
                .createNamedQuery("findChildren", clazz)
                .setParameter(HIERARCHY_ID, node.getUserId())
                .setParameter("lft", node.getLft())
                .setParameter("rgt", node.getRgt())
                .getResultList();
    }

    @Override
    public T findParent(T node) {
        List <T> ancestors = findAncestors(node);
        return (
                (ancestors != null) && !ancestors.isEmpty())
                ? ancestors.get(ancestors.size() - 1)
                : null;
    }

    @Transactional
    public void remove(T node) {
        if (entityManager.contains(node)) {
            entityManager.remove(node);
        } else {
            T attached = entityManager.find(clazz, node.getId());
            entityManager.remove(attached);
        }
    }

    private void removeSubTree(Set <T> subTree) {
        for (T s : subTree) {
            remove(s);
        }
    }

    private static <T> T getSingleResultWithoutException(TypedQuery <T> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
