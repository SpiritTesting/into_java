package com.spirittesting.training.intojava.level6.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"kunde"})
@EqualsAndHashCode(exclude = "kunde")
public class Konto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kontonummer;

    @Column
    private LocalDateTime timestamp;

    @PreUpdate
    @PrePersist
    public void updateTimestamp() {
        timestamp = LocalDateTime.now();
    }

    @ManyToOne
    private Kunde kunde;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "von")
    private List<Zahlung> abgehend;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nach")
    private List<Zahlung> eingehend;
}
