package com.spirittesting.training.intojava.level6.model.entities;

import com.spirittesting.training.intojava.level6.model.Betrag;
import com.spirittesting.training.intojava.level6.model.ZahlungsArt;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Zahlung {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Konto von;

    @ManyToOne
    private Konto nach;

    @Enumerated(EnumType.STRING)
    private ZahlungsArt zahlungsArt;

    @Embedded
    private String betrag;

    /*
     *  Unsere Klasse Betrag eignet sich nicht gut zur Persistierung: wir möchten die Instanzen gern zur Laufzeit
     *  unveränderlich (also final) haben. Die Arbeitsweise der Java Persistence Architecture (JPA) arbeitet jedoch
     *  so, dass zunächst ein leeres Objekt mit dem Default-Konstruktor (public und ohne Argumente) erzeugt wird und
     *  anschließend für jede persistierte Spalte der entsprechende Setter aufgerufen wird.
     *  Zum Glück haben wir für den Betrag bereits vorher einen Weg benötigt, um ihn in einen reinen String umzuwandeln
     *  und - vor allem - aus einem String wieder zurückzuverwandeln.
     *  So überschreiben wir hier Get- und Set mit den seltenen nichttrivialen Methoden: in Richtung der Datenbank
     *  stellen sie den Betrag als String dar, in Richtung der Applikation jedoch als 'Betrag'.
     */
    public void setBetrag(Betrag betrag) {
        this.betrag = betrag.toString();
    }

    public Betrag getBetrag() {
        return new Betrag(betrag);
    }

    @Column
    private LocalDateTime timestamp;

    @PreUpdate
    @PrePersist
    public void updateTimestamp() {
        timestamp = LocalDateTime.now();
    }


}
