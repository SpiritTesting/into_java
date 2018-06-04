package com.spirittesting.training.intojava.level6.repositories;

import com.spirittesting.training.intojava.level6.model.entities.Konto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KontoRepository extends JpaRepository<Konto, Integer> {

    Konto findByKontonummer(String kontonummer);

}
