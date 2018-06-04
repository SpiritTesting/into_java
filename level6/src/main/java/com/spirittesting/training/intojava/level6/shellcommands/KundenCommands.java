package com.spirittesting.training.intojava.level6.shellcommands;

import com.google.common.base.Joiner;
import com.spirittesting.training.intojava.level6.services.KundenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.transaction.Transactional;

@ShellComponent
public class KundenCommands {

    private final KundenService kundenService;

    @Autowired
    KundenCommands(KundenService kundenService) {
        this.kundenService = kundenService;
    }

    @ShellMethod("Lege einen neuen Kunden an")
    public String addKunde(@ShellOption String name) {
        return kundenService.addKunde(name).toString();
    }

    @ShellMethod("Entferne einen Kunden (Konten müssen leer sein")
    public String removeKunde(@ShellOption String kundennummer) {
        kundenService.removeKunde(kundennummer);
        return "Kunde wurde entfernt";
    }

    @Transactional
    @ShellMethod("Liste aller vorhandenen Kunden")
    public String listKunden() {
        return Joiner.on("\n").join(kundenService.getAllKunden());
    }

}
