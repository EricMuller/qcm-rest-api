package com.emu.apps.qcm.domain.adapters;


import com.emu.apps.qcm.api.models.Tag;
import com.emu.apps.qcm.domain.ports.TagServicePort;
import com.emu.apps.qcm.spi.persistence.TagPersistencePort;
import com.emu.apps.shared.parsers.rsql.CriteriaUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Tag Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
public class TagServiceAdapter implements TagServicePort {

    private final TagPersistencePort tagInfraService;

    public TagServiceAdapter(TagPersistencePort tagInfraService) {
        this.tagInfraService = tagInfraService;
    }

    @Override
    public Page <Tag> getTags(String search, Pageable pageable, String principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);

        return tagInfraService.findAllByPage(firstLetter, pageable, principal);
    }

    @Override
    public Tag getTagByUuid(String uuid) {
        return tagInfraService.findByUuid(uuid);
    }

    @Override
    public Tag saveTag(Tag tagDto) {
        return tagInfraService.save(tagDto);
    }

}
