package com.spirittesting.training.intojava.level6.services;

import com.spirittesting.training.intojava.level6.model.Betrag;
import com.spirittesting.training.intojava.level6.model.entities.Zahlung;
import com.spirittesting.training.intojava.level6.services.exceptions.NegativBetragNichtErlaubtException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerrechnungsService {

    public Betrag verrechne(List<Zahlung> abgehend, List<Zahlung> eingehend) {
        Betrag summe = null;
        for (Zahlung zahlung : eingehend) {
            if (summe == null) {
                summe = zahlung.getBetrag();
            } else {
                summe = summe.addiere(zahlung.getBetrag());
            }
        }
        for (Zahlung zahlung : abgehend) {
            if (summe == null) {
                throw new NegativBetragNichtErlaubtException();
            }
            summe = summe.subtrahiere(zahlung.getBetrag());
        }
        return summe == null ? new Betrag("EUR0.00") : summe;
    }

}
