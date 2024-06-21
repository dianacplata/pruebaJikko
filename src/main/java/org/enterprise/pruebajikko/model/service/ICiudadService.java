package org.enterprise.pruebajikko.model.service;

import org.enterprise.pruebajikko.model.entity.Ciudad;

import java.util.List;

public interface ICiudadService {

  void crearCiudad(Ciudad ciudad);

  void updateCiudad(Ciudad ciudad);

  List<Ciudad> findCiudadByIdDepartamento(Integer idDepartamento);

  void deleteCiudades(List<Ciudad> ciudades);
}
