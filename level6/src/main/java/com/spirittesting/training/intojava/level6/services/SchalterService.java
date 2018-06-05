package com.spirittesting.training.intojava.level6.services;

import com.spirittesting.training.intojava.level6.model.Betrag;
import com.spirittesting.training.intojava.level6.model.entities.Konto;
import com.spirittesting.training.intojava.level6.model.entities.Kunde;
import com.spirittesting.training.intojava.level6.repositories.KontoRepository;
import com.spirittesting.training.intojava.level6.repositories.KundenRepository;
import com.spirittesting.training.intojava.level6.services.exceptions.KontoauflösungFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchalterService {

    private final KundenRepository kundenRepository;
    private final KontoRepository kontoRepository;
    private final VerrechnungsService verrechnungsService;
    private Kunde kunde;

    @Autowired
    SchalterService(KundenRepository kundenRepository, VerrechnungsService verrechnungsService, KontoRepository kontoRepository) {
        this.kundenRepository = kundenRepository;
        this.verrechnungsService = verrechnungsService;
        this.kontoRepository = kontoRepository;
    }

    public Kunde addKunde(String name) {
        Kunde kunde = new Kunde();
        kunde.setName(name);
        return kundenRepository.save(kunde);
    }

    @Transactional
    public void removeKunde(String kundennummer) {
        /* Kunden dürfen nur gelöscht werden, wenn alle Konten leer sind */
        Kunde kunde = kundenRepository.findByKundennummer(Integer.parseInt(kundennummer));
        for (Konto konto : kunde.getKonten()) {
            Betrag restbetrag = verrechnungsService.verrechne(konto.getAbgehend(), konto.getEingehend());
            if (restbetrag.getVoll() != 0 || restbetrag.getTeil() != 0)
                throw new KontoauflösungFailedException("Restbetrag vorhanden bei Konto#" + konto.getKontonummer());
        }
        
        kundenRepository.delete(kunde);
    }

    @Transactional
    public List<Kunde> getAllKunden() {
        return kundenRepository.findAll();
    }

    public String selectKunde(String kundennummer) {
        kunde = kundenRepository.findByKundennummer(Integer.parseInt(kundennummer));
        return String.format("Gewählter Kunde: %s", kunde.getName());
    }

    public String deselectKunde() {
        kunde = null;
        return "Gewählter Kunde: <keiner>";
    }

    public List<Konto> listKonten() {
        if (kunde == null) return new ArrayList<>();
        return kontoRepository.findByKunde(kunde);
    }

    @Transactional
    public String addKonto() {
        if (kunde == null) return "Fehler: Kein Kunde gewählt";
        Konto konto = new Konto();
        konto.setKunde(kunde);
        kontoRepository.save(konto);
        return String.format("Kontonummer: %d", konto.getKontonummer());
    }

    @Transactional
    public String removeKonto(int kontonummer) {
        Konto konto = kontoRepository.findByKontonummer(kontonummer);
        if (konto == null) return "Fehler: Konto nicht gefunden";
        if (!konto.getKunde().equals(kunde)) return "Fehler: Konto gehört nicht dem gewählten Kunden";
        Betrag betrag = verrechnungsService.verrechne(konto.getAbgehend(), konto.getEingehend());
        if (betrag.getVoll() != 0 || betrag.getTeil() != 0) return "Fehler: Konto nicht geleert";
        kontoRepository.delete(konto);
        return "Konto entfernt";
    }

}
