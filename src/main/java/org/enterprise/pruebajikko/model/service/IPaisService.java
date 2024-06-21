package org.enterprise.pruebajikko.model.service;

import org.enterprise.pruebajikko.model.entity.Pais;
import org.enterprise.pruebajikko.view.dto.PaisDto;


import java.util.List;

public interface IPaisService {

  List<PaisDto> findPais(String pais);

  void createPais(Pais pais);

  void updatePais(Pais pais);

  void deletePais(Integer id);

  Pais findById(Integer id);
}
