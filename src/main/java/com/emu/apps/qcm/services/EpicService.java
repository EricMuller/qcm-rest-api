package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.epics.Epic;

public interface EpicService {

    Epic save(Epic epic);

    Epic findById(Long id);

    Epic findByLibelle(String libelle);

    Epic findOrCreateByLibelle(String libelle);

    Iterable<Epic> findAll();


}
