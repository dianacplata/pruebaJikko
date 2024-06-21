package org.enterprise.pruebajikko.view;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.enterprise.pruebajikko.controller.PaisController;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.enterprise.pruebajikko.view.dto.PaisInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping("crear/pais")
  public ResponseEntity<Void> crearPais(@Valid @RequestBody PaisInfoDto paisDto) {
    return paisController.crearPais(paisDto);
  }

  @PutMapping("actualizar/pais")
  public ResponseEntity<Void> actualizarPais(@Valid @RequestBody PaisInfoDto paisDto) {
    return paisController.actualizarPais(paisDto);
  }

  @DeleteMapping("eliminar/pais")
  public ResponseEntity<Void> eliminarPais(@RequestParam(name = "id") @NotNull Integer id) {
    return paisController.eliminarPais(id);
  }


}
