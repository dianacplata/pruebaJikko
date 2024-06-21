package org.enterprise.pruebajikko.view.dto;


import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaisInfoDto {

  private Integer id;
  private String nombre;
  private String sigla;
  private String moneda;
  private List<DepartamentoDto> departamentos;
}
