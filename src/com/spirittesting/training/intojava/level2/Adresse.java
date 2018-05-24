package com.spirittesting.training.intojava.level2;

import java.util.Objects; // Import-Statements erlauben es, eine Klasse unter ihrem Kurznamen (hier: Object) anzusprechen und nicht immer den vollqualifizierten Namen nutzen zu müssen. Sie werden in der Regel von der IDE verwaltet.

class Adresse {
	
	private final String id; // Das Schlüsselwort final sorgt dafür, dass das Feld ausschließlich in seiner eigenen Deklaration und im Konstruktor überschrieben werden kann. Dementsprechend fehlt auch der Setter.
	private String strasse;
	private String postleitzahl;
	
	Adresse(String id) {
		// wir wollen, dass ID niemals auf <null> gesetzt wird. Falls null übergeben wird, wirft die Klasse einen Fehler.
		if (id == null) throw new IllegalArgumentException("Die ID darf nicht <null> sein");
		this.id = id;
	}
	
	Adresse(String id, String strasse, String postleitzahl) {
		this(id);
		setStrasse(strasse);
		setPostleitzahl(postleitzahl);
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getPostleitzahl() {
		return postleitzahl;
	}

	public void setPostleitzahl(String postleitzahl) {
		this.postleitzahl = postleitzahl;
	}

	public String getId() {
		return id;
	}

	// Die folgenden Methoden sollten in jeder Klasse enthalten sein. Sie lassen sich bequem automatisch generieren.
	
	@Override	// @Override ist eine Annotation. Sie signalisiert dem Java Compiler, dass diese Methode eine andere in einer Eltern-Klasse überschreibt. Jede Klasse erbt von java.lang.Object und java.lang.Object enthält unsere drei Standardmethoden.
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		return Objects.equals(id, other.getId());
	}

	@Override
	public String toString() {
		return "Adresse [id=" + id + ", strasse=" + strasse + ", postleitzahl=" + postleitzahl + "]";
	}
	
}
