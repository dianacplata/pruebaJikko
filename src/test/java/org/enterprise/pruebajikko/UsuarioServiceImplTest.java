package org.enterprise.pruebajikko;

import org.enterprise.pruebajikko.model.entity.Usuario;
import org.enterprise.pruebajikko.model.repository.UsuarioRepository;
import org.enterprise.pruebajikko.model.service.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UsuarioServiceImplTest {

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsernameReturnsUserDetailsWhenUserExists() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testUser");
        usuario.setClave("testPassword");

        when(usuarioRepository.findByNombreUsuario("testUser")).thenReturn(usuario);

        UserDetails userDetails = usuarioService.loadUserByUsername("testUser");

        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
    }

    @Test
    public void loadUserByUsernameThrowsExceptionWhenUserDoesNotExist() {
        when(usuarioRepository.findByNombreUsuario("testUser")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> usuarioService.loadUserByUsername("testUser"));
    }

}