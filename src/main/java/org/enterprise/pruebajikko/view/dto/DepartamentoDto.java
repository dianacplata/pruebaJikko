package org.enterprise.pruebajikko.view.dto;


import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoDto {

  private Integer id;
  private String nombre;
  private Integer idPais;
  private Long poblacion;
  private List<CiudadDto> ciudades;

}
