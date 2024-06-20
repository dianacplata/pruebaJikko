package org.enterprise.pruebajikko.controller;

import org.enterprise.pruebajikko.model.entity.Rol;
import org.enterprise.pruebajikko.model.entity.Usuario;
import org.enterprise.pruebajikko.model.repository.UsuarioRepository;
import org.enterprise.pruebajikko.view.dto.CredencialDto;
import org.enterprise.pruebajikko.view.dto.UsuarioDto;
import org.enterprise.pruebajikko.view.security.UtilSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioControllerImpl implements UsuarioController {

  private UsuarioRepository usuarioDao;

  @Autowired
  public UsuarioControllerImpl(UsuarioRepository usuarioDao) {
    this.usuarioDao = usuarioDao;
  }

  @Override
  public ResponseEntity<CredencialDto> login(CredencialDto credencial) {
    ResponseEntity<CredencialDto> responseEntity;
    Usuario usuario = usuarioDao.findByNombreUsuario(credencial.getUsuario());
    if (credencial.getClave().equals(usuario.getClave())) {
      String token = UtilSecurity.generateToken(credencial.getUsuario());
      responseEntity = ResponseEntity.status(HttpStatus.OK).body(CredencialDto.builder().usuario(credencial.getUsuario()).token(token).build());
      return responseEntity;
    } else {
      responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      return responseEntity;
    }
  }

  @Override
  public ResponseEntity<Void> signup(UsuarioDto usuarioDto) {
    ResponseEntity<Void> responseEntity;
    Usuario usuario = usuarioDao.findByNombreUsuario(usuarioDto.getNombreUsuario());
    if (usuario == null) {
      Rol rol = new Rol();
      rol.setId(usuarioDto.getIdRol());
      usuario = Usuario.builder()
        .nombreUsuario(usuarioDto.getNombreUsuario())
        .nombre(usuarioDto.getNombre())
        .apellido(usuarioDto.getApellido())
        .email(usuarioDto.getEmail())
        .clave(UtilSecurity.encriptar(usuarioDto.getClave()))
        .habilitado(Boolean.TRUE)
        .rol(rol)
        .build();
      usuarioDao.save(usuario);
      responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).build();
      return responseEntity;
    }
    return responseEntity;
  }
}
