package org.enterprise.pruebajikko.view.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CiudadDto {

  private Integer id;
  private String nombre;
  private Integer idDepartamento;
  private boolean capital;

}
