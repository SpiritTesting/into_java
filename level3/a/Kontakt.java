package a;//package com.spirittesting.training.intojava.level3;

/* Schau zun�chst in die Klasse a.Adresse. Die dort beschriebenen Konzepte werden hier nicht wiederholt, aber direkt angewandt. */

import java.util.HashSet;
import java.util.Set;

class Kontakt {

    private final String name;
    /* Ein Set ist eine Sammlung von nicht-gleichen Objekten.
     * Dieses Set darf nur Objekte der Klasse 'a.Adresse' enthalten.
     * Die Variable wird direkt initialisiert und enth�lt ein leeres Set.
     * Wir m�chten den Inhalt von 'adressen' nie vollst�ndig austauschen, sondern immer nur Inhalt hinzuf�gen oder entfernen. Daher deklarieren wir das Set final.
     * Der innere Zustand des Objekts kann trotzdem ge�ndert werden!
     */
    private final Set<Adresse> adressen = new HashSet<>();

    Kontakt(String name) {
        if (name == null) throw new IllegalArgumentException("name darf nicht <null> sein");
        this.name = name;
    }

    public Set<Adresse> getAdressen() {
        return adressen;
    }

    public String getName() {
        return name;
    }

    public void addAdresse(Adresse adresse) {
        adressen.add(adresse);
    }

    public void removeAdresse(Adresse adresse) {
        adressen.remove(adresse);
    }

    @Override
    public String toString() {
        return "a.Kontakt [name=" + name + ", adressen=" + adressen + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((adressen == null) ? 0 : adressen.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Kontakt other = (Kontakt) obj;
        if (adressen == null) {
            if (other.adressen != null)
                return false;
        } else if (!adressen.equals(other.adressen))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}

