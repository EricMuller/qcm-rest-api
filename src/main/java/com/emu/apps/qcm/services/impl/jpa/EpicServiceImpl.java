package com.emu.apps.qcm.services.impl.jpa;

import com.emu.apps.qcm.services.entity.epics.Epic;
import com.emu.apps.qcm.services.repositories.EpicRepository;
import com.emu.apps.qcm.services.EpicService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eric on 14/06/2017.
 */
@Service
public class EpicServiceImpl implements EpicService {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EpicRepository epicRepository;

    @Override
    public Epic save(Epic epic) {
        return epicRepository.save(epic);
    }

    @Override
    public Epic findById(Long id) {
        return epicRepository.findOne(id);
    }

    @Override
    public Epic findByLibelle(String libelle) {
        return epicRepository.findByLibelle(libelle);
    }

    @Override
    public Epic findOrCreateByLibelle(String libelle) {
        Epic epic = epicRepository.findByLibelle(libelle);
        if(epic == null ) {
            epic = save(new Epic(libelle));
        }
        return epic;
    }


    @Override
    public Iterable<Epic> findAll() {
        return epicRepository.findAll();
    }

}
