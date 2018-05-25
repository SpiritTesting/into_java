package com.spirittesting.training.intojava.level4;

class Ausgabe {
    private final String titel;
    private final String inhalt;

    Ausgabe(String titel, String inhalt) {
        this.titel = titel;
        this.inhalt = inhalt;
    }

    public String getTitel() {
        return titel == null ? "" : titel;
    }

    public String getInhalt() {
        return inhalt == null ? "" : inhalt;
    }

}
