package com.spirittesting.training.intojava.level4;

import static org.junit.Assert.*;

import org.junit.Test;

import com.spirittesting.training.intojava.level4.Kontaktbuch.Ausgabe;

public class KontaktbuchTest {

	/*
	 * Um die Klasse Kontaktbuch testfähig zu machen, mussten wir sie etwas umbauen. Die einzelnen Methoden sind jetzt nicht
	 * mehr selbst für die Ausgaben zuständig. Stattdessen geben sie jeweils ein (oder mehrere) Instanzen einer neu erstellten
	 * Klasse zurück, die dann in der Main-Methode in Ausgaben umgewandelt werden können.
	 * 
	 * Die Testfälle hier sind etwas komplizierter, da wir jeweils einen komplexen Zustand im Konaktbuch setzen und prüfen müssen.
	 * Das heißt auch, dass wir die jeweiligen Testkandidaten - also die Methoden im Kontaktbuch - mit mehreren Testfällen
	 * je Kandidat abdecken sollten. 
	 * 
	 * Innerhalb dieser Testklasse wird jede Methode als Test erkannt und ausgeführt, die die folgenden Kriterien erfüllt:
	 * - Annotation @Test
	 * - Sichtbarkeit public
	 * - Rückgabewert void
	 * - keine Argumente
	 * 
	 * Der Name der Methode ist dabei gleichgültig, dient uns aber zur Differenzierung, was wir eigentlich prüfen wollen.
	 */
	
	private static final String name = "Hannes Kabeltod";
	
	/**
	 * Testfall:
	 * Ausgehend von einem leeren Kontaktbuch fügen wir einen Kontakt hinzu.
	 * Wir erwarten, dass danach genau ein Kontakt vorliegt.
	 * Wir erwarten, dass dieser Kontakt den übergebenen Namen besitzt. 
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
	 * Ausgehend von einem gefüllten Kontaktbuch fügen wir einen Kontakt hinzu.
	 * Wir erwarten, dass die Anzahl der Kontakte sich um 1 erhöht. 
	 */
	@Test
	public void testAddKontakt_prefilled() {
		Kontaktbuch buch = new Kontaktbuch();
		buch.addKontakt("Willi Wühlkelle");
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
		assertEquals("Gewählter Kontakt", ausgabe.getTitel());
		assertEquals("<kein Kontakt gewählt>", ausgabe.getInhalt());
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
