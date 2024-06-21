package org.enterprise.pruebajikko.model.repository;

import org.enterprise.pruebajikko.model.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>  {

  @Query(value = "select u from Usuario u where u.nombreUsuario = ?1 and u.habilitado = true")
  Usuario findByNombreUsuario(String usuario);
}
