package com.company;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class KontaktTest {


    private static final String id = "#42";
    private static final String strasse = "Mannheimer Stra�e";
    private static final String hausnummer = "123a";
    private static final String postleitzahl = "90402";
    private static final String ort = "Strunzen�d (Oberbayern)";

    private static final String name = "Michael";

    private static final String toString = "Adresse [id=#42, strasse=Mannheimer Stra�e, hausnummer=123a, postleitzahl=90402, ort=Strunzen�d (Oberbayern)]";

    private final List<Kontakt> kontakte = new ArrayList<>();


    private final Set<Adresse> adressen = new HashSet<>();


    @Test
    public void testGetAdressen() {

        // arrange


        // act

        // assert
    }

    @Test
    public void testGetName() {
    }

    @Test
    public void testAddAdresse() {
        // arrange
        Adresse a = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
        /*List<Kontakt> kontakte = new ArrayList<>();*/
        /*Kontakt selectedKontakt = null;*/

        Kontakt k = new Kontakt(name);

        // act
        k.addAdresse(a);
        Set<Adresse> result = k.getAdressen();

        // assert


        boolean contains = result.contains(a);

        assertTrue(contains);


    }

    @Test
    public void testRemoveAdresse() {

        // arrange
        Adresse a = new Adresse(id, strasse, hausnummer, postleitzahl, ort);

        Kontakt k = new Kontakt(name);

        // act
        k.addAdresse(a);

        k.removeAdresse(a);

        Set<Adresse> result = k.getAdressen();

        boolean contains = result.contains(a);

        assertFalse(contains);
    }


    @Test
    public void testToString() {
        // arrange
        Adresse a = new Adresse(id, strasse, hausnummer, postleitzahl, ort);

        Kontakt k = new Kontakt(name);



        // act
        k.addAdresse(a);

        String targetValue = "Kontakt [name=" + name + ", adressen=" + k.getAdressen() + "]";

        String result = k.toString();

        assertEquals(targetValue, result);

    }

    @Test
    public void testHashCode() {

        Kontakt kontakt = new Kontakt(name);
        int hash1 = kontakt.hashCode();

        Adresse adresse = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
        int hash2 = adresse.hashCode();
        assertNotEquals(hash1, hash2);

        kontakt.addAdresse(adresse);

        int hash3 = kontakt.getAdressen().hashCode();
        assertNotEquals(hash1, hash3);

        Kontakt kontakt2 = new Kontakt("xyz");
        assertNotEquals(kontakt.hashCode(), kontakt2.hashCode());

    }


    @Test
    public void testEqualsObject() {
        Kontakt compareObject = new Kontakt("xyz");
        Kontakt kontakt = new Kontakt(name);


        assertNotEquals(compareObject, kontakt);
        assertNotEquals(null, kontakt);


        Adresse adresse = new Adresse(id, strasse, hausnummer, postleitzahl, ort);

        compareObject.addAdresse(adresse);
        assertNotEquals(compareObject, kontakt);





    }
}

