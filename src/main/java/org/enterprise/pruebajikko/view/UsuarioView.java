package org.enterprise.pruebajikko.view;

import jakarta.validation.Valid;
import org.enterprise.pruebajikko.controller.UsuarioController;
import org.enterprise.pruebajikko.view.dto.CredencialDto;
import org.enterprise.pruebajikko.view.dto.UsuarioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UsuarioView {

  private final UsuarioController usuarioController;

  public UsuarioView(UsuarioController usuarioController) {
    this.usuarioController = usuarioController;
  }

  @PostMapping("/user/login")
  public ResponseEntity<CredencialDto> login(@Valid @RequestBody CredencialDto credencial) {
    return usuarioController.login(credencial);
  }

  @PostMapping("/user/signup")
  public ResponseEntity<Void> signup(@Valid @RequestBody UsuarioDto usuarioDto) {
    return usuarioController.signup(usuarioDto);
  }
}
