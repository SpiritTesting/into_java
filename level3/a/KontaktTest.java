package a;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

public class KontaktTest {
    private static final String name = "K";
    private static final String id = "#42";
    private static final String strasse = "Mannheimer Stra�e";
    private static final String hausnummer = "123a";
    private static final String postleitzahl = "90402";
    private static final String ort = "Strunzen�d (Oberbayern)";
    private static final Set<Adresse> adressen = new HashSet<>();
    private static final String toString=  "a.Kontakt [name=" + name + ", adressen=" + adressen + "]";
    //private static final String toString = "a.Adresse [id=#42, strasse=Mannheimer Stra�e, hausnummer=123a, postleitzahl=90402, ort=Strunzen�d (Oberbayern)]";
    Kontakt k = new Kontakt(name);
    ;
    private final List<Kontakt> kontakte = new ArrayList<>();
    Adresse a = new Adresse(id,strasse,hausnummer,postleitzahl,ort);
    //Adresse adresseB = new Adresse("#1","abc","123","567","vbn");
    @Test
     public void getAdressen() {
    }

    @Test
    public void getName() {
        Kontakt k = new Kontakt(name);
        assertEquals(name, k.getName());
    }

    @Test
    public void testAddAdresse() {
        kontakte.add(k);
        k.addAdresse(a);
        Set<Adresse>result = k.getAdressen();
        assertEquals(1,result.size());
        assertTrue(result.contains(a));





    }

    @Test
    public void removeAdresse() {
        k.addAdresse(a);
        Set<Adresse>result = k.getAdressen();
        assertTrue(result.contains(a));
        k.removeAdresse(a);
        assertFalse(result.contains(a));

    }

    @Test
    public void testToString() {
        Kontakt k= new Kontakt(name);
        //k.addAdresse(adresseB);
        //k.addAdresse(a);
        assertEquals(toString, k.toString());

    }

    @Test
    public void testHashCode() {
        Kontakt k = new Kontakt("K");
        int hash1 = k.hashCode();
        Kontakt m = new Kontakt("M");
        int hash2 = m.hashCode();
        assertNotEquals(k.hashCode(),m.hashCode());


    }

    @Test
    public void equals() {
        Kontakt vergleichsobjekt = new Kontakt("v");
        Kontakt y = new Kontakt("y");
        assertNotEquals(vergleichsobjekt, y);
        assertNotEquals(null, y);
        //assertNotEquals(null, vergleichsobjekt);
    }
}