package org.enterprise.pruebajikko.controller;


import org.enterprise.pruebajikko.model.service.IPaisService;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaisControllerImpl implements PaisController {

  private IPaisService paisService;

  public PaisControllerImpl(IPaisService paisService) {
    this.paisService = paisService;
  }

  @Override
  public ResponseEntity<List<PaisDto>> consultarPais(String pais) {
    final List<PaisDto> paisConsultado = new ArrayList<>();
    ResponseEntity<List<PaisDto>> responseEntity;
    try {
      paisService.findPais(pais).stream().forEach(paisDto ->
        paisConsultado.add(PaisDto.builder()
            .nombre(paisDto.getNombre())
            .moneda(paisDto.getMoneda())
            .poblacion(paisDto.getPoblacion())
            .ciudad(paisDto.getCiudad()).build())
      );

      responseEntity = ResponseEntity.status(HttpStatus.OK).body(paisConsultado);
    } catch (Exception e) {
      responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return responseEntity;
  }
}
