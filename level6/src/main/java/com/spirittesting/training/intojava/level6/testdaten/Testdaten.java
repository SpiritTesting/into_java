package com.spirittesting.training.intojava.level6.testdaten;

import com.spirittesting.training.intojava.level6.model.entities.Konto;
import com.spirittesting.training.intojava.level6.model.entities.Kunde;
import com.spirittesting.training.intojava.level6.repositories.KontoRepository;
import com.spirittesting.training.intojava.level6.repositories.KundenRepository;
import com.spirittesting.training.intojava.level6.repositories.ZahlungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class Testdaten {

    @Autowired
    KundenRepository kunden;
    @Autowired
    KontoRepository konten;
    @Autowired
    ZahlungRepository zahlungen;

    @Transactional
    @PostConstruct
    public void addTestdaten() {
        Kunde kunde = new Kunde();
        kunde.setName("Hannes");
        kunden.save(kunde);

        Konto konto = new Konto();
        konto.setKunde(kunde);
        konten.save(konto);
    }

}
