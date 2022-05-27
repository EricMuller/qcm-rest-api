package com.emu.apps.qcm.infra.persistence.adapters.jpa;


import com.emu.apps.qcm.domain.model.category.MpttCategory;
import com.emu.apps.qcm.infra.persistence.MpptCategoryPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttCategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttType;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.MpttCategoryRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.mptt.MpttExceptions;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.CategoryEntityMapper;
import com.emu.apps.shared.exceptions.TechnicalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttCategoryEntity.ROOT_NAME;

/**
 * Created by eric on 14/06/2017.
 */
@Service

public class MpttMpptCategoryPersistenceAdapter implements MpptCategoryPersistencePort {

    private final MpttCategoryRepository mpttCategoryRepository;

    private final CategoryEntityMapper categoryMapper;

    @PersistenceContext
    private EntityManager em;

    public MpttMpptCategoryPersistenceAdapter(MpttCategoryRepository mpttCategoryRepository, CategoryEntityMapper categoryMapper) {
        this.mpttCategoryRepository = mpttCategoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MpttCategory saveCategory(MpttCategory mpttCategory) {
        MpttCategoryEntity mpttCategoryEntity = categoryMapper.dtoToModel(mpttCategory);
        return findOrCreate(mpttCategoryEntity.getUserId(), mpttCategoryEntity.getType().name(), mpttCategoryEntity.getLibelle());
    }


    @Override
    @Transactional
    public Iterable <MpttCategory> findCategories(String userId, String type) {

        MpttCategoryEntity category = findOrCreateRoot(userId, type);
        return categoryMapper.modelsToDtos(mpttCategoryRepository.findSubTree(category));
    }

    @Override
    public Iterable <MpttCategory> findChildrenCategories(UUID parentId) {
        return categoryMapper.modelsToDtos(mpttCategoryRepository.findSubTree(mpttCategoryRepository.findByUuid(parentId)));
    }


    @Override
    public Optional <MpttCategory> findByUuid(String uuid) {
        return Optional.of(categoryMapper.modelToDto(mpttCategoryRepository.findByUuid(UUID.fromString(uuid))));
    }

    private Optional <MpttCategoryEntity> findRootByUserId(String userId) {
        try {
            return Optional.of(mpttCategoryRepository.findHierarchyRoot(userId));
        } catch (MpttExceptions.HierarchyRootDoesNotExistException e) {
            return Optional.empty();
        }
    }

    public Optional <MpttCategory> findRootByUserIdAndType(String userId, String type) {

        Optional <MpttCategoryEntity> userRoot = findRootByUserId(userId);

        if (userRoot.isPresent()) {
            MpttType mpttTypeToFind = MpttType.valueOf(type);
            MpttCategoryEntity category = mpttCategoryRepository.findSubTree(userRoot.get())
                    .stream()
                    .filter(c -> mpttTypeToFind.equals(c.getType()))
                    .findFirst().orElse(null);

            return Optional.of(categoryMapper.modelToDto(category));

        }
        return Optional.empty();
    }

    private MpttCategoryEntity findOrCreateRoot(String userId) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyRootExistsException {
        MpttCategoryEntity category;
        try {
            category = mpttCategoryRepository.findHierarchyRoot(userId);
        } catch (MpttExceptions.HierarchyRootDoesNotExistException e) {
            category = new MpttCategoryEntity(ROOT_NAME);
            mpttCategoryRepository.setAsUserRoot(category, userId);
        }
        return category;
    }

    private MpttCategoryEntity findOrCreateRoot(String userId, String type) {
        MpttType mpttTypeToFind = MpttType.valueOf(type);
        try {

            MpttCategoryEntity userRoot = findOrCreateRoot(userId);

            Optional <MpttCategoryEntity> category = mpttCategoryRepository.findSubTree(userRoot)
                    .stream()
                    .filter(c -> mpttTypeToFind.equals(c.getType()))
                    .findFirst();

            return category.isPresent() ? category.get() :
                    mpttCategoryRepository.createChild(userRoot, new MpttCategoryEntity(mpttTypeToFind, mpttTypeToFind.name()));

        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyRootExistsException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create tree " + mpttTypeToFind.name(), e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MpttCategory findOrCreateByLibelle(String userId, String type, String libelle) {

        return findOrCreate(userId, type, libelle);

    }

    /**
     *  java:S2229 workaround
     * @param userId
     * @param type
     * @param libelle
     * @return
     */
    private MpttCategory findOrCreate(String userId, String type, String libelle) {

        try {
            MpttCategoryEntity categoryRoot = findOrCreateRoot(userId, type);

            return categoryMapper.modelToDto(findCategory(categoryRoot, type, libelle));

        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create category ", e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MpttCategory findOrCreateChildByLibelle(UUID parentId, String type, String libelle) {

        try {
            MpttCategoryEntity parentCategory = mpttCategoryRepository.findByUuid(parentId);
            return categoryMapper.modelToDto(findCategory(parentCategory, type, libelle));
        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create category ", e);
        }

    }

    private MpttCategoryEntity findCategory(MpttCategoryEntity parent, String type, String libelle) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyIdNotSetException {

        Set <MpttCategoryEntity> categories = mpttCategoryRepository.findSubTree(parent);

        Optional <MpttCategoryEntity> category = categories
                .stream()
                .filter(c -> libelle.equals(c.getLibelle()))
                .findFirst();

        if (category.isPresent()) {
            return category.get();
        } else {
            MpttCategoryEntity category1 = mpttCategoryRepository.createChild(parent, new MpttCategoryEntity(MpttType.valueOf(type), libelle));
            return mpttCategoryRepository.findById(category1.getId());
        }
    }

}
