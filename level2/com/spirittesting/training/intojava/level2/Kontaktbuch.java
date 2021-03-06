package com.spirittesting.training.intojava.level2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// schau Dir zun�chst die Klassen Adressen und Kontakt an.

/**
 * Wir bauen aus Kontakten und Adressen ein einfaches Adressbuch auf. 
 */
class Kontaktbuch {

	/* eine Konstante f�r die Ausgabeformatierung */
	private static final String format = "\t%-20s - %s%n";

	// in diesem Feld werden die Kontakte vorgehalten
	private final List<Kontakt> kontakte = new ArrayList<>();
	// man kann einen Kontakt ausw�hlen, um ihm Adressen hinzuzuf�gen oder zu l�schen
	private Kontakt selectedKontakt = null;

	public List<Kontakt> getKontakte() {
		return kontakte;
	}

	// im n�chsten Block erstellen wir die Methoden, die Kontakte verarbeiten oder ausgeben sollen
	
	private void listAllContacts() {
		for (Kontakt kontakt : getKontakte()) {
			System.console().format(format, "Kontakt:", kontakt);
		}
	}

	private void showSelectedContact() {
		System.console().format(format, "Gew�hlter Kontakt:",
				selectedKontakt == null ? "<kein Kontakt gew�hlt>" : selectedKontakt);
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
	
	private void addAdresse(String strasse, String postleitzahl) {
		String id = "#" + selectedKontakt.getAdressen().size();
		selectedKontakt.addAdresse(new Adresse(id, strasse, postleitzahl));
	}
	
	private void removeAdresse(String id) {
		Iterator<Adresse> iterator = selectedKontakt.getAdressen().iterator();
		while (iterator.hasNext()) {
			Adresse adresse = iterator.next();
			if (adresse.getId().equals(id)) {
				iterator.remove();
			}
		}
	}

	private void printHelp() {
		System.console().format(format, "M�gliche Befehle:", "");
		System.console().format(format, "help", " diese Anzeige");
		System.console().format(format, "list", " gibt das gesamte Adressbuch aus");
		System.console().format(format, "add <name>", "Legt neuen Kontakt an.");
		System.console().format(format, "rem <name>", "L�scht einen Kontakt.");
		System.console().format(format, "sel <name>", "W�hlt einen Kontakt zur weiteren Bearbeitung");
		System.console().format(format, "who", "zeigt den zur Bearbeitung gew�hlten Kontakt");
		System.console().format(format, "newadd <strasse> <postleitzahl>", "f�gt dem gew�hlten Kontakt eine Adresse hinzu");		
		System.console().format(format, "remadd <id>", "entfernt die Adresse mit <id> vom gew�hlten Kontakt");
		System.console().format(format,  "quit", "Beendet das Programm");
	}

	// die Main-Methode l�uft in einer Endlosschleife. In jeder Runde gibt der Anwender einen Befehl ein,
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
					kontaktbuch.addKontakt(command.parameters[0]);
					break;
				case "rem":
					kontaktbuch.removeKontakt(command.parameters[0]);
					break;
				case "sel":
					kontaktbuch.selectKontakt(command.parameters[0]);
					break;
				case "newadd":
					kontaktbuch.addAdresse(command.parameters[0], command.parameters[1]);
					break;
				case "remadd":
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

	// Um die Befehlsverarbeitung etwas zu vereinfachen, lagern wir das Auslesen des Befehls in eine eigene Hilfsklasse aus. Die ist nicht-�ffentlich und wird von au�erhalb des Kontaktbuchs nicht gesehen.
	private static class Command {
		final String command;
		final String[] parameters;

		Command(String input) {
			if (input.isEmpty())
				throw new IllegalArgumentException("Eingabe erwartet");
			String[] inputWords = input.split("\\s+");
			// Das erste Wort der Eingabe ist der Befehl, alle weiteren W�rter sind
			// Parameter.
			command = inputWords[0];
			parameters = new String[inputWords.length - 1];
			for (int index = 1; index < inputWords.length; index++) {
				parameters[index - 1] = inputWords[index];
			}
		}
		
	}

}
