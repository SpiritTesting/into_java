package com.spirittesting.training.intojava.level5.currency;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BetragTest {

    @Test
    public void testBetrag() {
        Betrag betrag = new Betrag(0, 0, Währung.EUR);
        Betrag betrag1 = new Betrag(Integer.MAX_VALUE, 0, Währung.USD);
    }

    @Test(expected = BetragException.class)
    public void testBetragNegativ() {
            Betrag betrag = new Betrag(-1, 0, Währung.EUR);
    }

    @Test
    public void addiere_null() {
        Betrag summand1 = new Betrag(0,0,Währung.USD);
        Betrag summand2 = new Betrag(0, 0, Währung.USD);
        assertEquals(summand1, summand1.addiere(summand2));

    }

    @Test
    public void addiere_istKommutativ() {
        Betrag summand1 = new Betrag(1,0,Währung.USD);
        Betrag summand2 = new Betrag(2, 0, Währung.USD);
        Betrag summe = new Betrag(3, 0, Währung.USD);
        assertEquals(summe, summand1.addiere(summand2));
        assertEquals(summe, summand2.addiere(summand1));
    }

    @Test
    public void addiere_istTransitiv() {
        Betrag summand1 = new Betrag(1,0,Währung.USD);
        Betrag summe = new Betrag(3, 0, Währung.USD);
        assertEquals(summe, summand1.addiere(summand1.addiere(summand1)));
    }

    @Test
    public void addiere_nachkommabereich() {
        Betrag summand1 = new Betrag(0,50,Währung.USD);
        Betrag summand2 = new Betrag(0, 25, Währung.USD);
        Betrag summe = new Betrag(0, 75, Währung.USD);
        assertEquals(summe, summand1.addiere(summand2));
    }

    @Test
    public void addiere_nachkommabereich_ueberlauf() {
        Betrag summand1 = new Betrag(0,50,Währung.USD);
        Betrag summand2 = new Betrag(0, 75, Währung.USD);
        Betrag summe = new Betrag(1, 25, Währung.USD);
        assertEquals(summe, summand1.addiere(summand2));
    }

    @Test
    public void subtrahiere_simpel() {
        Betrag minuend = new Betrag(10, 75, Währung.GBP);
        Betrag subtrahend = new Betrag(5, 25, Währung.GBP);
        Betrag differenz = new Betrag(5, 50, Währung.GBP);
        assertEquals(differenz, minuend.subtrahiere(subtrahend));
    }

    @Test
    public void subtrahiere_ueberlauf() {
        Betrag minuend = new Betrag(10, 25, Währung.GBP);
        Betrag subtrahend = new Betrag(5, 50, Währung.GBP);
        Betrag differenz = new Betrag(4, 75, Währung.GBP);
        assertEquals(differenz, minuend.subtrahiere(subtrahend));
    }

    @Test
    public void testCompareTo() {
        Betrag kleinst = new Betrag(0, 0, Währung.USD);
        Betrag klein = new Betrag(0, 99, Währung.USD);
        Betrag mittel = new Betrag(50, 50, Währung.USD);
        Betrag groß = new Betrag(99, 99, Währung.USD);
        Betrag größt = new Betrag(Integer.MAX_VALUE, 99,Währung.USD);
        List<Betrag> unsorted = Arrays.asList(größt, groß, mittel, klein, kleinst);
        List<Betrag> sorted = Arrays.asList(kleinst, klein, mittel, groß, größt);
        Collections.sort(unsorted);
        assertEquals(sorted, unsorted);
    }
}
