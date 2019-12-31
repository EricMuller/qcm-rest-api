package com.emu.apps.qcm.webmvc.rest.caches;

public enum CacheName {
    QUESTION(Names.QUESTION),
    QUESTIONNAIRE(Names.QUESTIONNAIRE);

    private String libelle;

    CacheName(String aLibelle) {
        this.libelle = aLibelle;
    }

    public class Names {
        private Names() {
            //nop
        }

        public static final String QUESTION = "QUESTION";

        public static final String QUESTIONNAIRE = "QUESTIONNAIRE";
    }


}
