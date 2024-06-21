package org.enterprise.pruebajikko.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pais")
public class Pais {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais_id_gen")
  @SequenceGenerator(name = "pais_id_gen", sequenceName = "pais_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "sigla", nullable = false, length = 2)
  private String sigla;

  @Column(name = "moneda", nullable = false, length = 50)
  private String moneda;

  @OneToMany(mappedBy = "idPais")
  private List<Departamento> departamentos = new ArrayList<>();

}