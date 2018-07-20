package com.spirittesting.training.intojava.level5.bank;

import com.spirittesting.training.intojava.level5.BankCommands;
import com.spirittesting.training.intojava.level5.currency.Betrag;
import com.spirittesting.training.intojava.level5.currency.WechselkursInterface;
import com.spirittesting.training.intojava.level5.currency.Währung;
import com.spirittesting.training.intojava.level5.currency.Wechselkurs;
import java.sql.Timestamp;
import java.util.*;

public class Bank {

    private final Map<String, Kunde> kunden = new TreeMap<>();
    private final Map<String, List<Betrag>> einzahlungen = new HashMap<>();
    private final Map<String, List<Betrag>> auszahlungen = new HashMap<>();
    private final Map<String, List<Betrag>> auszahlKonto = new HashMap<>();
    private  final Map<String, List<Betrag>> einzahlKonto = new HashMap<>();
    private WechselkursInterface wechselkurs= new WechselkursTabelle(); // new Wechselkurs();

    public WechselkursInterface getWechselkurs() {
        return wechselkurs;
    }

    public void setWechselkurs(WechselkursInterface wechselkurs) {
        this.wechselkurs = wechselkurs;
    }

    public Wechselkurs getWechselkursEURUSD() {
        return wechselkursEURUSD;
    }

    public void setWechselkursEURUSD(Wechselkurs wechselkursEURUSD) {
        this.wechselkursEURUSD = wechselkursEURUSD;
    }

    private  Wechselkurs wechselkursEURUSD;

    public Wechselkurs getWechselkursUSDEUR() {
        return wechselkursUSDEUR;
    }

    public void setWechselkursUSDEUR(Wechselkurs wechselkursUSDEUR) {
        this.wechselkursUSDEUR = wechselkursUSDEUR;
    }

    private Wechselkurs wechselkursUSDEUR;
    public Map<String, List<Betrag>> getEinzahlKonto() {
        return einzahlKonto;
    }
    public Map<String, List<Betrag>> getAuszahlKonto() {
        return auszahlKonto; }

    /*private final Map<String, List<Betrag>> ersparnisEUR = new HashMap<>();

    public final Map<String, List<Betrag>> getErsparnisEUR() {
        return ersparnisEUR;
    }*/
    /*public void Ersparnis() {
        String kundennummer = BankCommands.command.poll;
        Kunde kunde = Bank.getKunde(kundennummer);
        for (Konto konto : kunde.getKonten()) {
            if (konto.getBetrag().getWährung() == Währung.EUR) {
                String ersparnisEUR = konto.getBetrag().toString();
                Double.parseDouble(ersparnisEUR);
                ersparnisEUR += ersparnisEUR;
                String ersparnisEURBetrag = ersparnisEUR + "EUR";
            } else if (konto.getBetrag().getWährung() == Währung.USD) {
                String ersparnisUSD = konto.getBetrag().toString();
                Double.parseDouble(ersparnisUSD);
                ersparnisUSD += ersparnisUSD;
                String ersparnisUSDBetrag = ersparnisUSD + "USD";
            } else if (konto.getBetrag().getWährung() == Währung.GBP) {
                String ersparnisGBP = konto.getBetrag().toString();
                Double.parseDouble(ersparnisGBP);
                ersparnisGBP += ersparnisGBP;
                String ersparnisGBPBetrag = ersparnisGBP + "GBP";
                //String spar = konto.getBetrag().toString();
                //Double.parseDouble(spar);
            } else {
                BankCommands.terminal.print("Keine Ersparnisse");
            }
        }
    }*/

    /*public Map<String, List<Betrag>> getErsparnisEUR() {
        Kunde kunde = Bank.getKunde(kundennummer);
        for (Konto konto : kunde.getKonten()) {
        if (konto.getBetrag().getWährung()==Währung.EUR) {
            String ersparnisEUR = ;
            konto.getBetrag().addiere(konto.getBetrag());
            String spar = konto.getBetrag().toString();
            Double.parseDouble(spar);
            }}
        return ersparnisEUR;
    }*/


    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public Map<String, List<Betrag>> getAuszahlungen() {
        return auszahlungen;
    }
    public Map<String, List<Betrag>> getEinzahlungen() {
        return einzahlungen;
    }

    public Bank() {}
    public Bank(Set<Kunde> kunden) {
        for (Kunde kunde: kunden) this.kunden.put(kunde.getKundennummer(), kunde);
    }

    public List<String> listKunden() {
        return new ArrayList<>(kunden.keySet());
    }

    public Kunde getKunde(String kundennummer) {
        return kunden.get(kundennummer);
    }

    public SortedSet<Konto> getKonten(String kundennummer) {
        SortedSet<Konto> konten = new TreeSet<>();
        konten.addAll(getKunde(kundennummer).getKonten());
        return konten;
    }

    public SortedSet<Konto> getKonten() {
        SortedSet<Konto> konten = new TreeSet<>();
        kunden.values().parallelStream().forEach(kunde -> konten.addAll(getKonten(kunde.getKundennummer())));
        return konten;
    }

    public Konto getKonto(String kontonummer) {
        for (Konto konto : getKonten()) {
            if (konto.getKontonummer().equals(kontonummer)) return konto;
        }
        throw new NoSuchElementException("Konto nicht bekannt");
    }

    public Kunde addKunde(String name) {
        Kunde kunde = new Kunde(name);
        this.kunden.put(kunde.getKundennummer(), kunde);
        return kunde;
    }

    public void removeKunde(String kundennummer) throws KontoNichtAusgeglichenException
    {
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
        Bank bank = this;
        nach.setBetrag(nach.getBetrag().addiereWechsel(betrag, bank.getWechselkurs()));
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

        if (!auszahlKonto.containsKey(von.getKontonummer())){
            auszahlKonto.put(von.getKontonummer(), new ArrayList<>());
        }
        auszahlKonto.get(von.getKontonummer()).add(betrag);
        //String auszahlZeit = new Timestamp(System.currentTimeMillis()).toString();

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

        if (!einzahlKonto.containsKey(nach.getKontonummer())){
            einzahlKonto.put(nach.getKontonummer(), new ArrayList<>());
        }
        einzahlKonto.get(nach.getKontonummer()).add(betrag);

    }

}
