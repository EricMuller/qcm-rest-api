package com.emu.apps.qcm.web.rest.caches;

public enum CacheName {
    QUESTION(Names.QUESTION),
    QUESTIONNAIRE(Names.QUESTIONNAIRE);

    String libelle;

    CacheName(String libelle) {
        this.libelle = libelle;
    }

    public class Names {
        public static final String QUESTION = "QUESTION";
        public static final String QUESTIONNAIRE = "QUESTIONNAIRE";
    }


}
