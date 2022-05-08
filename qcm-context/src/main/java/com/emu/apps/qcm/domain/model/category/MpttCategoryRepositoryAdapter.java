package com.emu.apps.qcm.domain.model.category;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.infra.persistence.MpptCategoryPersistencePort;
import org.springframework.stereotype.Service;


/**
 * Category Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
class MpttCategoryRepositoryAdapter implements MpttCategoryRepository {

    private final MpptCategoryPersistencePort mpptCategoryPersistencePort;

    public MpttCategoryRepositoryAdapter(MpptCategoryPersistencePort mpptCategoryPersistencePort) {
        this.mpptCategoryPersistencePort = mpptCategoryPersistencePort;
    }


    @Override
    public MpttCategory getCategoryByUuid(String id) {
        return mpptCategoryPersistencePort.findByUuid(id).orElse(null);
    }


    @Override
    public Iterable <MpttCategory> getCategories(PrincipalId principal, String type) {
        return mpptCategoryPersistencePort.findCategories(principal.toUuid(), type);
    }


    @Override
    public MpttCategory saveCategory(MpttCategory category, PrincipalId principal) {
        category.setUserId(principal.toUuid());
        return mpptCategoryPersistencePort.saveCategory(category);
    }

}
