package com.spirittesting.training.intojava.level6.shellcommands;

import com.google.common.base.Joiner;
import com.spirittesting.training.intojava.level6.services.SchalterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.transaction.Transactional;

@ShellComponent
public class SchalterCommands {

    private final SchalterService schalterService;

    @Autowired
    SchalterCommands(SchalterService schalterService) {
        this.schalterService = schalterService;
    }

    @ShellMethod("Lege einen neuen Kunden an")
    public String addKunde(@ShellOption String name) {
        return schalterService.addKunde(name).toString();
    }

    @ShellMethod("Entferne einen Kunden (Konten müssen leer sein")
    public String removeKunde(@ShellOption String kundennummer) {
        schalterService.removeKunde(kundennummer);
        return "Kunde wurde entfernt";
    }

    @Transactional
    @ShellMethod("Liste aller vorhandenen Kunden")
    public String listKunden() {
        return Joiner.on("\n").join(schalterService.getAllKunden());
    }

    @ShellMethod("Selektiert einen Kunden")
    public String selectKunde(@ShellOption String kundennummer) {
        return schalterService.selectKunde(kundennummer);
    }

    @ShellMethod("Deselektiert einen Kunden")
    public String deselectKunde() {
        return schalterService.deselectKunde();
    }

    @Transactional
    @ShellMethod("Führt alle Konten des Kunden auf")
    public String listKonten() {
        return Joiner.on("\n").join(schalterService.listKonten());
    }

    @Transactional
    @ShellMethod("Legt ein neues Konto für den gewählten Kunden an")
    public String addKonto() {
        return schalterService.addKonto();
    }

    @Transactional
    @ShellMethod("Entfernt ein Konto")
    public String removeKonto(@ShellOption String kontonummer) {
        return schalterService.removeKonto(Integer.parseInt(kontonummer));
    }
}
