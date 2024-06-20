package org.enterprise.pruebajikko.controller;

import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaisController {

  ResponseEntity<List<PaisDto>> consultarPais(String pais);
}
