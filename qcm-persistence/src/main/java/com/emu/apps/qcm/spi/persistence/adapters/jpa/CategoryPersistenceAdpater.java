package com.emu.apps.qcm.spi.persistence.adapters.jpa;

import com.emu.apps.qcm.api.models.Category;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.CategoryRepository;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.mptt.MpttExceptions;
import com.emu.apps.qcm.spi.persistence.exceptions.TechnicalException;
import com.emu.apps.qcm.spi.persistence.CategoryPersistencePort;
import com.emu.apps.qcm.spi.persistence.mappers.CategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity.ROOT_NAME;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional
public class CategoryPersistenceAdpater implements CategoryPersistencePort {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryPersistenceAdpater(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public Category saveCategory(Category categoryDto)  {
        CategoryEntity category = categoryMapper.dtoToModel(categoryDto);
        return findOrCreateByLibelle(category.getUserId(), category.getType(), category.getLibelle());
    }


    @Override
    public Iterable <Category> findCategories(String userId, Type type) {
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

    public Optional <Category> findRootByUserIdAndType(String userId, Type type) {

        Optional <CategoryEntity> userRoot = findRootByUserId(userId);

        if (userRoot.isPresent()) {

            CategoryEntity category = categoryRepository.findSubTree(userRoot.get())
                    .stream()
                    .filter(c -> type.equals(c.getType()))
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

    private CategoryEntity findOrCreateRoot(String userId, Type type) throws TechnicalException {
        try {

            CategoryEntity userRoot = findOrCreateRoot(userId);

            Optional <CategoryEntity> category = categoryRepository.findSubTree(userRoot)
                    .stream()
                    .filter(c -> type.equals(c.getType()))
                    .findFirst();

            return category.isPresent() ? category.get() :
                    categoryRepository.createChild(userRoot, new CategoryEntity(type, type.name()));

        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyRootExistsException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create tree " + type.name(), e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Category findOrCreateByLibelle(String userId, Type type, String libelle) throws TechnicalException {

        try {
            CategoryEntity categoryRoot = findOrCreateRoot(userId, type);

            return categoryMapper.modelToDto(findCategory(categoryRoot, type, libelle));

        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create category ", e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Category findOrCreateChildByLibelle(UUID parentId, Type type, String libelle) throws TechnicalException {

        try {
            CategoryEntity parentCategory = categoryRepository.findByUuid(parentId);
            return categoryMapper.modelToDto(findCategory(parentCategory, type, libelle));
        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create category ", e);
        }

    }

    private CategoryEntity findCategory(CategoryEntity parent, Type type, String libelle) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyIdNotSetException {

        Set <CategoryEntity> categories = categoryRepository.findSubTree(parent);

        Optional <CategoryEntity> category = categories
                .stream()
                .filter(c -> libelle.equals(c.getLibelle()))
                .findFirst();

        if (category.isPresent()) {
            return category.get();
        } else {
            CategoryEntity category1 = categoryRepository.createChild(parent, new CategoryEntity(type, libelle));
            return categoryRepository.findById(category1.getId());
        }
    }

}
