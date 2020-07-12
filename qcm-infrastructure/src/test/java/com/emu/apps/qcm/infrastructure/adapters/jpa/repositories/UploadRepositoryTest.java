package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.DbFixture;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload.Upload;
import com.emu.apps.qcm.infrastructure.adapters.jpa.config.SpringBootTestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class UploadRepositoryTest {

    @Autowired
    private DbFixture fixture;

    @Autowired
    private UploadRepository uploadRepository;

    @Test
    @Transactional
    public void findOneTest() {

        Upload newUpload = fixture.createUpload();
        assertNotNull(newUpload.getId());

        Optional <Upload> question = uploadRepository.findById(newUpload.getId());
        assertNotNull(question.orElse(null));
        assertNotNull(question.orElse(null).getId());

        assertEquals(newUpload.getFileName(), question.get().getFileName());
        assertEquals(newUpload.getPathfileName(), question.get().getPathfileName());
    }

    @Test
    @Transactional
    public void findAllTest() {

        Upload newUpload = fixture.createUpload();
        assertNotNull(newUpload.getId());

        Upload newUpload2 = fixture.createUpload();
        assertNotNull(newUpload2.getId());

        Pageable sortedBId =
                PageRequest.of(0, 50, Sort.by("id"));

        Page <Upload> all = uploadRepository.findAll(sortedBId);

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all.getTotalElements()).isEqualTo(2);

    }


}
