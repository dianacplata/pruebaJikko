package org.enterprise.pruebajikko.controller;

import org.enterprise.pruebajikko.view.dto.CredencialDto;
import org.enterprise.pruebajikko.view.dto.UsuarioDto;
import org.springframework.http.ResponseEntity;

public interface UsuarioController {

  ResponseEntity<CredencialDto> login(CredencialDto credencial);

  ResponseEntity<Void> signup(UsuarioDto usuarioDto);
}
