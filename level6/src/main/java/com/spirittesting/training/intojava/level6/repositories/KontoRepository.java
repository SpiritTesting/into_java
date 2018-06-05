package com.spirittesting.training.intojava.level6.repositories;

import com.spirittesting.training.intojava.level6.model.entities.Konto;
import com.spirittesting.training.intojava.level6.model.entities.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KontoRepository extends JpaRepository<Konto, Integer> {

    Konto findByKontonummer(int kontonummer);

    List<Konto> findByKunde(Kunde kunde);

}
