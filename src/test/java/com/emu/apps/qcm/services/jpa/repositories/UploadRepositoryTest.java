package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.SpringBootTestCase;
import com.emu.apps.qcm.services.jpa.entity.upload.Upload;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UploadRepositoryTest extends SpringBootTestCase {

    @Autowired
    private UploadRepository uploadRepository;

    @Test
    @Transactional
    public void findOneTest() {

        Upload newUpload = getFixtureService().createUpload();
        Assert.assertNotNull(newUpload.getId());

        Optional<Upload> question = uploadRepository.findById(newUpload.getId());
        Assert.assertNotNull(question.orElse(null));
        Assert.assertNotNull(question.orElse(null).getId());

        Assert.assertEquals(newUpload.getFileName(),question.get().getFileName());
        Assert.assertEquals(newUpload.getPathfileName(),question.get().getPathfileName());
    }

    @Test
    @Transactional
    public void findAllTest() {

        Upload newUpload = getFixtureService().createUpload();
        Assert.assertNotNull(newUpload.getId());

        Upload newUpload2 = getFixtureService().createUpload();
        Assert.assertNotNull(newUpload2.getId());

        Pageable sortedBId =
                PageRequest.of(0, 50, Sort.by("id"));

        Page<Upload> all  = uploadRepository.findAll(sortedBId);

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all.getTotalElements()).isEqualTo(2);

    }



}
