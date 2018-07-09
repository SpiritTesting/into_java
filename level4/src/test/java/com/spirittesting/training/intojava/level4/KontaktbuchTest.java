package com.spirittesting.training.intojava.level4;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class KontaktbuchTest {

	/*
	 * Um die Klasse Kontaktbuch testf�hig zu machen, mussten wir sie etwas umbauen. Die einzelnen Methoden sind jetzt nicht
	 * mehr selbst f�r die Ausgaben zust�ndig. Stattdessen geben sie jeweils ein (oder mehrere) Instanzen einer neu erstellten
	 * Klasse zur�ck, die dann in der Main-Methode in Ausgaben umgewandelt werden k�nnen.
	 * 
	 * Die Testf�lle hier sind etwas komplizierter, da wir jeweils einen komplexen Zustand im Konaktbuch setzen und pr�fen m�ssen.
	 * Das hei�t auch, dass wir die jeweiligen Testkandidaten - also die Methoden im Kontaktbuch - mit mehreren Testf�llen
	 * je Kandidat abdecken sollten. 
	 * 
	 * Innerhalb dieser Testklasse wird jede Methode als Test erkannt und ausgef�hrt, die die folgenden Kriterien erf�llt:
	 * - Annotation @Test
	 * - Sichtbarkeit public
	 * - R�ckgabewert void
	 * - keine Argumente
	 * 
	 * Der Name der Methode ist dabei gleichg�ltig, dient uns aber zur Differenzierung, was wir eigentlich pr�fen wollen.
	 */
	
	private static final String name = "Hannes Kabeltod";
	private static final String name2 ="Donald Duck";
	private static final String id = "#42";
	private static final String strasse = "Mannheimer Stra�e";
	private static final String hausnummer = "123a";
	private static final String postleitzahl = "90402";
	private static final String ort = "Strunzen�d (Oberbayern)";
	private static final String toString = "a.Adresse [id=#42, strasse=Mannheimer Stra�e, hausnummer=123a, postleitzahl=90402, ort=Strunzen�d (Oberbayern)]";
	Kontakt selectedKontakt = null;
	Kontakt selectedKontakt2 = null;
	/**
	 * Testfall:
	 * Ausgehend von einem leeren Kontaktbuch f�gen wir einen Kontakt hinzu.
	 * Wir erwarten, dass danach genau ein Kontakt vorliegt.
	 * Wir erwarten, dass dieser Kontakt den �bergebenen Namen besitzt. 
	 */
	@Test
	public void testAddKontakt() {
		Kontaktbuch buch = new Kontaktbuch();
		assertTrue(buch.getKontakte().isEmpty());
		
		buch.addKontakt(name);
		assertEquals(1, buch.getKontakte().size());
		assertEquals(name, buch.getKontakte().get(0).getName());
	}
	
	/**
	 * Testfall:
	 * Ausgehend von einem gef�llten Kontaktbuch f�gen wir einen Kontakt hinzu.
	 * Wir erwarten, dass die Anzahl der Kontakte sich um 1 erh�ht. 
	 */
	@Test
	public void testAddKontakt_prefilled() {
		Kontaktbuch buch = new Kontaktbuch();
		buch.addKontakt("Willi W�hlkelle");
		buch.addKontakt("Heinrich Gewinnsucht");
		int preSize = buch.getKontakte().size();
		
		buch.addKontakt(name);
		assertEquals(preSize + 1, buch.getKontakte().size());
	}
	
	@Test
	public void testShowSelectedContact() {
		Kontaktbuch buch = new Kontaktbuch();
		Ausgabe ausgabe = buch.showSelectedContact();
		assertEquals("Gew�hlter Kontakt", ausgabe.getTitel());
		assertEquals("<kein Kontakt gew�hlt>", ausgabe.getInhalt());
	}
	
	@Test
	public void testShowSelectedContact_keinGewaehlterKontakt() {
		Kontaktbuch buch = new Kontaktbuch();
		Ausgabe ausgabe = buch.showSelectedContact();
		assertEquals("Gew�hlter Kontakt", ausgabe.getTitel());
		assertEquals("<kein Kontakt gew�hlt>", ausgabe.getInhalt());
	}

	@Test
	public void testFindKontakt() {
		Kontaktbuch buch = new Kontaktbuch();
		Kontakt k = new Kontakt(name);
		buch.addKontakt(name);
		buch.addKontakt("Willi W�hlkelle");
		buch.addKontakt("Heinrich Gewinnsucht");
		assertEquals("Willi W�hlkelle", buch.findKontakt("Willi W�hlkelle").getName());
		assertEquals(null, buch.findKontakt("Donald"));
	}

	@Test
	public void testRemoveKontakt() {
		Kontaktbuch buch = new Kontaktbuch();
		Kontakt k = new Kontakt(name);
		buch.addKontakt(name);
		buch.addKontakt("Willi W�hlkelle");
		buch.addKontakt("Heinrich Gewinnsucht");
		int preSize = buch.getKontakte().size();
		buch.removeKontakt(name);
		buch.removeKontakt("Heinrich Gewinnsucht");
		assertEquals(preSize - 2, buch.getKontakte().size());
		assertFalse(buch.getKontakte().contains(k));
	}

	@Test
	public void testSelectKontakt() {
		Kontaktbuch buch = new Kontaktbuch();
		Kontakt k = new Kontakt(name);
		buch.addKontakt(name);
		buch.addKontakt("Willi W�hlkelle");
		assertEquals(k.toString(),buch.selectKontakt(name).getInhalt());
		System.out.println(buch.selectKontakt(name).getInhalt());
		System.out.println(k.toString());
	}

	@Test
	public void testAddAdresse() {
		//arrange
		Kontaktbuch buch = new Kontaktbuch(); //ok
		buch.addKontakt(name);
		// Kontakt im Kontaktbuch-Objekt auswaehlen
		buch.selectKontakt(name);
		//Kontakt keinKontakt= buch.selectKontakt(null);
		Ausgabe a = new Ausgabe("Fehler", "Kein a.Kontakt gew�hlt");
		// Vergleichs-Adresse
		// (String id, String strasse, String hausnummer, String postleitzahl, String ort)
		Adresse vergleichsAdresse = new Adresse("#0", strasse, hausnummer, postleitzahl, ort);
		//act
		// String strasse, String hausnummer, String postleitzahl, String ort
		buch.addAdresse(strasse, hausnummer, postleitzahl, ort); // welche id bekommt die Adresse?
		//assert
		// assertEquals(vergleichsAdresse,buch.getKontakte().get(0).getAdressen()); // nur ein Kontakt
		assertTrue(buch.getKontakte().get(0).getAdressen().contains(vergleichsAdresse));

		//assertTrue(buch.getKontakte().get(1). getAdressen().contains(vergleichsAdresse));
	}

	@Test
	public void testRemoveAdresse() {
		Kontaktbuch buch = new Kontaktbuch(); //ok
		buch.addKontakt(name);
		buch.selectKontakt(name);
		Ausgabe a = new Ausgabe("Fehler", "Kein a.Kontakt gew�hlt");
		Adresse vergleichsAdresse = new Adresse("#0", strasse, hausnummer, postleitzahl, ort);
		buch.addAdresse(strasse, hausnummer, postleitzahl, ort);
		assertTrue(buch.getKontakte().get(0).getAdressen().contains(vergleichsAdresse));
		buch.removeAdresse("#0");
		assertFalse(buch.getKontakte().get(0).getAdressen().contains(vergleichsAdresse));
		assertEquals("Adresse nicht gefunden",buch.removeAdresse(null).getTitel());


		buch = new Kontaktbuch();
		buch.addKontakt(name2);
		buch.addAdresse(strasse, hausnummer, postleitzahl, ort);
		selectedKontakt=null;
		assertEquals("Fehler",buch.removeAdresse(name2).getTitel());

	}

	@Test
	public void testPrintHelp() {
		Kontaktbuch buch = new Kontaktbuch();
		List<Ausgabe> ausgaben = buch.printHelp();
		//List<Kontaktbuch.Ausgabe> vergleichsausgaben= new ArrayList<>();
		int anzahlElem = ausgaben.size()-1;
		assertEquals("M�gliche Befehle:",ausgaben.get(0).getTitel());
		assertEquals("",ausgaben.get(0).getInhalt());
		assertEquals("quit",ausgaben.get(anzahlElem).getTitel());
		assertEquals("Beendet das Programm",ausgaben.get(anzahlElem).getInhalt());
	}
	@Test
	public void listALLContacts(){
		//arrange
		Kontaktbuch buch = new Kontaktbuch();
		Kontakt k = new Kontakt(name);
		Ausgabe a = new Ausgabe("a.Kontakt:",k.toString());

		buch.addKontakt(name);
		assertEquals(buch.getKontakte().size() ,buch.listAllContacts().size());
		assertEquals(k.toString(),buch.listAllContacts().get(0).getInhalt());
	}

}
