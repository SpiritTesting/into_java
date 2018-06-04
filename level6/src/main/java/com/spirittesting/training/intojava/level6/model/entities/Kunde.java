package com.spirittesting.training.intojava.level6.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Kunde {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int kundennummer;

    @Basic
    private String name;

    @Column
    private LocalDateTime timestamp;

    @PreUpdate
    @PrePersist
    public void updateTimestamp() {
        timestamp = LocalDateTime.now();
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kunde")
    private List<Konto> konten;

}
