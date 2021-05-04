package com.emu.apps.qcm.infra.persistence.adapters.jpa;


import com.emu.apps.qcm.domain.model.Category;
import com.emu.apps.qcm.infra.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.CategoryRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.mptt.MpttExceptions;
import com.emu.apps.qcm.infra.persistence.mappers.CategoryEntityMapper;
import com.emu.apps.shared.exceptions.TechnicalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity.ROOT_NAME;

/**
 * Created by eric on 14/06/2017.
 */
@Service

public class CategoryPersistenceAdpater implements CategoryPersistencePort {

    private final CategoryRepository categoryRepository;

    private final CategoryEntityMapper categoryMapper;

    public CategoryPersistenceAdpater(CategoryRepository categoryRepository, CategoryEntityMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Category saveCategory(Category category) {
        CategoryEntity categoryEntity = categoryMapper.dtoToModel(category);
        return findOrCreate(categoryEntity.getUserId(), categoryEntity.getType().name(), categoryEntity.getLibelle());
    }


    @Override
    @Transactional(readOnly = true)
    public Iterable <Category> findCategories(String userId, String type) {

        CategoryEntity category = findOrCreateRoot(userId, type);
        return categoryMapper.modelsToDtos(categoryRepository.findSubTree(category));
    }

    @Override
    public Iterable <Category> findChildrenCategories(UUID parentId) {
        return categoryMapper.modelsToDtos(categoryRepository.findSubTree(categoryRepository.findByUuid(parentId)));
    }


    @Override
    public Optional <Category> findByUuid(String uuid) {
        return Optional.of(categoryMapper.modelToDto(categoryRepository.findByUuid(UUID.fromString(uuid))));
    }

    private Optional <CategoryEntity> findRootByUserId(String userId) {
        try {
            return Optional.of(categoryRepository.findHierarchyRoot(userId));
        } catch (MpttExceptions.HierarchyRootDoesNotExistException e) {
            return Optional.empty();
        }
    }

    public Optional <Category> findRootByUserIdAndType(String userId, String type) {

        Optional <CategoryEntity> userRoot = findRootByUserId(userId);

        if (userRoot.isPresent()) {
            Type typeToFind = Type.valueOf(type);
            CategoryEntity category = categoryRepository.findSubTree(userRoot.get())
                    .stream()
                    .filter(c -> typeToFind.equals(c.getType()))
                    .findFirst().orElse(null);

            return Optional.of(categoryMapper.modelToDto(category));

        }
        return Optional.empty();
    }

    private CategoryEntity findOrCreateRoot(String userId) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyRootExistsException {
        CategoryEntity category;
        try {
            category = categoryRepository.findHierarchyRoot(userId);
        } catch (MpttExceptions.HierarchyRootDoesNotExistException e) {
            category = new CategoryEntity(ROOT_NAME);
            categoryRepository.setAsUserRoot(category, userId);
        }
        return category;
    }

    private CategoryEntity findOrCreateRoot(String userId, String type) {
        Type typeToFind = Type.valueOf(type);
        try {

            CategoryEntity userRoot = findOrCreateRoot(userId);

            Optional <CategoryEntity> category = categoryRepository.findSubTree(userRoot)
                    .stream()
                    .filter(c -> typeToFind.equals(c.getType()))
                    .findFirst();

            return category.isPresent() ? category.get() :
                    categoryRepository.createChild(userRoot, new CategoryEntity(typeToFind, typeToFind.name()));

        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyRootExistsException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create tree " + typeToFind.name(), e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Category findOrCreateByLibelle(String userId, String type, String libelle) {

        return findOrCreate(userId, type, libelle);

    }

    /**
     *  java:S2229 workaround
     * @param userId
     * @param type
     * @param libelle
     * @return
     */
    private Category findOrCreate(String userId, String type, String libelle) {

        try {
            CategoryEntity categoryRoot = findOrCreateRoot(userId, type);

            return categoryMapper.modelToDto(findCategory(categoryRoot, type, libelle));

        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create category ", e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Category findOrCreateChildByLibelle(UUID parentId, String type, String libelle) {

        try {
            CategoryEntity parentCategory = categoryRepository.findByUuid(parentId);
            return categoryMapper.modelToDto(findCategory(parentCategory, type, libelle));
        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create category ", e);
        }

    }

    private CategoryEntity findCategory(CategoryEntity parent, String type, String libelle) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyIdNotSetException {

        Set <CategoryEntity> categories = categoryRepository.findSubTree(parent);

        Optional <CategoryEntity> category = categories
                .stream()
                .filter(c -> libelle.equals(c.getLibelle()))
                .findFirst();

        if (category.isPresent()) {
            return category.get();
        } else {
            CategoryEntity category1 = categoryRepository.createChild(parent, new CategoryEntity(Type.valueOf(type), libelle));
            return categoryRepository.findById(category1.getId());
        }
    }

}
