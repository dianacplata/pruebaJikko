package org.enterprise.pruebajikko.model.service;


import org.enterprise.pruebajikko.model.entity.Departamento;
import org.enterprise.pruebajikko.model.entity.Pais;
import org.enterprise.pruebajikko.model.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoServiceImpl implements IDepartamentoService{

  private DepartamentoRepository departamentoRepository;

  public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository) {
    this.departamentoRepository = departamentoRepository;
  }

  @Override
  public List<Departamento> findDepartamentoByIdPais(Integer idPais) {
    return departamentoRepository.findDepartamentoByIdPais(Pais.builder().id(idPais).build());
  }

  public void crearDepartamento(Departamento departamento) {
    departamentoRepository.save(departamento);
  }

  public void updateDepartamento(Departamento departamento) {
    departamentoRepository.save(departamento);
  }

public void deleteDepartamentos(List<Departamento> departamentos) {
    departamentoRepository.deleteAll(departamentos);
  }
}
