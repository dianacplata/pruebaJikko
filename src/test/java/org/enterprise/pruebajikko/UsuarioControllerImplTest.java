package org.enterprise.pruebajikko;

import org.enterprise.pruebajikko.controller.UsuarioControllerImpl;
import org.enterprise.pruebajikko.model.entity.Usuario;
import org.enterprise.pruebajikko.model.repository.UsuarioRepository;
import org.enterprise.pruebajikko.view.dto.CredencialDto;
import org.enterprise.pruebajikko.view.dto.UsuarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsuarioControllerImplTest {

    @InjectMocks
    UsuarioControllerImpl usuarioController;

    @Mock
    UsuarioRepository usuarioDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginReturnsOkWithValidCredentials() {
        CredencialDto credencial = new CredencialDto();
        credencial.setUsuario("testUser");
        credencial.setClave("testPassword");

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testUser");
        usuario.setClave("testPassword");

        when(usuarioDao.findByNombreUsuario("testUser")).thenReturn(usuario);

        ResponseEntity<CredencialDto> response = usuarioController.login(credencial);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void loginReturnsForbiddenWithInvalidCredentials() {
        CredencialDto credencial = new CredencialDto();
        credencial.setUsuario("testUser");
        credencial.setClave("wrongPassword");

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testUser");
        usuario.setClave("testPassword");

        when(usuarioDao.findByNombreUsuario("testUser")).thenReturn(usuario);

        ResponseEntity<CredencialDto> response = usuarioController.login(credencial);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void signupReturnsCreatedWhenUserDoesNotExist() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombreUsuario("testUser");
        usuarioDto.setClave("1234");

        when(usuarioDao.findByNombreUsuario("testUser")).thenReturn(null);

        ResponseEntity<Void> response = usuarioController.signup(usuarioDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(usuarioDao, times(1)).save(any(Usuario.class));
    }

    @Test
    public void signupReturnsConflictWhenUserExists() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombreUsuario("testUser");
        usuarioDto.setClave("1234");

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testUser");

        when(usuarioDao.findByNombreUsuario("testUser")).thenReturn(usuario);

        ResponseEntity<Void> response = usuarioController.signup(usuarioDto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}