package com.emu.apps.qcm.infrastructure.adapters.jpa;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.CategoryRepository;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.mptt.MpttExceptions;
import com.emu.apps.qcm.infrastructure.exceptions.TechnicalException;
import com.emu.apps.qcm.infrastructure.ports.CategoryPersistencePort;
import com.emu.apps.qcm.mappers.CategoryMapper;
import com.emu.apps.qcm.domain.dtos.CategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category.ROOT_NAME;

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
    public CategoryDto saveCategory(CategoryDto categoryDto)  {
        Category category = categoryMapper.dtoToModel(categoryDto);
        return findOrCreateByLibelle(category.getUserId(), category.getType(), category.getLibelle());
    }


    @Override
    public Iterable <CategoryDto> findCategories(String userId, Type type) {
        Category category = findOrCreateRoot(userId, type);
        return categoryMapper.modelsToDtos(categoryRepository.findSubTree(category));
    }

    @Override
    public Iterable <CategoryDto> findChildrenCategories(UUID parentId) {
        return categoryMapper.modelsToDtos(categoryRepository.findSubTree(categoryRepository.findByUuid(parentId)));
    }


    @Override
    public Optional <CategoryDto> findByUuid(String uuid) {
        return Optional.of(categoryMapper.modelToDto(categoryRepository.findByUuid(UUID.fromString(uuid))));
    }

    private Optional <Category> findRootByUserId(String userId) {
        try {
            return Optional.of(categoryRepository.findHierarchyRoot(userId));
        } catch (MpttExceptions.HierarchyRootDoesNotExistException e) {
            return Optional.empty();
        }
    }

    public Optional <CategoryDto> findRootByUserIdAndType(String userId, Type type) {

        Optional <Category> userRoot = findRootByUserId(userId);

        if (userRoot.isPresent()) {

            Category category = categoryRepository.findSubTree(userRoot.get())
                    .stream()
                    .filter(c -> type.equals(c.getType()))
                    .findFirst().orElse(null);

            return Optional.of(categoryMapper.modelToDto(category));

        }
        return Optional.empty();
    }

    private Category findOrCreateRoot(String userId) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyRootExistsException {
        Category category;
        try {
            category = categoryRepository.findHierarchyRoot(userId);
        } catch (MpttExceptions.HierarchyRootDoesNotExistException e) {
            category = new Category(ROOT_NAME);
            categoryRepository.setAsUserRoot(category, userId);
        }
        return category;
    }

    private Category findOrCreateRoot(String userId, Type type) throws TechnicalException {
        try {

            Category userRoot = findOrCreateRoot(userId);

            Optional <Category> category = categoryRepository.findSubTree(userRoot)
                    .stream()
                    .filter(c -> type.equals(c.getType()))
                    .findFirst();

            return category.isPresent() ? category.get() :
                    categoryRepository.createChild(userRoot, new Category(type, type.name()));

        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyRootExistsException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create tree " + type.name(), e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CategoryDto findOrCreateByLibelle(String userId, Type type, String libelle) throws TechnicalException {

        try {
            Category categoryRoot = findOrCreateRoot(userId, type);

            return categoryMapper.modelToDto(findCategory(categoryRoot, type, libelle));

        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create category ", e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CategoryDto findOrCreateChildByLibelle(UUID parentId, Type type, String libelle) throws TechnicalException {

        try {
            Category parentCategory = categoryRepository.findByUuid(parentId);
            return categoryMapper.modelToDto(findCategory(parentCategory, type, libelle));
        } catch (MpttExceptions.HierarchyIdAlreadySetException | MpttExceptions.HierarchyIdNotSetException e) {
            throw new TechnicalException("Error create category ", e);
        }

    }

    private Category findCategory(Category parent, Type type, String libelle) throws MpttExceptions.HierarchyIdAlreadySetException, MpttExceptions.HierarchyIdNotSetException {

        Set <Category> categories = categoryRepository.findSubTree(parent);

        Optional <Category> category = categories
                .stream()
                .filter(c -> libelle.equals(c.getLibelle()))
                .findFirst();

        if (category.isPresent()) {
            return category.get();
        } else {
            Category category1 = categoryRepository.createChild(parent, new Category(type, libelle));
            return categoryRepository.findById(category1.getId());
        }
    }

}
