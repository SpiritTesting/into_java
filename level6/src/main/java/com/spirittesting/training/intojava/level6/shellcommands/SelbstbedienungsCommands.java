package com.spirittesting.training.intojava.level6.shellcommands;

import com.spirittesting.training.intojava.level6.services.SelbstbedienungsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.transaction.Transactional;

@ShellComponent
public class SelbstbedienungsCommands {

    private SelbstbedienungsService selbstbedienungsService;

    @Autowired
    SelbstbedienungsCommands(SelbstbedienungsService selbstbedienungsService) {
        this.selbstbedienungsService = selbstbedienungsService;
    }

    @ShellMethod("Meldet einen Kunden für Selbstbedienungsservices an")
    public String anmelden(@ShellOption String kundennummer) {
        return selbstbedienungsService.anmelden(kundennummer);
    }

    @ShellMethod("Meldet einen Kunden für Selbstbedienungsservices ab")
    public String abmelden() {
        return selbstbedienungsService.abmelden();
    }

    @ShellMethod("Meldet ein Konto für Selbstbedienungsservices an")
    public String konto(@ShellOption String kontonummer) {
        return selbstbedienungsService.selectKonto(kontonummer);
    }

    @Transactional
    @ShellMethod("Einzahlung auf das Konto vornehmen")
    public String einzahlen(@ShellOption String betrag) {
        return selbstbedienungsService.einzahlen(betrag);
    }

    @Transactional
    @ShellMethod("Auszahlung vom Konto vornehmen")
    public String auszahlen(@ShellOption String betrag) {
        return selbstbedienungsService.auszahlen(betrag);
    }

    @Transactional
    @ShellMethod("Überweisung vom Konto vornehmen")
    public String überweisen(@ShellOption String zielKonto, @ShellOption String betrag) {
        return selbstbedienungsService.überweisen(zielKonto, betrag);
    }

    @Transactional
    @ShellMethod("Kontostand einsehen")
    public String kontostand() {
        return selbstbedienungsService.getKontostand();
    }

}
