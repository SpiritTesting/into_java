package com.spirittesting.training.intojava.level4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Kontaktbuch {

    private final List<Kontakt> kontakte = new ArrayList<>();
    private Kontakt selectedKontakt = null;
    private static final String format = "\t%-20s - %s%n";


    public List<Kontakt> getKontakte() {
        return kontakte;
    }



    //void print() { System.console().format(format, getTitel(), getInhalt()); }

    List<Ausgabe> listAllContacts() {
        List<Ausgabe> ausgaben = new ArrayList<>();
        for (Kontakt kontakt : getKontakte()) {
            ausgaben.add(new Ausgabe("Kontakt:", kontakt.toString()));
        }
        return ausgaben;
    }

    Ausgabe showSelectedContact() {
        return new Ausgabe("Gew�hlter Kontakt", selectedKontakt == null ? "<kein Kontakt gew�hlt>" : selectedKontakt.toString());
    }

    Ausgabe addKontakt(String name) {
        Kontakt kontakt = new Kontakt(name);
        getKontakte().add(kontakt);
        return new Ausgabe("Kontakt hinzugef�gt", kontakt.toString());
    }

    Kontakt findKontakt(String name) {
        for (Kontakt kontakt : kontakte) {
            if (kontakt.getName().equalsIgnoreCase(name)) return kontakt;
        }
        return null;
    }

    Ausgabe removeKontakt(String name) {
        Kontakt kontakt = findKontakt(name);
        getKontakte().remove(kontakt);
        return new Ausgabe("Kontakt gel�scht", kontakt.toString());
    }

    Ausgabe selectKontakt(String name) {
        this.selectedKontakt = findKontakt(name);
        return new Ausgabe("Kontakt gew�hlt", this.selectedKontakt.toString());
    }

    Ausgabe addAdresse(String strasse, String hausnummer, String postleitzahl, String ort) {
        if (selectedKontakt == null) {
            return new Ausgabe("Fehler", "Kein Kontakt gew�hlt");
        }
        String id = "#" + selectedKontakt.getAdressen().size();
        Adresse adresse = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
        selectedKontakt.addAdresse(adresse);
        return new Ausgabe("Adresse hinzugef�gt", adresse.toString());
    }

    Ausgabe removeAdresse(String id) {
        if (selectedKontakt == null) {
            return new Ausgabe("Fehler", "Kein Kontakt gew�hlt");
        }
        Iterator<Adresse> iterator = selectedKontakt.getAdressen().iterator();
        while (iterator.hasNext()) {
            Adresse adresse = iterator.next();
            if (adresse.getId().equals(id)) {
                iterator.remove();
                return new Ausgabe("Adresse entfernt", adresse.toString());
            }
        }
        return new Ausgabe("Adresse nicht gefunden", id);
    }

    List<Ausgabe> printHelp() {
        List<Ausgabe> ausgaben = new ArrayList<>();
        ausgaben.add(new Ausgabe("M�gliche Befehle:", ""));
        ausgaben.add(new Ausgabe("help", " diese Anzeige"));
        ausgaben.add(new Ausgabe("list", " gibt das gesamte Adressbuch aus"));
        ausgaben.add(new Ausgabe("add <name>", "Legt neuen Kontakt an."));
        ausgaben.add(new Ausgabe("rem <name>", "L�scht einen Kontakt."));
        ausgaben.add(new Ausgabe("sel <name>", "W�hlt einen Kontakt zur weiteren Bearbeitung"));
        ausgaben.add(new Ausgabe("who", "zeigt den zur Bearbeitung gew�hlten Kontakt"));
        ausgaben.add(new Ausgabe("newadd <strasse> <hausnummer> <postleitzahl> <ort>", "f�gt dem gew�hlten Kontakt eine Adresse hinzu"));
        ausgaben.add(new Ausgabe("remadd <id>", "entfernt die Adresse mit <id> vom gew�hlten Kontakt"));
        ausgaben.add(new Ausgabe("quit", "Beendet das Programm"));
        return ausgaben;
    }

}
