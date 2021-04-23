package com.emu.apps.qcm.rest.config.cache;

public enum CacheName {
    QUESTION(Names.QUESTION),
    QUESTIONNAIRE(Names.QUESTIONNAIRE);

    private final String libelle;

    CacheName(String aLibelle) {
        this.libelle = aLibelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public class Names {

        public static final String QUESTION = "QUESTION";

        public static final String QUESTIONNAIRE = "QUESTIONNAIRE";

        private Names() {
            //nop
        }
    }


}
