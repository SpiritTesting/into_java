package com.spirittesting.training.intojava.level5.currency;

import java.util.Objects;
import java.util.StringJoiner;

public final class Betrag implements Comparable<Betrag> {

    private static final int TEIL_EINHEIT = 100;

    private final int voll;
    private final int teil;
    private final Währung währung;

    public Betrag(int voll, int teil, Währung währung) {
        int[] nivelliert = nivelliere(voll, teil);
        this.voll = nivelliert[0];
        this.teil = nivelliert[1];

        if (voll < 0) throw new IllegalArgumentException("Betrag muss immer positiv sein");
        this.währung = währung;
    }

    public Währung getWährung() {
        return währung;
    }

    private int[] nivelliere(int voll, int teil) {
        int[] result = {voll, teil};
        while (result[1] < 0) {
            result[0] -= 1;
            result[1] += TEIL_EINHEIT;
        }
        while (result[1] >= TEIL_EINHEIT) {
            result[0] += 1;
            result[1] -= TEIL_EINHEIT;
        }
        return result;
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

    /**
     * compareTo ist eine Methode, die das Interface Comparable erfordert. Mit ihr werden zwei Objekte verglichen.
     * Üblicherweise sollten die Objekte irgendeinen Zahlen- oder Mengenbezug haben, doch man kann in Java problemlos
     * Äpfel mit Birnen vergleichen.
     *
     * compareTo hat ein implizites und ein explizites Argument und errechet daraus eine Zahl. Das implizite Argument ist
     * *this*, also das Objekt, auf dem die Methode aufgerufen wird. Das explizite ist ein Objekt, mit dem *this*
     * verglichen werden soll. Für die Ausgabe gilt:
     *
     *  ist *this* kleiner als das andere Argument, soll die Ausgabe kleiner als 0 sein
     *  ist * this größer als das andere Argument, soll die Ausgabe größer als 0 sein
     *  sind die Objekte gleichwertig, soll die Ausgabe gleich 0 sein.
     *
     *  Dies ist üblichweise relevant im Kontext von Sortieroperationen.
     */
    @Override
    public int compareTo(Betrag that) {
        if (this.getVoll() != that.getVoll()) return this.getVoll() - that.getVoll();
        return this.getTeil() - that.getTeil();
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
        return String.format("%s %d.%02d", währung.name(), voll, teil);
    }

}
