package com.spirittesting.training.intojava.level2;

/* Schau zun�chst in die Klasse Adresse. Die dort beschriebenen Konzepte werden hier nicht wiederholt, aber direkt angewandt. */

import java.util.HashSet;
import java.util.Set;

class Kontakt {
	
	private final String name;
	/* Ein Set ist eine Sammlung von nicht-gleichen Objekten. 
	 * Dieses Set darf nur Objekte der Klasse 'Adresse' enthalten. 
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
		return "Kontakt [name=" + name + ", adressen=" + adressen + "]";
	}
	
	

}
