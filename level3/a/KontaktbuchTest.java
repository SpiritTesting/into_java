package a;//package com.spirittesting.training.intojava.level3;

import static org.junit.Assert.*;

import org.junit.Test;

import javax.naming.NamingEnumeration;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

//import com.spirittesting.training.intojava.level3.a.Kontaktbuch.Ausgabe;

public class KontaktbuchTest {

    /*
     * Um die Klasse a.Kontaktbuch testf�hig zu machen, mussten wir sie etwas umbauen. Die einzelnen Methoden sind jetzt nicht
     * mehr selbst f�r die Ausgaben zust�ndig. Stattdessen geben sie jeweils ein (oder mehrere) Instanzen einer neu erstellten
     * Klasse zur�ck, die dann in der Main-Methode in Ausgaben umgewandelt werden k�nnen.
     *
     * Die Testf�lle hier sind etwas komplizierter, da wir jeweils einen komplexen Zustand im Konaktbuch setzen und pr�fen m�ssen.
     * Das hei�t auch, dass wir die jeweiligen Testkandidaten - also die Methoden im a.Kontaktbuch - mit mehreren Testf�llen
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
     * Ausgehend von einem leeren a.Kontaktbuch f�gen wir einen a.Kontakt hinzu.
     * Wir erwarten, dass danach genau ein a.Kontakt vorliegt.
     * Wir erwarten, dass dieser a.Kontakt den �bergebenen Namen besitzt.
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
     * Ausgehend von einem gef�llten a.Kontaktbuch f�gen wir einen a.Kontakt hinzu.
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
        Kontaktbuch.Ausgabe ausgabe = buch.addKontakt(name);
        System.out.print(buch.getKontakte().get(0).toString());
        System.out.print(ausgabe.getInhalt());
        assertEquals(buch.getKontakte().get(0).toString(), ausgabe.getInhalt());

        //List <Kontakt> result= buch.getKontakte().get(0);
        //assertEquals("Gew�hlter a.Kontakt", ausgabe.getTitel());
        //assertEquals("Willi W�hlkelle",ausgabe.getInhalt());

    }

    @Test
    public void testShowSelectedContact_keinGewaehlterKontakt() {
        Kontaktbuch buch = new Kontaktbuch();
        Kontaktbuch.Ausgabe ausgabe = buch.showSelectedContact();
        assertEquals("Gew�hlter a.Kontakt", ausgabe.getTitel());
        assertEquals("<kein a.Kontakt gew�hlt>", ausgabe.getInhalt());
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
        Kontaktbuch.Ausgabe a = new Kontaktbuch.Ausgabe("Fehler", "Kein a.Kontakt gew�hlt");
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
        Adresse vergleichsAdresse = new Adresse("#0", strasse, hausnummer, postleitzahl, ort);
        buch.addAdresse(strasse, hausnummer, postleitzahl, ort);
        assertTrue(buch.getKontakte().get(0).getAdressen().contains(vergleichsAdresse));
        buch.removeAdresse("#0");
        assertFalse(buch.getKontakte().get(0).getAdressen().contains(vergleichsAdresse));
    }

    @Test
    public void testPrintHelp() {
        Kontaktbuch buch = new Kontaktbuch();
        List<Kontaktbuch.Ausgabe> ausgaben = buch.printHelp();
        //List<Kontaktbuch.Ausgabe> vergleichsausgaben= new ArrayList<>();
        int anzahlElem = ausgaben.size()-1;

       /* vergleichsausgaben.add(new Kontaktbuch.Ausgabe("M�gliche Befehle:", ""));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("help", " diese Anzeige"));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("list", " gibt das gesamte Adressbuch aus"));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("add <name>", "Legt neuen a.Kontakt an."));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("rem <name>", "L�scht einen a.Kontakt."));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("sel <name>", "W�hlt einen a.Kontakt zur weiteren Bearbeitung"));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("who", "zeigt den zur Bearbeitung gew�hlten a.Kontakt"));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("newadd <strasse> <hausnummer> <postleitzahl> <ort>", "f�gt dem gew�hlten a.Kontakt eine a.Adresse hinzu"));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("remadd <id>", "entfernt die a.Adresse mit <id> vom gew�hlten a.Kontakt"));
        vergleichsausgaben.add(new Kontaktbuch.Ausgabe("quit", "Beendet das Programm"));*/

        assertEquals("M�gliche Befehle:",ausgaben.get(0).getTitel());
        assertEquals("",ausgaben.get(0).getInhalt());
        assertEquals("quit",ausgaben.get(anzahlElem).getTitel());
        assertEquals("Beendet das Programm",ausgaben.get(anzahlElem).getInhalt());
        //assertTrue(ausgaben.contains(vergleichsausgaben));
    }
    @Test
    public void listALLContacts(){
        //arrange
        Kontaktbuch buch = new Kontaktbuch();
        Kontakt k = new Kontakt(name);
        Kontaktbuch.Ausgabe a = new Kontaktbuch.Ausgabe("a.Kontakt:",k.toString());

        buch.addKontakt(name);
        assertEquals(buch.getKontakte().size() ,buch.listAllContacts().size());
        //assertEquals(k.toString(),buch.getKontakte());
        assertEquals(k.toString(),buch.listAllContacts().get(0).getInhalt());
        //System.out.println(ausgaben.get(0).getInhalt());
    }

}



