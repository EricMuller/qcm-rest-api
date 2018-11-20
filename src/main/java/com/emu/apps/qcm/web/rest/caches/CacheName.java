package com.emu.apps.qcm.web.rest.caches;

public enum CacheName {
    QUESTION(Names.QUESTION),
    QUESTIONNAIRE(Names.QUESTIONNAIRE);

    private String libelle;

    CacheName(String aLibelle) {
        this.libelle = aLibelle;
    }

    public class Names {
        public static final String QUESTION = "QUESTION";

        public static final String QUESTIONNAIRE = "QUESTIONNAIRE";
    }


}
