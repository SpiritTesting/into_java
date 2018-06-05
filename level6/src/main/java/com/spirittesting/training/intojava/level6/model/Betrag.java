package com.spirittesting.training.intojava.level6.model;

public final class Betrag implements Comparable<Betrag> {

    private static final int TEIL_EINHEIT = 100;

    private final int voll;
    private final int teil;
    private final Währung währung;

    public Betrag(int voll, int teil, Währung währung) throws BetragException {
        int[] nivelliert = nivelliere(voll, teil);
        this.voll = nivelliert[0];
        this.teil = nivelliert[1];

        this.währung = währung;
    }

    /**
     * Erzeugt einen Betrag aus einem String. Der String muss wie folgt formatiert sein:
     * [Währungskürzel][Ganzzahlanteil][Punkt][Zwei Nachkommastellen]
     * z.B. EUR123.45 oder USD122.53
     * Jede andere Formatierung führt zu einem Fehler
     * @param asString
     */
    public Betrag(String asString) throws BetragException {
        try {
            währung = Währung.valueOf(asString.substring(0, 3));
            int voll = Integer.parseInt(asString.substring(3, asString.length() - 3));
            int teil = Integer.parseInt(asString.substring(asString.length() - 2));

            int[] nivelliert = nivelliere(voll, teil);
            this.voll = nivelliert[0];
            this.teil = nivelliert[1];

        } catch (Exception e) {
            throw new BetragException("Betrag kann nicht erzeugt werden");
        }
        if (voll < 0) throw new BetragException("Betrag muss immer positiv sein");
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

    public Betrag addiere(Betrag geldwert) throws BetragException {
        if (geldwert.getWährung() != währung) throw new BetragException("Geldwechsel nicht möglich");
        Betrag betrag = (Betrag) geldwert;
        return new Betrag(voll + betrag.getVoll(), teil + betrag.getTeil(), währung);
    }

    public Betrag subtrahiere(Betrag geldwert) throws BetragException{
        if (geldwert.getWährung() != währung) throw new BetragException("Geldwechsel nicht möglich");
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
    public String toString() {
        return String.format("%s%d.%02d", währung.name(), voll, teil);
    }

    @Override
    public int compareTo(Betrag that) {
        if (this.getVoll() != that.getVoll()) return this.getVoll() - that.getVoll();
        return this.getTeil() - that.getTeil();
    }
}
