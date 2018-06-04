package com.spirittesting.training.intojava.level5.bank;

import com.spirittesting.training.intojava.level5.currency.Betrag;
import com.spirittesting.training.intojava.level5.currency.BetragException;
import com.spirittesting.training.intojava.level5.currency.Währung;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

public class Konto implements Comparable<Konto> {

    private static final AtomicInteger kontonummerGenerator = new AtomicInteger();
    private final String kontonummer;
    private Betrag betrag;

    public Konto(Währung währung) {
        kontonummer = String.format("%08d", kontonummerGenerator.incrementAndGet());
        try {
            this.betrag = new Betrag(0, 0, währung);
        } catch (BetragException e) {
            System.err.println("Wenn hier etwas schiefgeht, haben wir ein ernstes Problem mit dem Betrag");
            e.printStackTrace();
        }
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public Betrag getBetrag() {
        return betrag;
    }

    public void setBetrag(Betrag betrag) {
        this.betrag = betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Konto that = (Konto) o;

        return Objects.equals(this.betrag, that.betrag) &&
                Objects.equals(this.kontonummer, that.kontonummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(betrag, kontonummer);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("betrag = " + betrag)
                .add("kontonummer = " + kontonummer)
                .add("kontonummerGenerator = " + kontonummerGenerator)
                .toString();
    }

    @Override
    public int compareTo(Konto o) {
        return kontonummer.compareTo(o.getKontonummer());
    }
}
