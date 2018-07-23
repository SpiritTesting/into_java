package com.spirittesting.training.intojava.level5.currency;

import java.util.Objects;

public final class Betrag implements Comparable<Betrag> {

    private static final int TEIL_EINHEIT = 100;

    private final int voll;
    private final int teil;
    private final Währung währung;

    public Betrag(int voll, int teil, Währung währung) throws BetragException {
        int[] nivelliert = nivelliere(voll, teil);
        this.voll = nivelliert[0];
        this.teil = nivelliert[1];

        if (voll < 0) throw new BetragException("Betrag muss immer positiv sein");
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
        Betrag summand = geldwert;
        if (summand.getWährung() != währung) {
            System.out.println("Geldwechsel: " + geldwert + " in " + summand + " getauscht");
            summand = geldwert.convertTo(währung);
        }
        return new Betrag(voll + summand.getVoll(), teil + summand.getTeil(), währung);
    }

    public Betrag subtrahiere(Betrag geldwert) throws BetragException{
        Betrag summand = geldwert;
        if (summand.getWährung() != währung) {
            System.out.println("Geldwechsel: " + geldwert + " in " + summand + " getauscht");
            summand = geldwert.convertTo(währung);
        }
        return new Betrag(voll - summand.getVoll(), teil - summand.getTeil(), währung);
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

    private Betrag convertTo(Währung nach) {
        double wechselkurs = währung.to(nach);
        int wert = this.voll * 100 + this.teil;
        int converted = (int) Math.floor(wert * wechselkurs);
        Betrag wechselwert = new Betrag(0, converted, nach);
        return wechselwert;
    }
}
