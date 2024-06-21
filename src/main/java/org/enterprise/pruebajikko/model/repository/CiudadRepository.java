package org.enterprise.pruebajikko.model.repository;

import org.enterprise.pruebajikko.model.entity.Ciudad;
import org.enterprise.pruebajikko.model.entity.Departamento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CiudadRepository extends CrudRepository<Ciudad, Integer> {

  public List<Ciudad> findCiudadByIdDepartamento(Departamento departamento);
}
