package com.spirittesting.training.intojava.level6.repositories;

import com.spirittesting.training.intojava.level6.model.entities.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KundenRepository extends JpaRepository<Kunde, Integer> {

    Kunde findByKundennummer(String kundennummer);

}
