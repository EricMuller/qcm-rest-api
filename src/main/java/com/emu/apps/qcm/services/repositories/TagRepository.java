package com.emu.apps.qcm.services.repositories;

import com.emu.apps.qcm.services.entity.tags.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
//@Api(tags = "tags")
//@RepositoryRestResource(collectionResourceRel = "tags", path = "/api/v1/tags")
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

    @Query("SELECT DISTINCT q FROM Tag q WHERE q.libelle  = :libelle ")
    Tag findByLibelle(@Param("libelle") String libelle);

    Iterable<Tag> findByLibelleContaining(String libelle);

}
