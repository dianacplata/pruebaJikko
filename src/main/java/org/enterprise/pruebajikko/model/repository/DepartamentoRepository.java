package org.enterprise.pruebajikko.model.repository;

import org.enterprise.pruebajikko.model.entity.Departamento;
import org.enterprise.pruebajikko.model.entity.Pais;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartamentoRepository extends CrudRepository<Departamento, Integer> {

  List<Departamento> findDepartamentoByIdPais(Pais pais);
}
