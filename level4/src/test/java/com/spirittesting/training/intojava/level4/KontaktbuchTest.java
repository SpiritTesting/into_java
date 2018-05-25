package com.spirittesting.training.intojava.level4;

import org.junit.Test;

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
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveKontakt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectKontakt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAdresse() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveAdresse() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintHelp() {
		fail("Not yet implemented");
	}

}
