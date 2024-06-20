package org.enterprise.pruebajikko.view.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaisDto {

  private String nombre;
  private String moneda;
  private Long poblacion;
  private String ciudad;
}
