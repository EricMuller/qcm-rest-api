package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.infrastructure.DbFixture;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload.UploadEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
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

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
public class UploadRepositoryTest {

    @Autowired
    private DbFixture dbFixture;

    @Autowired
    private UploadRepository uploadRepository;

    @Test
    @Transactional
    public void findOneTest() {

        UploadEntity newUpload = dbFixture.createUpload();
        assertNotNull(newUpload.getId());

        Optional <UploadEntity> question = uploadRepository.findById(newUpload.getId());
        assertNotNull(question.orElse(null));
        assertNotNull(question.orElse(null).getId());

        assertEquals(newUpload.getFileName(), question.get().getFileName());
        assertEquals(newUpload.getPathfileName(), question.get().getPathfileName());
    }

    @Test
    @Transactional
    public void findAllTest() {

        UploadEntity newUpload = dbFixture.createUpload();
        assertNotNull(newUpload.getId());

        UploadEntity newUpload2 = dbFixture.createUpload();
        assertNotNull(newUpload2.getId());

        Pageable sortedBId =
                PageRequest.of(0, 50, Sort.by("id"));

        Page <UploadEntity> all = uploadRepository.findAll(sortedBId);

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all.getTotalElements()).isEqualTo(2);

    }


}
