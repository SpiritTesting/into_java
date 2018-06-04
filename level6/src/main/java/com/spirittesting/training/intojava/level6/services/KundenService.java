package com.spirittesting.training.intojava.level6.services;

import com.spirittesting.training.intojava.level6.model.Betrag;
import com.spirittesting.training.intojava.level6.model.entities.Konto;
import com.spirittesting.training.intojava.level6.model.entities.Kunde;
import com.spirittesting.training.intojava.level6.repositories.KundenRepository;
import com.spirittesting.training.intojava.level6.services.exceptions.KontoauflösungFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class KundenService {

    private final KundenRepository kundenRepository;
    private final VerrechnungsService verrechnungsService;

    @Autowired
    KundenService(KundenRepository kundenRepository, VerrechnungsService verrechnungsService) {
        this.kundenRepository = kundenRepository;
        this.verrechnungsService = verrechnungsService;
    }

    public Kunde addKunde(String name) {
        Kunde kunde = new Kunde();
        kunde.setName(name);
        return kundenRepository.save(kunde);
    }

    @Transactional
    public void removeKunde(String kundennummer) {
        /* Kunden dürfen nur gelöscht werden, wenn alle Konten leer sind */
        Kunde kunde = kundenRepository.findByKundennummer(kundennummer);
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

}
