package a;//package com.spirittesting.training.intojava.level3;

import java.util.*;

class Kontaktbuch {

    private static final String format = "\t%-20s - %s%n";

    private final List<Kontakt> kontakte = new ArrayList<>();
    private Kontakt selectedKontakt = null;
    public List<Kontakt> getKontakte() {
        return kontakte;
    }


    public List<Ausgabe> listAllContacts() {
        List<Ausgabe> ausgaben = new ArrayList<>();
        for (Kontakt kontakt : getKontakte()) {
            ausgaben.add(new Ausgabe("a.Kontakt:", kontakt.toString()));
        }
        return ausgaben;
    }

    Ausgabe showSelectedContact() {
        return new Ausgabe("Gew�hlter a.Kontakt", selectedKontakt == null ? "<kein a.Kontakt gew�hlt>" : selectedKontakt.toString());
    }

    Ausgabe addKontakt(String name) {
        Kontakt kontakt = new Kontakt(name);
        getKontakte().add(kontakt);
        return new Ausgabe("a.Kontakt hinzugef�gt", kontakt.toString());
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
        return new Ausgabe("a.Kontakt gel�scht", kontakt.toString());
    }

    Ausgabe selectKontakt(String name) {
        this.selectedKontakt = findKontakt(name);
        return new Ausgabe("a.Kontakt gew�hlt", this.selectedKontakt.toString());
    }

    Ausgabe addAdresse(String strasse, String hausnummer, String postleitzahl, String ort) {
        if (selectedKontakt == null) {
            return new Ausgabe("Fehler", "Kein a.Kontakt gew�hlt");
        }
        String id = "#" + selectedKontakt.getAdressen().size();
        Adresse adresse = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
        selectedKontakt.addAdresse(adresse);
        return new Ausgabe("a.Adresse hinzugef�gt", adresse.toString());
    }

    Ausgabe removeAdresse(String id) {
        if (selectedKontakt == null) {
            return new Ausgabe("Fehler", "Kein a.Kontakt gew�hlt");
        }
        Iterator<Adresse> iterator = selectedKontakt.getAdressen().iterator();
        while (iterator.hasNext()) {
            Adresse adresse = iterator.next();
            if (adresse.getId().equals(id)) {
                iterator.remove();
                return new Ausgabe("a.Adresse entfernt", adresse.toString());
            }
        }
        return new Ausgabe("a.Adresse nicht gefunden", id);
    }

    List<Ausgabe> printHelp() {
        List<Ausgabe> ausgaben = new ArrayList<>();
        ausgaben.add(new Ausgabe("M�gliche Befehle:", ""));
        ausgaben.add(new Ausgabe("help", " diese Anzeige"));
        ausgaben.add(new Ausgabe("list", " gibt das gesamte Adressbuch aus"));
        ausgaben.add(new Ausgabe("add <name>", "Legt neuen a.Kontakt an."));
        ausgaben.add(new Ausgabe("rem <name>", "L�scht einen a.Kontakt."));
        ausgaben.add(new Ausgabe("sel <name>", "W�hlt einen a.Kontakt zur weiteren Bearbeitung"));
        ausgaben.add(new Ausgabe("who", "zeigt den zur Bearbeitung gew�hlten a.Kontakt"));
        ausgaben.add(new Ausgabe("newadd <strasse> <hausnummer> <postleitzahl> <ort>", "f�gt dem gew�hlten a.Kontakt eine a.Adresse hinzu"));
        ausgaben.add(new Ausgabe("remadd <id>", "entfernt die a.Adresse mit <id> vom gew�hlten a.Kontakt"));
        ausgaben.add(new Ausgabe("quit", "Beendet das Programm"));
        return ausgaben;
    }

    public static void main(String[] args) {
        Kontaktbuch kontaktbuch = new Kontaktbuch();
        //System.out.println("Bitte Befehl eingeben: ");
        //Scanner myVar = new Scanner(System.in);
        //Command command = myVar.nextLine();
        while (true) {
            List<Ausgabe> ausgaben = new ArrayList<>();
            try {
                Command command = new Command(System.console().readLine("$ "));

                switch (command.command) {
                    case "list":
                        ausgaben.addAll(kontaktbuch.listAllContacts());
                        break;
                    case "who":
                        ausgaben.add(kontaktbuch.showSelectedContact());
                        break;
                    case "add":
                        if (command.parameters.length != 1) {
                            ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie den Namen des Kontakts ein"));
                            break;
                        }
                        ausgaben.add(kontaktbuch.addKontakt(command.parameters[0]));
                        break;
                    case "rem":
                        if (command.parameters.length != 1) {
                            ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie den Namen des Kontakts ein"));
                            break;
                        }
                        ausgaben.add(kontaktbuch.removeKontakt(command.parameters[0]));
                        break;
                    case "sel":
                        if (command.parameters.length != 1) {
                            ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie den Namen des Kontakts ein"));
                            break;
                        }
                        ausgaben.add(kontaktbuch.selectKontakt(command.parameters[0]));
                        break;
                    case "newadd":
                        if (command.parameters.length != 4) {
                            ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie Stra�e, Hausnummer, Postleitzahl und Ort getrennt durch je ein Komma ein"));
                            break;
                        }
                        ausgaben.add(kontaktbuch.addAdresse(command.parameters[0], command.parameters[1], command.parameters[2], command.parameters[3]));
                        break;
                    case "remadd":
                        if (command.parameters.length != 1) {
                            ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie die ID der zu l�schenden a.Adresse ein. Denken Sie an das HashTag!"));
                            break;
                        }
                        ausgaben.add(kontaktbuch.removeAdresse(command.parameters[0]));
                        break;
                    case "quit":
                        System.exit(0);
                    case "help":
                    default:
                        ausgaben.addAll(kontaktbuch.printHelp());
                }
            } catch (IllegalArgumentException e) {
                ausgaben.add(new Ausgabe("Eingabe nicht verstanden", ""));
            }
            for (Ausgabe ausgabe : ausgaben) {
                ausgabe.print();
            }
        }

    }

    @Override
    public String toString() {
        return " a.Kontakte: " + kontakte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kontaktbuch that = (Kontaktbuch) o;
        return Objects.equals(kontakte, that.kontakte) &&
                Objects.equals(selectedKontakt, that.selectedKontakt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(kontakte, selectedKontakt);
    }

    static class Command {
        final String command;
        final String[] parameters;

        Command(String input) {
            if (input.isEmpty()) throw new IllegalArgumentException("Eingabe erwartet");

            int indexOf = input.indexOf(" ");
            if (indexOf == -1) {
                command = input;
                parameters = new String[0];
            } else {
                command = input.substring(0, indexOf).trim();
                String parameterString = input.substring(indexOf);
                parameters = parameterString.split(",");
            }
        }
    }

    static class Ausgabe {
        private final String titel;
        private final String inhalt;

        Ausgabe(String titel, String inhalt) {
            this.titel = titel;
            this.inhalt = inhalt;
        }

        public String getTitel() {
            return titel == null ? "" : titel;
        }

        public String getInhalt() {
            return inhalt == null ? "" : inhalt;
        }

        void print() {
            System.console().format(format, getTitel(), getInhalt());
        }


    }



}

