package com.emu.apps.qcm.domain.jpa.repositories.mptt;

import com.emu.apps.qcm.domain.entity.mptt.MpttHierarchicalEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.emu.apps.qcm.domain.jpa.repositories.mptt.MpttExceptions.*;

public class MpttHierarchicalEntityRepositoryImpl<T extends MpttHierarchicalEntity <T>>
        implements MpttHierarchicalEntityRepository <T> {

    private Class <T> clazz;

    @PersistenceContext
    public EntityManager entityManager;

    public MpttHierarchicalEntityRepositoryImpl(Class <T> clazz) {
        this.clazz = clazz;
    }

    public T findById( Long id) {
        return entityManager.find(clazz, id);
    }

    @Override
    @Transactional
    public void setAsUserRoot(T node, String userId)
            throws HierarchyIdAlreadySetException, HierarchyRootExistsException {
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
    public T createChild(T parent, T child) throws HierarchyIdAlreadySetException, HierarchyIdNotSetException {

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
        child.setRgt(parentRght+ 1);
        child.setLevel(parent.getLevel() + 1);
        child.setUserId(userId);

        entityManager.persist(child);
        entityManager.flush();
        return child;
    }

    @Override
    @Transactional
    public void removeChild(T parent, T child) throws NotADescendantException, HierarchyIdNotSetException,
            NotInTheSameHierarchyException {

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
            throws HierarchyIdNotSetException {
        if (node.getUserId() == null) {
            throw new HierarchyIdNotSetException();
        }
    }

    private void ensureNodeDoesNotBelongToAnyHierarchy(T node)
            throws HierarchyIdAlreadySetException {
        if (node.getUserId() != null) {
            throw new HierarchyIdAlreadySetException();
        }
    }

    private void ensureNodeIsInTheSameHierarchy(T thisNode, T node)
            throws NotInTheSameHierarchyException {
        if (thisNode.getUserId() == null
                || thisNode.getUserId() != node.getUserId())
            throw new NotInTheSameHierarchyException();
    }

    private void ensureNodeIsDescendant(T thisNode, T node)
            throws NotADescendantException, NotInTheSameHierarchyException {
        ensureNodeIsInTheSameHierarchy(thisNode, node);
        if (!(thisNode.getLft() < node.getLft()
                && node.getRgt() < thisNode.getRgt()))
            throw new NotADescendantException();
    }

    private void ensureHierarchyRootIsNotSet(String hierarchyId)
            throws HierarchyRootExistsException {
        try {
            findHierarchyRoot(hierarchyId);

            throw new HierarchyRootExistsException();
        } catch (HierarchyRootDoesNotExistException e) {
            return;
        }
    }

    @Override
    public T findHierarchyRoot(String hierarchyId)
            throws HierarchyRootDoesNotExistException {
        try {
            return entityManager
                    .createNamedQuery("findHierarchyRoot", clazz)
                    .setParameter("hierarchyId", hierarchyId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new HierarchyRootDoesNotExistException();
        }
    }

    @Override
    public T findRightMostChild(T node) {
        return getSingleResultWithoutException(entityManager
                .createNamedQuery("findEntityByRightValue", clazz)
                .setParameter("hierarchyId", node.getUserId())
                .setParameter("value", node.getRgt() - 1)
        );
    }

    @Override
    public Set <T> findEntitiesWhichLftIsGreaterThanOrEqualTo(String userId, Long value) {
        return new HashSet <T>(entityManager
                .createNamedQuery("findEntitiesWithLeftGreaterThanOrEqual", clazz)
                .setParameter("hierarchyId", userId)
                .setParameter("value", value)
                .getResultList()
        );
    }

    @Override
    public Set <T> findEntitiesWhichLftIsGreaterThan(T node, Long value) {
        return new HashSet <T>(entityManager
                .createNamedQuery("findEntitiesWithLeftGreaterThan", clazz)
                .setParameter("hierarchyId", node.getUserId())
                .setParameter("value", value)
                .getResultList()
        );
    }


    @Override
    public Set <T> findEntitiesWhichRgtIsGreaterThan(String userID, Long value) {
        return new HashSet <T>(entityManager
                .createNamedQuery("findEntitiesWithRightGreaterThan", clazz)
                .setParameter("hierarchyId", userID)
                .setParameter("value", value)
                .getResultList()
        );
    }

    @Override
    public Set <T> findEntitiesWhichRgtIsGreaterThanOrEqual(String userID, Long value) {
        return new HashSet <T>(entityManager
                .createNamedQuery("findEntitiesWithRightGreaterThanOrEqual", clazz)
                .setParameter("hierarchyId", userID)
                .setParameter("value", value)
                .getResultList()
        );
    }

    @Override
    public Set <T> findSubTree(T node) {
        return new HashSet <T>(entityManager
                .createNamedQuery("findSubtree", clazz)
                .setParameter("hierarchyId", node.getUserId())
                .setParameter("lft", node.getLft())
                .setParameter("rgt", node.getRgt())
                .setParameter("level", node.getLevel() + 1 )
                .getResultList()
        );
    }

    @Override
    public List <T> findAncestors(T node) {
        return entityManager
                .createNamedQuery("findAncestors", clazz)
                .setParameter("hierarchyId", node.getUserId())
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
                .setParameter("hierarchyId", node.getUserId())
                .setParameter("lft", node.getLft())
                .setParameter("rgt", node.getRgt())
                .getResultList();
    }

    @Override
    public T findParent(T node) {
        List <T> ancestors = findAncestors(node);
        return (
                (ancestors != null) && ancestors.size() > 0)
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
