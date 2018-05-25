package com.spirittesting.training.intojava.level5.currency;

import java.util.Objects;
import java.util.StringJoiner;

public class Betrag {

    private final int voll;
    private final int teil;
    private final Währung währung;

    public Betrag(int voll, int teil, Währung währung) {
        this.voll = voll;
        this.teil = teil;
        while (teil < 0) {
            teil += 100;
            voll--;
        }
        while (teil >= 100) {
            voll++;
            teil -= 100;
        }
        if (voll < 0) throw new IllegalArgumentException("Betrag muss immer positiv sein");
        this.währung = währung;
    }

    public Währung getWährung() {
        return währung;
    }

    public Betrag addiere(Betrag geldwert) {
        if (geldwert.getWährung() != währung) throw new IllegalArgumentException("Geldwechsel nicht möglich");
        Betrag betrag = (Betrag) geldwert;
        return new Betrag(voll + betrag.getVoll(), teil + betrag.getTeil(), währung);
    }

    public Betrag subtrahiere(Betrag geldwert) {
        if (geldwert.getWährung() != währung) throw new IllegalArgumentException("Geldwechsel nicht möglich");
        Betrag betrag = (Betrag) geldwert;
        return new Betrag(voll - betrag.getVoll(), teil - betrag.getTeil(), währung);
    }

    public int getVoll() {
        return voll;
    }

    public int getTeil() {
        return teil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Betrag that = (Betrag) o;

        return Objects.equals(this.teil, that.teil) &&
                Objects.equals(this.voll, that.voll) &&
                Objects.equals(this.währung, that.währung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teil, voll, währung);
    }

    @Override
    public String toString() {
        return String.format("%s %d.%d", währung.name(), voll, teil);
    }

}
