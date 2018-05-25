package com.spirittesting.training.intojava.level1.solution;
/*
 * Musterlösung für Level 1
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
	private String einFeld = "<nicht belegt>";

	/**
	 * Eine Klasse darf beliebig viele Konstruktoren enthalten. Jeder Konstruktor
	 * muss jedoch eine einzigartige Liste von Parametern besitzen.
	 */
	DemoKlasse(String einFeld) {
		if (einFeld != null) setEinFeld(einFeld);
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

		// Der Ternäroperator: if condition ? then : else
		DemoKlasse demoKlasse = new DemoKlasse(args.length > 0 ? args[0] : null);

		System.out.println("EinFeld: " + demoKlasse.getEinFeld());

	}
	
	/*
	 * #### Antworten ####
	 * 
	 * 1. a) Welche Ausgabe erwartest Du, wenn Du 'java com.spirittesting.training.intojava.level1.DemoKlasse' eingibst und startest?						--> EinFeld: <nicht belegt>
	 *    b) Welche Ausgabe erwartest Du, wenn Du 'java com.spirittesting.training.intojava.level1.DemoKlasse HalloWelt' eingibst und startest?				--> EinFeld: HalloWelt
	 *    c) Welche Ausgabe erwartest Du, wenn Du 'java com.spirittesting.training.intojava.level1.DemoKlasse HalloWelt FooBar' eingibst und startest?		--> EinFeld: HalloWelt
	 *    
	 * 2. Die Klasse ist zu umständlich. Wir möchten das Feld bereits beim Konstruktoraufruf setzen und so die Zeile 'demoKlasse.setEinFeld(einFeld);' einsparen. Baue die Klasse entsprechend um. Prüfe, dass die Ausgaben sich nicht geändert haben.
	 * 
	 * 	  --> Der Konstruktor lautet nun: DemoKlasse(String einFeld) { setEinFeld(einFeld); }
	 *    --> Die MainMethode lautet nun: 
	       public static void main(String[] args) {

				// wir definieren eine Variable
				String einFeld = "<nicht belegt>";				
				// falls dem Programm Parameter mitgegeben wurden, überschreiben wir die
				// Variable mit dem ersten Parameter
				if (args.length > 0) {
					einFeld = args[0];
				}
		
				// Wir initialisieren eine neue Instanz der DemoKlasse.
				DemoKlasse demoKlasse = new DemoKlasse(einFeld);
		
				// Auslesen des Feldes und Ausgabe in die Kommandozeile
				System.out.println("EinFeld: " + demoKlasse.getEinFeld());
		
			}
	  
	 * 3. Felder sollten nur über ihre Getter / Setter Methoden angesprochen werden können. Von wo kann das Feld 'einFeld' direkt manipuliert werden?
	 * 	  a) Nur innerhalb der Klasse DemoKlasse
	 *    b) Von allen Klassen innerhalb des Packages com.spirittesting.training.intojava.level1
	 *    c) Von allen Klassen
	 *    d) Von allen Klassen innerhalb des Packages com.spirittesting.training.intojava.level1 sowie von allen Klassen, die von DemoKlasse abgeleitet sind, unabhängig von ihrem Package.
	 *    
	 *    --> b
	 *    
	 *    e) --> nichts. Um erbenden Klassen den Zugriff zu erlauben, muss das Feld die Sichtbarkeit 'protected' erhalten.
	 *    
	 * 4. Schränke die Sichtbarkeit von des Feldes einFeld so ein, dass es nur innerhalb der Klasse sichtbar ist.
	 * 
	 * 	  --> private String einFeld; 
	 * 
	 * 5. Der Rückfallwert '<nicht belegt>' sollte nicht von außen eingegeben werden. Die Klasse sollte selbst dafür sorgen, dass ihre Felder initialisiert sind. Baue sie entsprechend um.
	 * 
	 *    --> private String einFeld = "<nicht belegt>"; // das Feld wird vorinitialisiert
	 *    --> DemoKlasse(String einFeld) { if (einFeld != null) setEinFeld(einFeld); } // Konstruktor sichern, um nicht doch wieder ein 'null' übergeben zu bekommen.
	 * 
	 * Bonusfrage: Wann und wie kann das Feld einFeld jetzt doch noch 'null' werden?
	 */
}

