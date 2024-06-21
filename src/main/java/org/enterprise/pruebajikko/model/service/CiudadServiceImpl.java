package org.enterprise.pruebajikko.model.service;

import org.enterprise.pruebajikko.model.entity.Ciudad;
import org.enterprise.pruebajikko.model.entity.Departamento;
import org.enterprise.pruebajikko.model.repository.CiudadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadServiceImpl implements ICiudadService {

  CiudadRepository ciudadRepository;

  public CiudadServiceImpl(CiudadRepository ciudadRepository) {
    this.ciudadRepository = ciudadRepository;
  }

  public void crearCiudad(Ciudad ciudad) {
      ciudadRepository.save(ciudad);
  }

  public void updateCiudad(Ciudad ciudad) {
      ciudadRepository.save(ciudad);
  }

  @Override
  public List<Ciudad> findCiudadByIdDepartamento(Integer idDepartamento) {
    return ciudadRepository.findCiudadByIdDepartamento(Departamento.builder().id(idDepartamento).build());
  }

  @Override
  public void deleteCiudades(List<Ciudad> ciudades) {
    ciudadRepository.deleteAll(ciudades);
  }

}
