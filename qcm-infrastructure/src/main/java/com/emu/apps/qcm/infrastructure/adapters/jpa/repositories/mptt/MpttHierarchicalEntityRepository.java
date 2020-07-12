package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.mptt;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.common.MpttHierarchicalEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MpttHierarchicalEntityRepository<T extends MpttHierarchicalEntity <T>> {

    void deleteAll();

    void setAsUserRoot(T entity, String hierarchyId) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyRootExistsException;

    T findHierarchyRoot(String hierarchyId) throws MpttExceptions.HierarchyRootDoesNotExistException;

    T findRightMostChild(T node);

    Set <T> findEntitiesWhichLftIsGreaterThanOrEqualTo(String userId, Long value);

    Set <T> findEntitiesWhichLftIsGreaterThan(T node, Long value);

    Set <T> findEntitiesWhichRgtIsGreaterThan(String userId, Long value);

    Set <T> findEntitiesWhichRgtIsGreaterThanOrEqual(String userID, Long value) ;

    Set <T> findSubTree(T node);

    List <T> findAncestors(T node);

    List <T> findChildren(T node);

    T findParent(T node);


//    void setClazz(Class <T> clazzToSet);

    T createChild(T parent, T child) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyIdNotSetException;

    void removeChild(T parent, T child) throws MpttExceptions.NotADescendantException, MpttExceptions.HierarchyIdNotSetException, MpttExceptions.NotInTheSameHierarchyException;

}
