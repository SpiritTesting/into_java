package com.spirittesting.training.intojava.level1.solution;
/*
 * Musterl�sung f�r Level 1
 */

/**
 * Der Klassenname soll grunds�tzlich mit einem Gro�buchstaben beginnen. Er soll dem Dateinamen entsprechen und in jeder Datei soll im Regelfall genau eine Klasse abgelegt sein (die ggf. innere Klassen enthalten kann). 
 * Der Klassenname muss innerhalb des Package eindeutig sein (ergibt sich daraus, dass der Dateiname ja eindeutig innerhalb des Ordners sein muss). 
 * Zur Einbindung dieser Klasse in andere Klassen wird der vollqualifizierte Klassenname benutzt. Dieser besteht aus package und Klasse, hier also: com.spirittesting.training.intojava.level1.DemoKlasse  
 */
class DemoKlasse {

	/**
	 * Felder sollen grunds�tzlich mit einem Kleinbuchstaben beginnen. Es gibt hier
	 * keine Kurzschreibweise f�r Getter und Setter - beide m�ssen als vollst�ndige
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

	/** Trivialer Getter f�r einFeld */
	String getEinFeld() {
		return einFeld;
	}

	/** Trivialer Setter f�r einFeld */
	void setEinFeld(String einFeld) {
		this.einFeld = einFeld;
	}

	/**
	 * Der Einstiegspunkt in ein Java-Programm muss immer die folgende Signatur
	 * besitzen:
	 */
	public static void main(String[] args) {

		// Der Tern�roperator: if condition ? then : else
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
	 * 2. Die Klasse ist zu umst�ndlich. Wir m�chten das Feld bereits beim Konstruktoraufruf setzen und so die Zeile 'demoKlasse.setEinFeld(einFeld);' einsparen. Baue die Klasse entsprechend um. Pr�fe, dass die Ausgaben sich nicht ge�ndert haben.
	 * 
	 * 	  --> Der Konstruktor lautet nun: DemoKlasse(String einFeld) { setEinFeld(einFeld); }
	 *    --> Die MainMethode lautet nun: 
	       public static void main(String[] args) {

				// wir definieren eine Variable
				String einFeld = "<nicht belegt>";				
				// falls dem Programm Parameter mitgegeben wurden, �berschreiben wir die
				// Variable mit dem ersten Parameter
				if (args.length > 0) {
					einFeld = args[0];
				}
		
				// Wir initialisieren eine neue Instanz der DemoKlasse.
				DemoKlasse demoKlasse = new DemoKlasse(einFeld);
		
				// Auslesen des Feldes und Ausgabe in die Kommandozeile
				System.out.println("EinFeld: " + demoKlasse.getEinFeld());
		
			}
	  
	 * 3. Felder sollten nur �ber ihre Getter / Setter Methoden angesprochen werden k�nnen. Von wo kann das Feld 'einFeld' direkt manipuliert werden?
	 * 	  a) Nur innerhalb der Klasse DemoKlasse
	 *    b) Von allen Klassen innerhalb des Packages com.spirittesting.training.intojava.level1
	 *    c) Von allen Klassen
	 *    d) Von allen Klassen innerhalb des Packages com.spirittesting.training.intojava.level1 sowie von allen Klassen, die von DemoKlasse abgeleitet sind, unabh�ngig von ihrem Package.
	 *    
	 *    --> b
	 *    
	 *    e) --> nichts. Um erbenden Klassen den Zugriff zu erlauben, muss das Feld die Sichtbarkeit 'protected' erhalten.
	 *    
	 * 4. Schr�nke die Sichtbarkeit von des Feldes einFeld so ein, dass es nur innerhalb der Klasse sichtbar ist.
	 * 
	 * 	  --> private String einFeld; 
	 * 
	 * 5. Der R�ckfallwert '<nicht belegt>' sollte nicht von au�en eingegeben werden. Die Klasse sollte selbst daf�r sorgen, dass ihre Felder initialisiert sind. Baue sie entsprechend um.
	 * 
	 *    --> private String einFeld = "<nicht belegt>"; // das Feld wird vorinitialisiert
	 *    --> DemoKlasse(String einFeld) { if (einFeld != null) setEinFeld(einFeld); } // Konstruktor sichern, um nicht doch wieder ein 'null' �bergeben zu bekommen.
	 * 
	 * Bonusfrage: Wann und wie kann das Feld einFeld jetzt doch noch 'null' werden?
	 */
}

