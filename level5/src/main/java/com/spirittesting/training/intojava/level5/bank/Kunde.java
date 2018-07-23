package com.spirittesting.training.intojava.level5.bank;

import com.spirittesting.training.intojava.level5.currency.Betrag;
import com.spirittesting.training.intojava.level5.currency.Währung;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Kunde implements Comparable<Kunde> {

    private static final AtomicInteger kundennummerGenerator = new AtomicInteger();
    private final String kundennummer;
    private final Set<Konto> konten = new HashSet<>();
    private String name;

    public Kunde(String name) {
        this.kundennummer = String.format("KDNR%04d", kundennummerGenerator.incrementAndGet());
        this.name = name;
    }

    public String getKundennummer() {
        return kundennummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Konto> getKonten() {
        return konten;
    }

    public void removeKonto(Konto konto) throws KontoNichtAusgeglichenException {
        if (!konten.contains(konto)) throw new NoSuchElementException("Nicht mein Konto!");
        if (konto.getBetrag().getVoll() != 0 || konto.getBetrag().getTeil() != 0) throw new KontoNichtAusgeglichenException("Das Konto ist nicht ausgeglichen");
        konten.remove(konto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kunde that = (Kunde) o;

        return Objects.equals(this.konten, that.konten) &&
                Objects.equals(this.kundennummer, that.kundennummer) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(konten, kundennummer, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("kundennummer = " + kundennummer)
                .add("name = " + name)
                .add("konten = " + konten)
                .toString();
    }

    @Override
    public int compareTo(Kunde o) {
        return this.kundennummer.compareTo(o.kundennummer);
    }

    public Betrag getNetWorth(Währung währung) {
        Betrag summe = new Betrag(0, 0, währung);
        for (Konto konto : getKonten()) {
            final Betrag betrag = konto.getBetrag();
            if (betrag.getWährung().equals(währung)) summe = summe.addiere(betrag);
        }
        return summe;
    }

}
