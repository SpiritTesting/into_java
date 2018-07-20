package com.spirittesting.training.intojava.level5.currency;

import com.spirittesting.training.intojava.level5.bank.Bank;

import java.util.Objects;

import static com.spirittesting.training.intojava.level5.currency.Währung.EUR;
import static com.spirittesting.training.intojava.level5.currency.Währung.GBP;
import static com.spirittesting.training.intojava.level5.currency.Währung.USD;
import static com.spirittesting.training.intojava.level5.currency.Wechselkurs.*;

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
     *
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

    public Betrag wechsle( WechselkursInterface wechselkurs, Währung zielWährung) {
        Betrag geldwert = this;
        Währung währung = zielWährung;

        int v =0;
        int t=0;

        double faktor = 1.0;

        if (geldwert.getWährung() != währung && geldwert.getWährung() == EUR && währung == USD) {
            faktor = wechselkurs.getWechselkursEURUSD();
        } else if (geldwert.getWährung() != währung && geldwert.getWährung() == EUR && währung == GBP) {
            faktor = wechselkurs.getWechselkursEURGBP();
        } else if (geldwert.getWährung() != währung && geldwert.getWährung() == USD && währung == GBP) {
            faktor = wechselkurs.getWechselkursUSDGBP();
        } else if (geldwert.getWährung() != währung && geldwert.getWährung() == USD && währung == EUR) {
            faktor = wechselkurs.getWechselkursUSDEUR();
        } else if (geldwert.getWährung() != währung && geldwert.getWährung() == GBP && währung == EUR) {
            faktor = wechselkurs.getWechselkursGBPEUR();
        } else if (geldwert.getWährung() != währung && geldwert.getWährung() == GBP && währung == USD) {
            faktor = wechselkurs.getWechselkursGBPUSD();
        } else if (geldwert.getWährung() == währung) {
            faktor = 1.0;
        }

        double zwischenwert = geldwert.getVoll() + 0.01 * geldwert.getTeil(); // voll.teil
        // 99.11

        double umgerechnet = zwischenwert * faktor;
        v = (int) Math.floor(umgerechnet); // 99
        double umgerechnetTeil = umgerechnet - v; // 99.11 - 99 = 0.11
        t = (int) Math.floor(umgerechnetTeil * 100); // 11 // fixed

        System.out.println("ungewechselter Betrag: "+this.toString());

        System.out.println("faktor: "+ faktor);


        Betrag result = new Betrag( v,  t, zielWährung);
        System.out.println("gewechselter Betrag"+  result.toString());
        return result;
    }

    public Betrag addiereWechsel(Betrag geldwert, WechselkursInterface wechselkurs) throws BetragException {

        Währung zielWährung = this.getWährung();

        Betrag umgerechnet = geldwert.wechsle(wechselkurs, zielWährung);

        Betrag result = this.addiere(umgerechnet); // haben durch wechsel gleiche währung

        return result;

    }

    public Betrag addiere(Betrag geldwert) throws BetragException {
        if (geldwert.getWährung() != währung) throw new BetragException("Geldwechsel nicht möglich");
        Betrag betrag = (Betrag) geldwert;
        return new Betrag(voll + betrag.getVoll(), teil + betrag.getTeil(), währung);
    }

    public Betrag subtrahiere(Betrag geldwert) throws BetragException {
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
