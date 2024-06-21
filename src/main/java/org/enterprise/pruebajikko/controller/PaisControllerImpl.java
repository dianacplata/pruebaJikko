package org.enterprise.pruebajikko.controller;


import org.apache.catalina.connector.Response;
import org.enterprise.pruebajikko.model.entity.Ciudad;
import org.enterprise.pruebajikko.model.entity.Departamento;
import org.enterprise.pruebajikko.model.entity.Pais;
import org.enterprise.pruebajikko.model.service.ICiudadService;
import org.enterprise.pruebajikko.model.service.IDepartamentoService;
import org.enterprise.pruebajikko.model.service.IPaisService;
import org.enterprise.pruebajikko.view.dto.CiudadDto;
import org.enterprise.pruebajikko.view.dto.DepartamentoDto;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.enterprise.pruebajikko.view.dto.PaisInfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PaisControllerImpl implements PaisController {

  private IPaisService paisService;

  private IDepartamentoService departamentoService;

  private ICiudadService ciudadService;

  public PaisControllerImpl(IPaisService paisService, IDepartamentoService departamentoService,
                            ICiudadService ciudadService) {
    this.paisService = paisService;
    this.departamentoService = departamentoService;
    this.ciudadService = ciudadService;
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

  @Override
  public ResponseEntity crearPais(PaisInfoDto paisInfoDto) {
    ResponseEntity responseEntity = null;
    try {
      validarPaisDto(paisInfoDto);

      if (paisService.findPais(paisInfoDto.getNombre()).isEmpty()) {
        Pais pais = Pais.builder()
          .nombre(paisInfoDto.getNombre())
          .moneda(paisInfoDto.getMoneda())
          .sigla(paisInfoDto.getSigla())
          .build();
        paisService.createPais(pais);
        if (paisInfoDto.getDepartamentos() != null && !paisInfoDto.getDepartamentos().isEmpty()) {
          crearDepartamento(paisInfoDto.getDepartamentos(), pais.getId());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("Info", "Pais creado correctamente");
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(body);
      } else {
        Map<String, Object> body = new HashMap<>();
        body.put("Error", "El pais ya existe");
        responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
      }
    } catch (Exception e) {
      Map<String, Object> body = new HashMap<>();
      body.put("Error", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    return responseEntity;
  }

  private void validarPaisDto(PaisInfoDto paisInfoDto) {
    if (paisInfoDto == null) {
      throw new IllegalArgumentException("El objeto paisInfoDto no puede ser nulo");
    }

    if (paisInfoDto.getNombre() == null || paisInfoDto.getNombre().isEmpty()) {
      throw new IllegalArgumentException("El nombre del pais no puede ser nulo o vacío");
    }

    if (paisInfoDto.getMoneda() == null || paisInfoDto.getMoneda().isEmpty()) {
      throw new IllegalArgumentException("La moneda del pais no puede ser nula o vacía");
    }

    if (paisInfoDto.getSigla() == null || paisInfoDto.getSigla().isEmpty()) {
      throw new IllegalArgumentException("La sigla del pais no puede ser nula o vacía");
    }

    if (paisInfoDto.getDepartamentos() == null || paisInfoDto.getDepartamentos().isEmpty()) {
      throw new IllegalArgumentException("El pais debe tener al menos un departamento");
    }

    for (DepartamentoDto departamentoDto : paisInfoDto.getDepartamentos()) {
      if (departamentoDto.getNombre() == null || departamentoDto.getNombre().isEmpty()) {
        throw new IllegalArgumentException("El nombre del departamento no puede ser nulo o vacío");
      }

      if (departamentoDto.getPoblacion() == null || departamentoDto.getPoblacion() <= 0) {
        throw new IllegalArgumentException("La población del departamento no puede ser nula o menor o igual a 0");
      }

      if (departamentoDto.getCiudades() == null || departamentoDto.getCiudades().isEmpty()) {
        throw new IllegalArgumentException("El departamento debe tener al menos una ciudad");
      }

      int validarCapital = 0;
      for (CiudadDto ciudadDto : departamentoDto.getCiudades()) {
        if (ciudadDto.getNombre() == null || ciudadDto.getNombre().isEmpty()) {
          throw new IllegalArgumentException("El nombre de la ciudad no puede ser nulo o vacío");
        }

        if (ciudadDto.isCapital()) {
          validarCapital++;
        }
      }

      if (validarCapital != 1) {
        throw new IllegalArgumentException("El pais debe tener una ciudad capital");
      }
    }
  }


  private void crearDepartamento(List<DepartamentoDto> departamentoDto, Integer paisId) throws Exception {
      for (DepartamentoDto departamento : departamentoDto) {
        Departamento departamentoEntity = Departamento.builder()
          .nombre(departamento.getNombre())
          .poblacion(departamento.getPoblacion())
          .idPais(Pais.builder().id(paisId).build())
          .build();
        departamentoService.crearDepartamento(departamentoEntity);
        if (departamento.getCiudades() != null && !departamento.getCiudades().isEmpty()) {
          crearCiudad(departamento.getCiudades(), departamentoEntity.getId());
        }
      }
  }

  private void crearCiudad(List<CiudadDto> ciudadDto, Integer departamentoId) throws Exception {
      for (CiudadDto ciudad : ciudadDto) {
        Ciudad ciudadEntity = Ciudad.builder()
          .nombre(ciudad.getNombre())
          .capital(ciudad.isCapital())
          .idDepartamento(Departamento.builder().id(departamentoId).build())
          .build();
        ciudadService.crearCiudad(ciudadEntity);
      }
  }

  @Override
  public ResponseEntity actualizarPais(PaisInfoDto paisInfoDto) {
    ResponseEntity responseEntity = null;
    try {
      validarPaisDto(paisInfoDto);
      if (paisService.findById(paisInfoDto.getId()) != null) {

        Pais pais = Pais.builder()
          .id(paisInfoDto.getId())
          .nombre(paisInfoDto.getNombre())
          .moneda(paisInfoDto.getMoneda())
          .sigla(paisInfoDto.getSigla())
          .build();
        paisService.updatePais(pais);

        if (paisInfoDto.getDepartamentos() != null && !paisInfoDto.getDepartamentos().isEmpty()) {
          actualizarDepartamento(paisInfoDto.getDepartamentos(), pais.getId());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("Info", "Pais actualizado correctamente");
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(body);
      }
    } catch (Exception e) {
      Map<String, Object> body = new HashMap<>();
      body.put("Error", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    return responseEntity;

  }

  private void actualizarDepartamento(List<DepartamentoDto> departamentoDto, Integer paisId) throws Exception {
    for (DepartamentoDto departamento : departamentoDto) {
      Departamento departamentoEntity = Departamento.builder()
        .id(departamento.getId())
        .nombre(departamento.getNombre())
        .poblacion(departamento.getPoblacion())
        .idPais(Pais.builder().id(paisId).build())
        .build();
      departamentoService.updateDepartamento(departamentoEntity);
      if (departamento.getCiudades() != null && !departamento.getCiudades().isEmpty()) {
        actualizarCiudad(departamento.getCiudades(), departamentoEntity.getId());
      }
    }
  }

  private void actualizarCiudad(List<CiudadDto> ciudadDto, Integer departamentoId) throws Exception {
    for (CiudadDto ciudad : ciudadDto) {
      Ciudad ciudadEntity = Ciudad.builder()
        .id(ciudad.getId())
        .nombre(ciudad.getNombre())
        .capital(ciudad.isCapital())
        .idDepartamento(Departamento.builder().id(departamentoId).build())
        .build();
      ciudadService.updateCiudad(ciudadEntity);
    }
  }

  @Override
  public ResponseEntity eliminarPais(Integer id) {
    ResponseEntity responseEntity = null;
    try {
      if (paisService.findById(id) != null) {
        eliminarDepartamento(id);
        paisService.deletePais(id);
        Map<String, Object> body = new HashMap<>();
        body.put("Info", "Pais eliminado correctamente");
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(body);
      } else {
        Map<String, Object> body = new HashMap<>();
        body.put("Error", "El pais no existe");
        responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
      }
    } catch (Exception e) {
      Map<String, Object> body = new HashMap<>();
      body.put("Error", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    return responseEntity;
  }

  private void eliminarDepartamento(Integer paisId) throws Exception {
    List<Departamento> departamentos = departamentoService.findDepartamentoByIdPais(paisId);
    for (Departamento departamento : departamentos) {
      eliminarCiudad(departamento.getId());
    }
    departamentoService.deleteDepartamentos(departamentos);
  }

  public void eliminarCiudad(Integer departamentoId) throws Exception {
    List<Ciudad> ciudades = ciudadService.findCiudadByIdDepartamento(departamentoId);
    if (ciudades != null && !ciudades.isEmpty()) {
      ciudadService.deleteCiudades(ciudades);
    }
  }
}
