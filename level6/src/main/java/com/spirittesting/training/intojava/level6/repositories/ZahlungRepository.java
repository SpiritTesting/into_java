package com.spirittesting.training.intojava.level6.repositories;

import com.spirittesting.training.intojava.level6.model.entities.Zahlung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZahlungRepository extends JpaRepository<Zahlung, Integer> {

}
