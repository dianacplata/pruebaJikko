package org.enterprise.pruebajikko.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ciudad")
public class Ciudad {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ciudad_id_gen")
  @SequenceGenerator(name = "ciudad_id_gen", sequenceName = "ciudad_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "capital", nullable = false)
  private boolean capital;


  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_departamento", referencedColumnName = "id", nullable = false)
  private Departamento idDepartamento;

}