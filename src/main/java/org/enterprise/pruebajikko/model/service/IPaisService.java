package org.enterprise.pruebajikko.model.service;

import org.enterprise.pruebajikko.view.dto.PaisDto;

import java.util.List;

public interface IPaisService {

  List<PaisDto> findPais(String pais);
}
