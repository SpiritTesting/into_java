package com.company;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class KontaktbuchTest {

    private static final String format = "\t%-20s - %s%n";
    private final List<Kontakt> kontakte = new ArrayList<>();
    private Kontakt selectedKontakt = null;

    // Set count to zero initially.
    int countervariable = 0;

    private static final String name1 = "Andi Bar";
    private static final String name2 = "Beta Karotin";
    private static final String name3 = "Gam MaHoam";
    private static final String id = "#1";
    private static final String strasse = "Mannheimer Stra�e";
    private static final String hausnummer = "123a";
    private static final String postleitzahl = "90402";
    private static final String ort = "Strunzen�d (Oberbayern)";

    private static final String toString = "Kontakte:[Kontakt [name=Andi Bar, adressen=[Adresse [id=#1, strasse=Mannheimer Stra�e, hausnummer=123a, postleitzahl=90402, ort=Strunzen�d (Oberbayern)]]]]";
    private static final String toStringAdress = "Adresse [id=#1, strasse=#1, hausnummer=Mannheimer Stra�e, postleitzahl=90402, ort=Strunzen�d (Oberbayern)]";
    private static final String testId = "#1";

    private static Kontaktbuch kb;


    @Before
    public void setUp() throws Exception {

         kb = new Kontaktbuch();


    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testToString(){

        // arrange
        // Kontaktbuch kb = new Kontaktbuch();
        kb.addKontakt(name1);
        kb.selectKontakt(name1);
        kb.addAdresse(strasse,hausnummer,postleitzahl,ort);

        // act

        String result = kb.toString();


        // assert

        assertEquals(toString,result);









    }

    @Test
    public void testGetKontakte() {
    }
    @Test
    public void testListAllContacts(){
        // arrange1
        // nothing to arrange

        // act1
        boolean resultBoolEmpty   = kb.listAllContacts().isEmpty();
        System.out.println("Result 'listAllContacts()' 'isEmpty()' true : = " + resultBoolEmpty);

        // assert1

        assertTrue(resultBoolEmpty);

        // arrange2

        kb.addKontakt(name2);
        kb.addKontakt(name1);
        kb.addKontakt(name3);

        int anzahlKontakte = 3;



        // act2

        int resultInt = kb.listAllContacts().size();
        System.out.println("Result 'listAllContacts' qty: = " + anzahlKontakte);

        boolean resultBoolFilled   = kb.listAllContacts().isEmpty();
        System.out.println("Result 'listAllContacts()' 'isEmpty()' false : = " + resultBoolFilled);


        // assert2

        assertEquals(anzahlKontakte,resultInt);
        assertFalse(resultBoolFilled);



    }

    @Test
    public void testShowSelectedContact() {
    }

    @Test
    public void testAddKontakt() {


        //Kontaktbuch buch = new Kontaktbuch();

        assertTrue(kb.getKontakte().isEmpty());



        kb.addKontakt(name1);

        assertEquals(1, kb.getKontakte().size());

        assertEquals(name1, kb.getKontakte().get(0).getName());
    }
    @Test
    public void testAddKontakt_prefilled() {

        //Kontaktbuch buch = new Kontaktbuch();

        kb.addKontakt(name2);

        kb.addKontakt(name3);

        int preSize = kb.getKontakte().size();



        kb.addKontakt(name1);

        assertEquals(preSize + 1, kb.getKontakte().size());

    }

    @Test
    public void testFindKontakt() {

        //arrange

        //Kontaktbuch buch = new Kontaktbuch();

        kb.addKontakt(name2);
        kb.addKontakt(name1);
        kb.addKontakt(name3);


        //act

        Kontakt result = kb.findKontakt(name1);

        // assert
        // result.getName().contains(name)

        assertEquals(name1,kb.findKontakt(name1).getName());


    }

    @Test
    public void testRemoveKontakt() {

        // arrange

        // Kontaktbuch buch = new Kontaktbuch();

        kb.addKontakt(name2);
        kb.addKontakt(name1);
        kb.addKontakt(name3);

        int size = kb.getKontakte().size();

        // act

        kb.removeKontakt(name1);
        int remSize = kb.getKontakte().size();

        // assert

        assertFalse(kb.getKontakte().contains(name1));
        assertNotEquals(size,remSize);


    }

    @Test
    public void testSelectKontakt() {
        // arrange
        kb.addKontakt(name2);
        kb.addKontakt(name1);
        kb.addKontakt(name3);

        String targetVal = "Kontakt [name=Andi Bar, adressen=[]]";

        // act

        String result = kb.selectKontakt(name1).getInhalt();

        // assert

        assertEquals(targetVal,result);



    }

    @Test
    public void testAddAdresse() {
        // arrange
        kb.addKontakt(name2);
        kb.addKontakt(name1);
        kb.addKontakt(name3);
        kb.selectKontakt(name1);

        String targetVal = toStringAdress;

        // act
        String result = kb.addAdresse(id,strasse,postleitzahl,ort).getInhalt();

        // assert

        assertEquals(targetVal,result);

    }

    @Test
    public void testRemoveAdresse() {

        // arrange
        kb.addKontakt(name2);
        kb.addKontakt(name1);
        kb.addKontakt(name3);
        kb.selectKontakt(name1);

        kb.addAdresse(id,strasse,postleitzahl,ort);

        String targetVal = testId ;

        // act

        String result = kb.removeAdresse(id).getInhalt();

        // assert

        assertEquals(targetVal,result);
    }

    @Test
    public void testPrintHelp() {

        // arrange
        // List<Kontaktbuch.Ausgabe> ausgaben = new ArrayList<>();

        // act




        // assert
    }

    @Test
    public void main() {

        // arrange


        // act


        // assert
    }
}