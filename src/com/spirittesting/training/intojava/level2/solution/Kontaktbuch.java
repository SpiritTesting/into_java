package com.spirittesting.training.intojava.level2.solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// schau Dir zunächst die Klassen Adressen und Kontakt an.

/**
 * Wir bauen aus Kontakten und Adressen ein einfaches Adressbuch auf. 
 */
class Kontaktbuch {

	/* eine Konstante für die Ausgabeformatierung */
	private static final String format = "\t%-20s - %s%n";

	// in diesem Feld werden die Kontakte vorgehalten
	private final List<Kontakt> kontakte = new ArrayList<>();
	// man kann einen Kontakt auswählen, um ihm Adressen hinzuzufügen oder zu löschen
	private Kontakt selectedKontakt = null;

	public List<Kontakt> getKontakte() {
		return kontakte;
	}

	// im nächsten Block erstellen wir die Methoden, die Kontakte verarbeiten oder ausgeben sollen
	
	private void listAllContacts() {
		for (Kontakt kontakt : getKontakte()) {
			System.console().format(format, "Kontakt:", kontakt);
		}
	}

	private void showSelectedContact() {
		System.console().format(format, "Gewählter Kontakt:",
				selectedKontakt == null ? "<kein Kontakt gewählt>" : selectedKontakt);
	}

	private void addKontakt(String name) {
		getKontakte().add(new Kontakt(name));
	}
	
	private Kontakt findKontakt(String name) {
		for (Kontakt kontakt : kontakte) {
			if (kontakt.getName().equalsIgnoreCase(name)) return kontakt;
		}
		return null;
	}
	
	private void removeKontakt(String name) {
		getKontakte().remove(findKontakt(name));		
	}
	
	private void selectKontakt(String name) {
		this.selectedKontakt = findKontakt(name);
	}
	
	private void addAdresse(String strasse, String hausnummer, String postleitzahl, String ort) {
		if (selectedKontakt == null) {
			System.console().format(format, "Fehler", "Kein Kontakt gewählt");
			return;
		}
		String id = "#" + selectedKontakt.getAdressen().size();
		selectedKontakt.addAdresse(new Adresse(id, strasse, hausnummer, postleitzahl, ort));
	}
	
	private void removeAdresse(String id) {
		if (selectedKontakt == null) {
			System.console().format(format, "Fehler", "Kein Kontakt gewählt");
			return;
		}
		Iterator<Adresse> iterator = selectedKontakt.getAdressen().iterator();
		while (iterator.hasNext()) {
			Adresse adresse = iterator.next();
			if (adresse.getId().equals(id)) {
				iterator.remove();
			}
		}
	}

	private void printHelp() {
		System.console().format(format, "Mögliche Befehle:", "");
		System.console().format(format, "help", " diese Anzeige");
		System.console().format(format, "list", " gibt das gesamte Adressbuch aus");
		System.console().format(format, "add <name>", "Legt neuen Kontakt an.");
		System.console().format(format, "rem <name>", "Löscht einen Kontakt.");
		System.console().format(format, "sel <name>", "Wählt einen Kontakt zur weiteren Bearbeitung");
		System.console().format(format, "who", "zeigt den zur Bearbeitung gewählten Kontakt");
		System.console().format(format, "newadd <strasse> <hausnummer> <postleitzahl> <ort>", "fügt dem gewählten Kontakt eine Adresse hinzu");		
		System.console().format(format, "remadd <id>", "entfernt die Adresse mit <id> vom gewählten Kontakt");
		System.console().format(format,  "quit", "Beendet das Programm");
	}

	// die Main-Methode läuft in einer Endlosschleife. In jeder Runde gibt der Anwender einen Befehl ein,
	// der dann vom Kontaktbuch verarbeitet wird.
	public static void main(String[] args) {
		Kontaktbuch kontaktbuch = new Kontaktbuch();

		while (true) {
			// Eingabe auslesen
			try {
				Command command = new Command(System.console().readLine("$ "));
				switch (command.command) {
				case "list":
					kontaktbuch.listAllContacts();
					break;
				case "who":
					kontaktbuch.showSelectedContact();
					break;
				case "add":
					if (command.parameters.length != 1) {
						System.console().format(format, "Fehler", "Bitte geben Sie den Namen des Kontakts ein");
						break;
					}					
					kontaktbuch.addKontakt(command.parameters[0]);
					break;
				case "rem":
					if (command.parameters.length != 1) {
						System.console().format(format, "Fehler", "Bitte geben Sie den Namen des Kontakts ein");
						break;
					}
					kontaktbuch.removeKontakt(command.parameters[0]);
					break;
				case "sel":
					if (command.parameters.length != 1) {
						System.console().format(format, "Fehler", "Bitte geben Sie den Namen des Kontakts ein");
						break;
					}
					kontaktbuch.selectKontakt(command.parameters[0]);
					break;
				case "newadd":
					if (command.parameters.length != 4) {
						System.console().format(format, "Fehler", "Bitte geben Sie Straße, Hausnummer, Postleitzahl und Ort getrennt durch je ein Komma ein");
						break;
					}					
					kontaktbuch.addAdresse(command.parameters[0], command.parameters[1], command.parameters[2], command.parameters[3]);
					break;
				case "remadd":
					if (command.parameters.length != 1) {
						System.console().format(format, "Fehler", "Bitte geben Sie die ID der zu löschenden Adresse ein. Denken Sie an das HashTag!");
						break;
					}
					kontaktbuch.removeAdresse(command.parameters[0]);
					break;
				case "quit":
					System.exit(0);
				case "help":
				default:
					kontaktbuch.printHelp();
				}
			} catch (IllegalArgumentException e) {
				System.console().format(format, "Eingabe nicht verstanden", "");
			}
		}

	}

	// Um die Befehlsverarbeitung etwas zu vereinfachen, lagern wir das Auslesen des Befehls in eine eigene Hilfsklasse aus. Die ist nicht-öffentlich und wird von außerhalb des Kontaktbuchs nicht gesehen.
	private static class Command {
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

}
