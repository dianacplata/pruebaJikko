package org.enterprise.pruebajikko.controller;


import org.apache.catalina.connector.Response;
import org.enterprise.pruebajikko.model.service.IPaisService;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PaisControllerImpl implements PaisController {

  private IPaisService paisService;

  public PaisControllerImpl(IPaisService paisService) {
    this.paisService = paisService;
  }

  @Override
  public ResponseEntity consultarPais(String pais) {
    final List<PaisDto> paisConsultado = new ArrayList<>();
    ResponseEntity<List<PaisDto>> responseEntity;
    try {
      validationParameters(pais);
      paisService.findPais(pais).stream().forEach(paisDto ->
        paisConsultado.add(PaisDto.builder()
            .nombre(paisDto.getNombre())
            .moneda(paisDto.getMoneda())
            .poblacion(paisDto.getPoblacion())
            .capital(paisDto.getCapital()).build())
      );

      responseEntity = ResponseEntity.status(HttpStatus.OK).body(paisConsultado);
    } catch (Exception e) {
      Map<String,Object> body = new HashMap<>();
      body.put("error", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    return responseEntity;
  }

  public void validationParameters(String pais) {
    if (pais == null || pais.isEmpty()) {
      throw new IllegalArgumentException("El parámetro 'pais' no puede ser nulo o vacío");
    }

    if (pais.length() < 3) {
      throw new IllegalArgumentException("El parámetro 'pais' debe tener al menos 3 caracteres");
    }

    if (!pais.matches("^[a-zA-Z]*$")) {
      throw new IllegalArgumentException("El parámetro 'pais' solo puede contener letras");
    }
  }
}
