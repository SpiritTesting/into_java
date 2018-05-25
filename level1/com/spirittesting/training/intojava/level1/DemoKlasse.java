package com.spirittesting.training.intojava.level1;
/*
 * Alle Klassen sollten eine Package-Deklaration besitzen. Die Package-Deklaration soll dem Ordnerpfad entsprechen, in dem die Klasse abgelegt ist. Root-Ebene ist hier der Ordner 'src'.
 * Darunter finden wir die Ordnerstruktur 'com', 'spirittesting', 'training', 'intojava' und schließlich 'level1'. Die Strukturierung in Packages dient unter anderem dazu, die Sichtbarkeit
 * von Klassen, Feldern und Methoden zu steuern.  
 */

/**
 * Der Klassenname soll grundsätzlich mit einem Großbuchstaben beginnen. Er soll dem Dateinamen entsprechen und in jeder Datei soll im Regelfall genau eine Klasse abgelegt sein (die ggf. innere Klassen enthalten kann). 
 * Der Klassenname muss innerhalb des Package eindeutig sein (ergibt sich daraus, dass der Dateiname ja eindeutig innerhalb des Ordners sein muss). 
 * Zur Einbindung dieser Klasse in andere Klassen wird der vollqualifizierte Klassenname benutzt. Dieser besteht aus package und Klasse, hier also: com.spirittesting.training.intojava.level1.DemoKlasse  
 */
class DemoKlasse {

	/**
	 * Felder sollen grundsätzlich mit einem Kleinbuchstaben beginnen. Es gibt hier
	 * keine Kurzschreibweise für Getter und Setter - beide müssen als vollständige
	 * Methoden ausgeschrieben werden.
	 */
	String einFeld;

	/**
	 * Eine Klasse darf beliebig viele Konstruktoren enthalten. Jeder Konstruktor
	 * muss jedoch eine einzigartige Liste von Parametern besitzen.
	 */
	DemoKlasse() {
		// Initialisierung der Klasse ist hier nicht notwendig
	}

	/** Trivialer Getter für einFeld */
	String getEinFeld() {
		return einFeld;
	}

	/** Trivialer Setter für einFeld */
	void setEinFeld(String einFeld) {
		this.einFeld = einFeld;
	}

	/**
	 * Der Einstiegspunkt in ein Java-Programm muss immer die folgende Signatur
	 * besitzen:
	 */
	public static void main(String[] args) {

		// wir definieren eine Variable
		String einFeld = "<nicht belegt>";
		// falls dem Programm Parameter mitgegeben wurden, überschreiben wir die
		// Variable mit dem ersten Parameter
		if (args.length > 0) {
			einFeld = args[0];
		}

		// Wir initialisieren eine neue Instanz der DemoKlasse.
		DemoKlasse demoKlasse = new DemoKlasse();

		// Über den Setter der Instanz wird das Feld mit der Variable belegt
		demoKlasse.setEinFeld(einFeld);

		// Auslesen des Feldes und Ausgabe in die Kommandozeile
		System.out.println("EinFeld: " + demoKlasse.getEinFeld());

	}
	
	/*
	 * #### Aufgaben ####
	 * 
	 * 1. Öffne ein Terminal und navigiere in das Wurzelverzeichnis der Anwendung. In diesem liegen die Unterordner 'src' und 'bin'. 'src' ist hier sichtbar und enthält den Quellcode. 'bin' wird von Eclipse (oder den meisten anderen IDEs) 
	 *    automatisch erstellt und beinhaltet den kompilierten Code. Wechsle in das Unterverzeichnis 'bin'. 
	 *    Um eine Klasse auszuführen, übergeben wir den vollqualifizierten Klassennamen und ggf. Parameter an den Befehl 'java'.
	 *    a) Welche Ausgabe erwartest Du, wenn Du 'java com.spirittesting.training.intojava.level1.DemoKlasse' eingibst und startest?
	 *    b) Welche Ausgabe erwartest Du, wenn Du 'java com.spirittesting.training.intojava.level1.DemoKlasse HalloWelt' eingibst und startest?
	 *    c) Welche Ausgabe erwartest Du, wenn Du 'java com.spirittesting.training.intojava.level1.DemoKlasse HalloWelt FooBar' eingibst und startest?
	 *    
	 * 2. Die Klasse ist zu umständlich. Wir möchten das Feld bereits beim Konstruktoraufruf setzen und so die Zeile 'demoKlasse.setEinFeld(einFeld);' einsparen. Baue die Klasse entsprechend um. Prüfe, dass die Ausgaben sich nicht geändert haben.
	 * 
	 * 3. Felder sollten nur über ihre Getter / Setter Methoden angesprochen werden können. Von wo kann das Feld 'einFeld' direkt manipuliert werden?
	 * 	  a) Nur innerhalb der Klasse DemoKlasse
	 *    b) Von allen Klassen innerhalb des Packages com.spirittesting.training.intojava.level1
	 *    c) Von allen Klassen
	 *    d) Von allen Klassen innerhalb des Packages com.spirittesting.training.intojava.level1 sowie von allen Klassen, die von DemoKlasse abgeleitet sind, unabhängig von ihrem Package.
	 *    e) Was ändert sich, wenn wir die Klasse als 'public' deklarieren, also: public class DemoKlasse {     ?
	 *    
	 * 4. Schränke die Sichtbarkeit von des Feldes einFeld so ein, dass es nur innerhalb der Klasse sichtbar ist.
	 * 
	 * 5. Der Rückfallwert '<nicht belegt>' sollte nicht von außen eingegeben werden. Die Klasse sollte selbst dafür sorgen, dass ihre Felder initialisiert sind. Baue sie entsprechend um.
	 * 
	 * Eine mögliche Lösung ist im Unterpackage 'solution' abgelegt.
	 */
	
}

