package org.enterprise.pruebajikko.model.service;


import org.enterprise.pruebajikko.model.entity.Usuario;
import org.enterprise.pruebajikko.model.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;


@Service
public class UsuarioServiceImpl implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;

  public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByNombreUsuario(username);
    return new User(usuario.getNombreUsuario(), usuario.getClave(), new ArrayList<>());
  }
}
