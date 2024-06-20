package org.enterprise.pruebajikko.model.repository;

import org.enterprise.pruebajikko.model.entity.Pais;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PaisRepository extends CrudRepository<Pais, Long> {

  @Query(value = "select p.nombre,p.moneda, d.poblacion, c.nombre, c.capital as ciudad from Pais p JOIN p.departamentos d join d.ciudades c " +
          "where p.nombre = ?1 ")
  List<Object> findById(String pais);
}
