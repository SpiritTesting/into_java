package com.spirittesting.training.intojava.level5.bank;

import com.spirittesting.training.intojava.level5.currency.Betrag;
import com.spirittesting.training.intojava.level5.currency.Währung;

import java.util.*;

public class Bank {

    private final Map<String, Kunde> kunden = new TreeMap<>();
    private final Map<String, List<Betrag>> einzahlungen = new HashMap<>();
    private final Map<String, List<Betrag>> auszahlungen = new HashMap<>();

    public List<String> listKunden() {
        return new ArrayList<>(kunden.keySet());
    }

    public void addKunde(String name) {
        Kunde kunde = new Kunde(name);
        this.kunden.put(kunde.getKundennummer(), kunde);
    }

    public void removeKunde(String kundennummer) throws KontoNichtAusgeglichenException {
        if (!kunden.containsKey(kundennummer))
            throw new IllegalArgumentException("Diese Kundennummer ist nicht bekannt");
        Kunde kunde = kunden.get(kundennummer);
        for (Konto konto : kunde.getKonten()) {
            if (konto.getBetrag().getVoll() != 0 || konto.getBetrag().getTeil() != 0) {
                throw new KontoNichtAusgeglichenException("Kunde besitzt noch Einlagen");
            }
        }
        kunden.remove(kundennummer);
    }

    public void überweise(Konto von, Konto nach, Betrag betrag) throws KontostandNichtAusreichendException {
        // sind alle Parameter gesetzt?
        if (von == null) throw new IllegalArgumentException("Ausgangskonto darf nicht null sein");
        if (nach == null) throw new IllegalArgumentException("Zielkonto darf nicht null sein");
        if (betrag == null) throw new IllegalArgumentException("Betrag darf nicht null sein");

        // ist genug Geld auf dem Ausgangskonto vorhanden?
        if (von.getBetrag().subtrahiere(betrag).compareTo(new Betrag(0, 0, betrag.getWährung())) < 0) {
            throw new KontostandNichtAusreichendException("Der Betrag steht auf dem Quellkonto nicht zur Verfügung");
        }

        // wir können überweisen
        von.setBetrag(von.getBetrag().subtrahiere(betrag));
        nach.setBetrag(nach.getBetrag().addiere(betrag));
    }

    public void zahleAus(Kunde kunde, Konto von, Betrag betrag) throws KontostandNichtAusreichendException {
        // sind alle Parameter gesetzt?
        if (von == null) throw new IllegalArgumentException("Ausgangskonto darf nicht null sein");
        if (betrag == null) throw new IllegalArgumentException("Betrag darf nicht null sein");
        if (kunde == null) throw new IllegalArgumentException("Kunde darf nicht null sein");

        // ist das Konto eines, auf dass der Kunde zugreifen darf?
        if (!kunde.getKonten().contains(von)) throw new IllegalArgumentException("Kein Kontozugriff");

        // ist genug Geld auf dem Ausgangskonto vorhanden?
        if (von.getBetrag().subtrahiere(betrag).compareTo(new Betrag(0, 0, betrag.getWährung())) < 0) {
            throw new KontostandNichtAusreichendException("Der Betrag steht auf dem Quellkonto nicht zur Verfügung");
        }

        // wir können auszahlen
        von.setBetrag(von.getBetrag().subtrahiere(betrag));

        // der Geldautomat druckt fröhlich die neuen Scheine (oder was auch immer die tun). Aber wir sollten Buch führen
        if (!auszahlungen.containsKey(kunde.getKundennummer())) {
            // Kunde hat noch keine Auszahlungen erhalten. Lege den Eintrag im Register für ihn an
            auszahlungen.put(kunde.getKundennummer(), new ArrayList<>());
        }

        // Auszahlung eintragen
        auszahlungen.get(kunde.getKundennummer()).add(betrag);
    }

    public void zahleEin(Kunde kunde, Konto nach, Betrag betrag) {
        // sind alle Parameter gesetzt?
        if (nach == null) throw new IllegalArgumentException("Eingangskonto darf nicht null sein");
        if (betrag == null) throw new IllegalArgumentException("Betrag darf nicht null sein");
        if (kunde == null) throw new IllegalArgumentException("Kunde darf nicht null sein");

        // ist das Konto eines, auf dass der Kunde zugreifen darf?
        if (!kunde.getKonten().contains(nach)) throw new IllegalArgumentException("Kein Kontozugriff");

        // wir können einzahlen
        nach.setBetrag(nach.getBetrag().addiere(betrag));

        // wir sollten Buch führen
        if (!einzahlungen.containsKey(kunde.getKundennummer())) {
            // Kunde hat noch keine Auszahlungen erhalten. Lege den Eintrag im Register für ihn an
            einzahlungen.put(kunde.getKundennummer(), new ArrayList<>());
        }

        // Einzahlung eintragen
        einzahlungen.get(kunde.getKundennummer()).add(betrag);
    }

}
