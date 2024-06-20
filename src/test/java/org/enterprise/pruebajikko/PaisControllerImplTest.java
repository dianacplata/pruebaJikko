package org.enterprise.pruebajikko;

import org.enterprise.pruebajikko.controller.PaisControllerImpl;
import org.enterprise.pruebajikko.model.service.IPaisService;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PaisControllerImplTest {

  @InjectMocks
  PaisControllerImpl paisController;

  @Mock
  IPaisService paisService;

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
      .ciudad("Bogota").build();
    List<PaisDto> paisDtos = new ArrayList<>();
    paisDtos.add(paisDto);

    when(paisService.findPais("Colombia")).thenReturn(Arrays.asList(paisDto));

    ResponseEntity<List<PaisDto>> response = paisController.consultarPais("Colombia");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals("Colombia", response.getBody().get(0).getNombre());
  }

  @Test
  public void consultarPaisReturnsInternalServerErrorOnException() {
    when(paisService.findPais("Colombia")).thenThrow(new RuntimeException());

    ResponseEntity<List<PaisDto>> response = paisController.consultarPais("Colombia");

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }
}
