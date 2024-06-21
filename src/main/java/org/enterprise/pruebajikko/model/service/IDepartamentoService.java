package org.enterprise.pruebajikko.model.service;

import org.enterprise.pruebajikko.model.entity.Departamento;

import java.util.List;

public interface IDepartamentoService {

  List<Departamento> findDepartamentoByIdPais(Integer idPais);

  void crearDepartamento(Departamento departamento);

  void updateDepartamento(Departamento departamento);

  void deleteDepartamentos(List<Departamento> departamentos);
}
