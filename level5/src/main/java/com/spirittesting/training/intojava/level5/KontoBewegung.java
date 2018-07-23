package com.spirittesting.training.intojava.level5;

import com.spirittesting.training.intojava.level5.currency.Betrag;

public class KontoBewegung {

    private final Betrag betrag;
    private final Richtung richtung;

    public KontoBewegung(Betrag betrag, Richtung richtung) {
        this.betrag = betrag;
        this.richtung = richtung;
    }

    public Betrag getBetrag() {
        return betrag;
    }

    public Richtung getRichtung() {
        return richtung;
    }

    @Override
    public String toString() {
        return betrag.toString() + " " + richtung.toString();
    }

    public enum Richtung {
        ZUGANG("haben"),
        ABGANG("soll"),
        LASTSCHRIFT("lastschrift"),
        DISPO("Bereitstellung Dispositionskredit");

        private String ausgabe;
        Richtung(String ausgabe) {
            this.ausgabe = ausgabe;
        }

        @Override
        public String toString() {
            return ausgabe;
        }
    }
}
