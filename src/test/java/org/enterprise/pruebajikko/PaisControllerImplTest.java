package org.enterprise.pruebajikko;

import org.enterprise.pruebajikko.controller.PaisControllerImpl;
import org.enterprise.pruebajikko.model.entity.Ciudad;
import org.enterprise.pruebajikko.model.entity.Pais;
import org.enterprise.pruebajikko.model.service.ICiudadService;
import org.enterprise.pruebajikko.model.service.IDepartamentoService;
import org.enterprise.pruebajikko.model.service.IPaisService;
import org.enterprise.pruebajikko.view.dto.CiudadDto;
import org.enterprise.pruebajikko.view.dto.DepartamentoDto;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.enterprise.pruebajikko.view.dto.PaisInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaisControllerImplTest {

  @InjectMocks
  PaisControllerImpl paisController;

  @Mock
  IPaisService paisService;

  @Mock
  IDepartamentoService departamentoService;

  @Mock
  ICiudadService ciudadService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void consultarPaisReturnsCorrectResponseOnValidInput() {
    PaisDto paisDto = PaisDto.builder()
      .nombre("Colombia")
      .moneda("COP")
      .poblacion(50000000L)
      .capital("Bogota").build();
    List<PaisDto> paisDtos = new ArrayList<>();
    paisDtos.add(paisDto);

    when(paisService.findPais("Colombia")).thenReturn(Arrays.asList(paisDto));

    ResponseEntity<List<PaisDto>> response = paisController.consultarPais("Colombia");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals("Colombia", response.getBody().get(0).getNombre());
  }

  @Test
  public void crearPaisReturnsCreatedOnValidInput() {
    PaisInfoDto paisInfoDto = new PaisInfoDto();
    paisInfoDto.setNombre("Colombia1");
    paisInfoDto.setMoneda("COP");
    paisInfoDto.setSigla("CO");
    paisInfoDto.setDepartamentos(new ArrayList<>());

    DepartamentoDto departamentoDto = new DepartamentoDto();
    departamentoDto.setNombre("Cundinamarca");
    departamentoDto.setPoblacion(10000000L);
    departamentoDto.setCiudades(new ArrayList<>());
    paisInfoDto.getDepartamentos().add(departamentoDto);

    CiudadDto ciudadDto = new CiudadDto();
    ciudadDto.setNombre("Bogota");
    ciudadDto.setCapital(true);

    paisInfoDto.getDepartamentos().get(0).getCiudades().add(ciudadDto);

    when(paisService.findPais(paisInfoDto.getNombre())).thenReturn(Collections.emptyList());

    ResponseEntity response = paisController.crearPais(paisInfoDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    verify(paisService, times(1)).createPais(any(Pais.class));
  }

  @Test
  public void crearPaisReturnsBadRequestWhenPaisExists() {
    PaisInfoDto paisInfoDto = new PaisInfoDto();
    paisInfoDto.setNombre("Colombia");
    paisInfoDto.setMoneda("COP");
    paisInfoDto.setSigla("CO");

    List<PaisDto> paisesDto = new ArrayList<>();
    paisesDto.add(PaisDto.builder().nombre("Colombia").build());

    when(paisService.findPais(paisInfoDto.getNombre())).thenReturn(paisesDto);

    ResponseEntity response = paisController.crearPais(paisInfoDto);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(paisService, never()).createPais(any(Pais.class));
  }

  @Test
  public void crearPaisReturnsBadRequest() {
    PaisInfoDto paisInfoDto = new PaisInfoDto();
    paisInfoDto.setNombre("Colombia");
    paisInfoDto.setMoneda("COP");
    paisInfoDto.setSigla("CO");

    when(paisService.findPais(paisInfoDto.getNombre())).thenReturn((List<PaisDto>) Arrays.asList(new PaisDto()));

    ResponseEntity response = paisController.crearPais(paisInfoDto);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(paisService, never()).createPais(any(Pais.class));
  }

  @Test
  public void crearPaisReturnsBadRequestOnException() {
    PaisInfoDto paisInfoDto = new PaisInfoDto();
    paisInfoDto.setNombre("Colombia");
    paisInfoDto.setMoneda("COP");
    paisInfoDto.setSigla("CO");

    when(paisService.findPais(paisInfoDto.getNombre())).thenThrow(new RuntimeException());

    ResponseEntity response = paisController.crearPais(paisInfoDto);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void actualizarPaisReturnsCreatedOnValidInput() {
    PaisInfoDto paisInfoDto = new PaisInfoDto();
    paisInfoDto.setId(1);
    paisInfoDto.setNombre("Colombia1");
    paisInfoDto.setMoneda("COP");
    paisInfoDto.setSigla("CO");
    paisInfoDto.setDepartamentos(new ArrayList<>());

    DepartamentoDto departamentoDto = new DepartamentoDto();
    departamentoDto.setId(1);
    departamentoDto.setNombre("Cundinamarca");
    departamentoDto.setPoblacion(10000000L);
    departamentoDto.setCiudades(new ArrayList<>());
    paisInfoDto.getDepartamentos().add(departamentoDto);

    CiudadDto ciudadDto = new CiudadDto();
    ciudadDto.setId(1);
    ciudadDto.setNombre("Bogota");
    ciudadDto.setCapital(true);
    paisInfoDto.getDepartamentos().get(0).getCiudades().add(ciudadDto);


    when(paisService.findById(paisInfoDto.getId())).thenReturn(new Pais());

    ResponseEntity response = paisController.actualizarPais(paisInfoDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    verify(paisService, times(1)).updatePais(any(Pais.class));
  }

  @Test
  public void actualizarPaisReturnsBadRequestWhenPaisDoesNotExist() {
    PaisInfoDto paisInfoDto = new PaisInfoDto();
    paisInfoDto.setId(1);
    paisInfoDto.setNombre("Colombia");
    paisInfoDto.setMoneda("COP");
    paisInfoDto.setSigla("CO");

    when(paisService.findById(paisInfoDto.getId())).thenReturn(null);

    ResponseEntity response = paisController.actualizarPais(paisInfoDto);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(paisService, never()).updatePais(any(Pais.class));
  }

  @Test
  public void actualizarPaisReturnsBadRequestOnException() {
    PaisInfoDto paisInfoDto = new PaisInfoDto();
    paisInfoDto.setId(1);
    paisInfoDto.setNombre("Colombia");
    paisInfoDto.setMoneda("COP");
    paisInfoDto.setSigla("CO");

    when(paisService.findById(paisInfoDto.getId())).thenThrow(new RuntimeException());

    ResponseEntity response = paisController.actualizarPais(paisInfoDto);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void eliminarPaisReturnsOkWhenPaisExists() {
    Integer id = 1;

    when(paisService.findById(id)).thenReturn(new Pais());

    ResponseEntity response = paisController.eliminarPais(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(paisService, times(1)).deletePais(id);
  }

  @Test
  public void eliminarPaisReturnsBadRequestWhenPaisDoesNotExist() {
    Integer id = 1;

    when(paisService.findById(id)).thenReturn(null);

    ResponseEntity response = paisController.eliminarPais(id);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(paisService, never()).deletePais(id);
  }

  @Test
  public void eliminarPaisReturnsBadRequestOnException() {
    Integer id = 1;

    when(paisService.findById(id)).thenThrow(new RuntimeException());

    ResponseEntity response = paisController.eliminarPais(id);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void eliminarCiudadDeletesCiudadesWhenPresent() throws Exception {
    Integer departamentoId = 1;
    List<Ciudad> ciudades = Arrays.asList(new Ciudad());

    when(ciudadService.findCiudadByIdDepartamento(departamentoId)).thenReturn(ciudades);

    paisController.eliminarCiudad(departamentoId);

    verify(ciudadService, times(1)).deleteCiudades(ciudades);
  }

  @Test
  public void eliminarCiudadDoesNotDeleteCiudadesWhenNonePresent() throws Exception {
    Integer departamentoId = 1;

    when(ciudadService.findCiudadByIdDepartamento(departamentoId)).thenReturn(Collections.emptyList());

    paisController.eliminarCiudad(departamentoId);

    verify(ciudadService, never()).deleteCiudades(any());
  }

  @Test
  public void eliminarCiudadThrowsExceptionWhenServiceFails() {
    Integer departamentoId = 1;

    when(ciudadService.findCiudadByIdDepartamento(departamentoId)).thenThrow(new RuntimeException());

    assertThrows(Exception.class, () -> paisController.eliminarCiudad(departamentoId));
  }
}
