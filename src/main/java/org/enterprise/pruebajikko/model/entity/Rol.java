package org.enterprise.pruebajikko.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rol")
public class Rol {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_id_gen")
  @SequenceGenerator(name = "rol_id_gen", sequenceName = "rol_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "habilitado", nullable = false)
  private Boolean habilitado = false;

  @OneToMany(mappedBy = "rol")
  private Set<Usuario> usuarios = new LinkedHashSet<>();

  public Rol(Integer idRol) {
  }
}