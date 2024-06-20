package org.enterprise.pruebajikko.view;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.enterprise.pruebajikko.controller.PaisController;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@Slf4j
public class PaisView {

  private PaisController paisController;

  @Autowired
  public PaisView(PaisController paisController) {
    this.paisController = paisController;
  }

  @GetMapping("/pais")
  public ResponseEntity consultarPais (
    @RequestParam(name = "pais")
    String pais) {
    return paisController.consultarPais(pais);
  }


}
