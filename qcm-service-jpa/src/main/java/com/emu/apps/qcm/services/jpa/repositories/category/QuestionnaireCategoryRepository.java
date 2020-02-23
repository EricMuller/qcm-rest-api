package com.emu.apps.qcm.services.jpa.repositories.category;

import com.emu.apps.qcm.services.entity.category.QuestionCategory;
import com.emu.apps.qcm.services.entity.category.QuestionnaireCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public interface QuestionnaireCategoryRepository extends PagingAndSortingRepository<QuestionnaireCategory, Long> {

    @Query("SELECT DISTINCT q FROM QuestionnaireCategory q WHERE q.libelle  = :libelle ")
    QuestionCategory findByLibelle(@Param("libelle") String libelle);
}
