package com.spirittesting.training.intojava.level5.bank;

import com.spirittesting.training.intojava.level5.KontoBewegung;
import com.spirittesting.training.intojava.level5.currency.Betrag;
import com.spirittesting.training.intojava.level5.currency.BetragException;
import com.spirittesting.training.intojava.level5.currency.Währung;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Konto implements Comparable<Konto> {

    private static final AtomicInteger kontonummerGenerator = new AtomicInteger();
    private final String kontonummer;
    private Betrag betrag;
    private List<KontoBewegung> kontobewegungen = new ArrayList<>();
    private Set<String> lastschriftGläubiger = new HashSet<>();
    private Betrag dispo;

    public Konto(Währung währung) {
        kontonummer = String.format("%08d", kontonummerGenerator.incrementAndGet());
        try {
            this.betrag = new Betrag(0, 0, währung);
            this.dispo = new Betrag(0,0, währung);
        } catch (BetragException e) {
            System.err.println("Wenn hier etwas schiefgeht, haben wir ein ernstes Problem mit dem Betrag");
            e.printStackTrace();
        }
    }

    public void addLastschriftGläubiger(String name) {
        lastschriftGläubiger.add(name);
    }
    public void removeLastschriftGläubiger(String name) {
        lastschriftGläubiger.remove(name);
    }
    public boolean isLastschriftErlaubt(String gläubiger) {
        return lastschriftGläubiger.contains(gläubiger);
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
                .add("dispo = " + dispo)
                .toString();
    }

    @Override
    public int compareTo(Konto o) {
        return kontonummer.compareTo(o.getKontonummer());
    }

    public Iterable<? extends KontoBewegung> getKontoBewegungen() {
        return kontobewegungen;
    }

    public void addKontoBewegung(Betrag betrag, KontoBewegung.Richtung richtung) {
        this.kontobewegungen.add(new KontoBewegung(betrag, richtung));
    }

    public void addToDispo(Betrag dispo) {
        this.betrag = this.betrag.addiere(dispo);
        this.dispo = this.dispo.addiere(dispo);
        this.addKontoBewegung(dispo, KontoBewegung.Richtung.DISPO);
    }

    public void removeFromDispo(Betrag dispo) {
        this.betrag = this.betrag.subtrahiere(dispo);
        this.dispo = this.dispo.subtrahiere(dispo);
        this.addKontoBewegung(dispo, KontoBewegung.Richtung.DISPO);
    }
}
