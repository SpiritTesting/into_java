package com.spirittesting.training.intojava.level6.services;

import com.spirittesting.training.intojava.level6.model.Betrag;
import com.spirittesting.training.intojava.level6.model.ZahlungsArt;
import com.spirittesting.training.intojava.level6.model.entities.Konto;
import com.spirittesting.training.intojava.level6.model.entities.Kunde;
import com.spirittesting.training.intojava.level6.model.entities.Zahlung;
import com.spirittesting.training.intojava.level6.repositories.KontoRepository;
import com.spirittesting.training.intojava.level6.repositories.KundenRepository;
import com.spirittesting.training.intojava.level6.repositories.ZahlungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SelbstbedienungsService {

    private final KundenRepository kundenRepository;
    private final KontoRepository kontoRepository;
    private final VerrechnungsService verrechnungsService;
    private final ZahlungRepository zahlungRepository;
    private Kunde kunde;
    private Konto konto;

    @Autowired
    SelbstbedienungsService(KundenRepository kundenRepository, VerrechnungsService verrechnungsService, KontoRepository kontoRepository, ZahlungRepository zahlungRepository) {
        this.kundenRepository = kundenRepository;
        this.verrechnungsService = verrechnungsService;
        this.kontoRepository = kontoRepository;
        this.zahlungRepository = zahlungRepository;
    }

    public String anmelden(String kundennummer) {
        kunde = kundenRepository.findByKundennummer(Integer.parseInt(kundennummer));
        return String.format("Willkommen %s", kunde.getName());
    }

    public String abmelden() {
        kunde = null;
        return "Auf Wiedersehen";
    }

    @Transactional
    public String selectKonto(String kontonummer) {
        konto = kontoRepository.findByKontonummer(Integer.parseInt(kontonummer));
        if (konto == null) return "Fehler Konto nicht gefunden";
        if (!konto.getKunde().equals(kunde)) return "Fehler: Konto nicht bekannt";
        return "Kontostand: " + getKontostand();
    }

    @Transactional
    public String getKontostand() {
        if (konto == null) return "Fehler: kein Konto gewählt";
        // konto aktualisieren
        konto = kontoRepository.findByKontonummer(konto.getKontonummer());
        Betrag betrag = verrechnungsService.verrechne(konto.getAbgehend(), konto.getEingehend());
        return betrag.toString();
    }

    @Transactional
    public String einzahlen(String betrag) {
        if (konto == null) return "Fehler: kein Konto gewählt";
        Zahlung zahlung = new Zahlung();
        zahlung.setBetrag(new Betrag(betrag));
        zahlung.setNach(konto);
        zahlung.setZahlungsArt(ZahlungsArt.Einzahlung);
        zahlungRepository.save(zahlung);
        return "Neuer Kontostand: " + getKontostand();
    }

    @Transactional
    public String auszahlen(String betrag) {
        if (konto == null) return "Fehler: kein Konto gewählt";
        Zahlung zahlung = new Zahlung();
        zahlung.setBetrag(new Betrag(betrag));
        zahlung.setVon(konto);
        zahlung.setZahlungsArt(ZahlungsArt.Auszahlung);
        zahlungRepository.save(zahlung);
        return "Neuer Kontostand: " + getKontostand();
    }

    @Transactional
    public String überweisen(String anKonto, String betrag) {
        if (konto == null) return "Fehler: kein Konto gewählt";
        Zahlung zahlung = new Zahlung();
        zahlung.setBetrag(new Betrag(betrag));
        zahlung.setVon(konto);

        Konto nach = kontoRepository.findByKontonummer(Integer.parseInt(anKonto));
        if (nach == null) return "Fehler: Zielkonto nicht gefunden";
        zahlung.setNach(nach);
        zahlung.setZahlungsArt(ZahlungsArt.Überweisung);
        zahlungRepository.save(zahlung);
        return "Neuer Kontostand: " + getKontostand();
    }

}
