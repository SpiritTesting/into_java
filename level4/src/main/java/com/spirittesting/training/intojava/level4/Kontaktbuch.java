package com.spirittesting.training.intojava.level4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Kontaktbuch {

	private static final String format = "\t%-20s - %s%n";

	private final List<Kontakt> kontakte = new ArrayList<>();
	private Kontakt selectedKontakt = null;

	public List<Kontakt> getKontakte() {
		return kontakte;
	}

	private List<Ausgabe> listAllContacts() {
		List<Ausgabe> ausgaben = new ArrayList<>();
		for (Kontakt kontakt : getKontakte()) {
			ausgaben.add(new Ausgabe("Kontakt:", kontakt.toString()));
		}
		return ausgaben;
	}

	Ausgabe showSelectedContact() {
		return new Ausgabe("Gewählter Kontakt", selectedKontakt == null ? "<kein Kontakt gewählt>" : selectedKontakt.toString());
	}

	Ausgabe addKontakt(String name) {
		Kontakt kontakt = new Kontakt(name);
		getKontakte().add(kontakt);
		return new Ausgabe("Kontakt hinzugefügt", kontakt.toString());
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
		return new Ausgabe("Kontakt gelöscht", kontakt.toString());
	}
	
	Ausgabe selectKontakt(String name) {
		this.selectedKontakt = findKontakt(name);
		return new Ausgabe("Kontakt gewählt", this.selectedKontakt.toString());
	}
	
	Ausgabe addAdresse(String strasse, String hausnummer, String postleitzahl, String ort) {
		if (selectedKontakt == null) {
			return new Ausgabe("Fehler", "Kein Kontakt gewählt");
		}
		String id = "#" + selectedKontakt.getAdressen().size();
		Adresse adresse = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
		selectedKontakt.addAdresse(adresse);
		return new Ausgabe("Adresse hinzugefügt", adresse.toString());
	}
	
	Ausgabe removeAdresse(String id) {
		if (selectedKontakt == null) {
			return new Ausgabe("Fehler", "Kein Kontakt gewählt");
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
		ausgaben.add(new Ausgabe("Mögliche Befehle:", ""));
		ausgaben.add(new Ausgabe("help", " diese Anzeige"));
		ausgaben.add(new Ausgabe("list", " gibt das gesamte Adressbuch aus"));
		ausgaben.add(new Ausgabe("add <name>", "Legt neuen Kontakt an."));
		ausgaben.add(new Ausgabe("rem <name>", "Löscht einen Kontakt."));
		ausgaben.add(new Ausgabe("sel <name>", "Wählt einen Kontakt zur weiteren Bearbeitung"));
		ausgaben.add(new Ausgabe("who", "zeigt den zur Bearbeitung gewählten Kontakt"));
		ausgaben.add(new Ausgabe("newadd <strasse> <hausnummer> <postleitzahl> <ort>", "fügt dem gewählten Kontakt eine Adresse hinzu"));		
		ausgaben.add(new Ausgabe("remadd <id>", "entfernt die Adresse mit <id> vom gewählten Kontakt"));
		ausgaben.add(new Ausgabe("quit", "Beendet das Programm"));
		return ausgaben;
	}

	public static void main(String[] args) {
		Kontaktbuch kontaktbuch = new Kontaktbuch();

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
						ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie Straße, Hausnummer, Postleitzahl und Ort getrennt durch je ein Komma ein"));
						break;
					}					
					ausgaben.add(kontaktbuch.addAdresse(command.parameters[0], command.parameters[1], command.parameters[2], command.parameters[3]));
					break;
				case "remadd":
					if (command.parameters.length != 1) {
						ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie die ID der zu löschenden Adresse ein. Denken Sie an das HashTag!"));
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
