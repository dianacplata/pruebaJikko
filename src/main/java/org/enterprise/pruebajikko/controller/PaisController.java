package org.enterprise.pruebajikko.controller;

import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.enterprise.pruebajikko.view.dto.PaisInfoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaisController {

  ResponseEntity<List<PaisDto>> consultarPais(String pais);

  ResponseEntity<Void> crearPais(PaisInfoDto paisDto);

  ResponseEntity<Void> actualizarPais(PaisInfoDto paisDto);

  ResponseEntity<Void> eliminarPais(Integer id);
}
