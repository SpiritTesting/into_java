package com.spirittesting.training.intojava.level4;

class Adresse {
	
	private final String id; // Das Schlüsselwort final sorgt dafür, dass das Feld ausschließlich in seiner eigenen Deklaration und im Konstruktor überschrieben werden kann. Dementsprechend fehlt auch der Setter.
	private String strasse;
	private String hausnummer;
	private String postleitzahl;
	private String ort;
	
	Adresse(String id) {
		// wir wollen, dass ID niemals auf <null> gesetzt wird. Falls null übergeben wird, wirft die Klasse einen Fehler.
		if (id == null) throw new IllegalArgumentException("Die ID darf nicht <null> sein");
		this.id = id;
	}
	
	Adresse(String id, String strasse, String hausnummer, String postleitzahl, String ort) {
		this(id);
		setStrasse(strasse);
		setHausnummer(hausnummer);
		setPostleitzahl(postleitzahl);
		setOrt(ort);
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
	
	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hausnummer == null) ? 0 : hausnummer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + ((postleitzahl == null) ? 0 : postleitzahl.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
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
		Adresse other = (Adresse) obj;
		if (hausnummer == null) {
			if (other.hausnummer != null)
				return false;
		} else if (!hausnummer.equals(other.hausnummer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ort == null) {
			if (other.ort != null)
				return false;
		} else if (!ort.equals(other.ort))
			return false;
		if (postleitzahl == null) {
			if (other.postleitzahl != null)
				return false;
		} else if (!postleitzahl.equals(other.postleitzahl))
			return false;
		if (strasse == null) {
			if (other.strasse != null)
				return false;
		} else if (!strasse.equals(other.strasse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Adresse [id=" + id + ", strasse=" + strasse + ", hausnummer=" + hausnummer + ", postleitzahl="
				+ postleitzahl + ", ort=" + ort + "]";
	}

}
