package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdresseTest {

    /* hier oben lege ich mir ein paar Konstanten an, um sp�ter sicher dieselben Strings zu verwenden */
    private static final String id = "#42";
    private static final String strasse = "Mannheimer Stra�e";
    private static final String hausnummer = "123a";
    private static final String postleitzahl = "90402";
    private static final String ort = "Strunzen�d (Oberbayern)";

    private static final String toString = "Adresse [id=#42, strasse=Mannheimer Stra�e, hausnummer=123a, postleitzahl=90402, ort=Strunzen�d (Oberbayern)]";

    @Test
    /**
     * Testet den Konstruktor Adresse(String id)
     *
     * Wir wollen hier pr�fen, ob das Objekt im Normalfall so initialisiert wurde, wie wir es erwarten. Unsere Erwartung
     * ist einfach: Das Feld 'id' ist mit dem �bergebenen Wert belegt, alle anderen Felder sind null.
     */
    public void testAdresseString() {
        // Wir erzeugen uns ein Testobjekt, indem wir den entsprechenden Konstruktor benutzen
        Adresse adresse = new Adresse(id);

        // Mit 'assertions' (Feststellungen) pr�fen wir dann die einzelnen Felder.
        // Assertions stammen aus der Klasse org.junit.Assert. Die ist nicht Teil der
        // JDK, sondern kommt als Bibliothek daher.
        // In Eclipse m�ssen wir im Package Explorer den Ordner 'lib' �ffnen und dann jeweils auf 'junit-4.12.jar' und
        // 'hamcrest-core-1.3.jar' rechtsklicken, 'BuildPath' ausw�hlen und schlie�lich 'add to Buildpath'.
        // Aus der Kommandozeile heraus ist der Start etwas komplizierter. Zun�chst mal besitzt der Test ja gar
        // keine Main-Methode. Gl�cklicherweise stellt die JUnit Bibliothek einen sogenannten TestRunner zur
        // Verf�gung, der durchaus eine MainMethode besitzt und als Argument(e) den oder die auszuf�hrenden
        // Testklassen erwartet.
        // Wir navigieren also in den 'bin' Ordner und f�hren aus:
        // java -cp .;..\level3\lib\hamcrest-core-1.3.jar;..\level3\lib\junit-4.12.jar org.junit.runner.JUnitCore com.spirittesting.training.intojava.level3.AdresseTest
        // (in der git-Konsole m�ssen die ';'-Zeichen im Classpath escaped werden, dort sieht es dann so aus:
        // java -cp .\;../level3/lib/hamcrest-core-1.3.jar\;../level3/lib/junit-4.12.jar org.junit.runner.JUnitCore com.spirittesting.training.intojava.level3.AdresseTest

        assertEquals(id, adresse.getId());
        assertNull(adresse.getStrasse());
        assertNull(adresse.getHausnummer());
        assertNull(adresse.getPostleitzahl());
        assertNull(adresse.getOrt());

    }

    @Test
    /**
     * Testet den Konstruktor Adresse(String id, String strasse, String hausnummer, String postleitzahl, String ort)
     */
    public void testAdresseStringStringStringStringString() {
        Adresse adresse = new Adresse (id, strasse, hausnummer, postleitzahl, ort);
        assertEquals(id, adresse.getId());
        assertEquals(strasse, adresse.getStrasse());
        assertEquals(hausnummer, adresse.getHausnummer());
        assertEquals(postleitzahl, adresse.getPostleitzahl());
        assertEquals(ort, adresse.getOrt());
    }

    @Test
    /**
     * Tests f�r primitive Getter sind relativ wertlos. Interessant werden sie, sobald ein Getter Seiteneffekte hat
     * (z.B. wird der Wert durch das abholen erst erzeugt) oder eine weitere Verarbeitung (z.B. gib nie null zur�ck,
     * sondern wirf einen Fehler)
     */
    public void testGetStrasse() {
        Adresse adresse = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
        assertEquals(strasse, adresse.getStrasse());
    }

    @Test
    /**
     * F�r Setter gilt nat�rlich dasselbe. Sonderbehandlungen sollten dringend gepr�ft werden. Nimmt der Setter aber
     * nur einen Parameter und �berschreibt damit ein Feld, testen wir an der Stelle nichts - au�er das Java funktioniert.
     */
    public void testSetStrasse() {
        Adresse adresse = new Adresse(id, null, hausnummer, postleitzahl, ort);

        adresse.setStrasse(strasse);
        assertEquals(strasse, adresse.getStrasse());
    }

    @Test
    public void testGetPostleitzahl() {
        Adresse adresse = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
        assertEquals(postleitzahl, adresse.getPostleitzahl());
    }

    @Test
    public void testSetPostleitzahl() {
        Adresse adresse = new Adresse(id, strasse, hausnummer, null, ort);

        adresse.setPostleitzahl(postleitzahl);
        assertEquals(postleitzahl, adresse.getPostleitzahl());
    }

    @Test
    public void testGetId() {
        Adresse adresse = new Adresse(id);
        assertEquals(id, adresse.getId());
    }

    @Test
    /**
     * Autogenerierte Methoden, also Boilerplate-Code, m�ssen in der Regel auch nicht intensiv getestet werden.
     * Hier k�nnte aber zum Beispiel gepr�ft werden, ob bei einer Erweiterung der Klasse um neue Felder diese
     * auch in equals(), hashCode() und toString() aufgenommen wurden.
     */
    public void testEqualsObject() {
        Adresse vergleichsobjekt = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
        Adresse adresse = new Adresse(id); // id identisch, alle anderen weichen ab
        assertNotEquals(vergleichsobjekt, adresse);

        adresse.setStrasse(strasse); // noch immer Abweichungen
        assertNotEquals(vergleichsobjekt, adresse);

        adresse.setHausnummer(hausnummer); // noch immer Abweichungen
        assertNotEquals(vergleichsobjekt, adresse);

        adresse.setPostleitzahl(postleitzahl); // noch immer Abweichungen
        assertNotEquals(vergleichsobjekt, adresse);

        adresse.setOrt(ort); // jetzt sind alle Felder identisch, also sollten die Objekte auch gleich sein
        assertEquals(vergleichsobjekt, adresse);

        adresse.setStrasse(null); // das h�tte aber auch funktioniert, wenn nur auf 'ort' gepr�ft wird. Also schlie�en wir den Fall auch noch aus.
        assertNotEquals(vergleichsobjekt, adresse);
    }

    @Test
    public void testHashCode() {
        Adresse adresse = new Adresse(id);
        int hash1 = adresse.hashCode();

        adresse.setStrasse(strasse);
        int hash2 = adresse.hashCode();
        assertNotEquals(hash1, hash2);

        adresse.setHausnummer(hausnummer);
        int hash3 = adresse.hashCode();
        assertNotEquals(hash2, hash3);

        adresse.setPostleitzahl(postleitzahl);
        int hash4 = adresse.hashCode();
        assertNotEquals(hash3, hash4);

        adresse.setOrt(ort);
        int hash5 = adresse.hashCode();
        assertNotEquals(hash4, hash5);

        // und was, wenn nur die ID abweicht?
        Adresse adresse2 = new Adresse("asdf�al");
        assertNotEquals(adresse.hashCode(), adresse2.hashCode());
    }

    @Test
    public void testToString() {
        Adresse adresse = new Adresse(id, strasse, hausnummer, postleitzahl, ort);
        assertEquals(toString, adresse.toString());
    }


}
