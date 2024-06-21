package org.enterprise.pruebajikko.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departamento")
public class Departamento {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamento_id_gen")
  @SequenceGenerator(name = "departamento_id_gen", sequenceName = "departamento_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_pais", referencedColumnName = "id", nullable = false)
  private Pais idPais;

  @Column(name = "poblacion", nullable = false)
  private Long poblacion;

  @OneToMany(mappedBy = "idDepartamento")
  private List<Ciudad> ciudades = new ArrayList<>();

}