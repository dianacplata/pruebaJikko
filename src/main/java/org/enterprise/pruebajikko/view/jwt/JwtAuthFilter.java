package org.enterprise.pruebajikko.view.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.enterprise.pruebajikko.model.service.UsuarioServiceImpl;
import org.enterprise.pruebajikko.view.security.UtilSecurity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final UsuarioServiceImpl usuarioService;
  private final ObjectMapper objectMapper;

  public JwtAuthFilter(UsuarioServiceImpl usuarioService, ObjectMapper objectMapper) {
    this.usuarioService = usuarioService;
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    try {
      String authHeader = request.getHeader("Authorization");
      String token = null;
      String username = null;
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        username = UtilSecurity.extractUsername(token);
      }
//      If the accessToken is null. It will pass the request to next filter in the chain.
//      Any login and signup requests will not have jwt token in their header, therefore they will be passed to next filter chain.
      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }
//       If any accessToken is present, then it will validate the token and then authenticate the request in security context
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails usuario = usuarioService.loadUserByUsername(username);
        if (Boolean.TRUE.equals(UtilSecurity.validateToken(token, usuario))) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, null);
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }

      filterChain.doFilter(request, response);
    } catch (AccessDeniedException e) {
      Response errorResponse = new Response();
      errorResponse.setMessage(e.getMessage());
      errorResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getWriter().write(toJson(errorResponse));
    }
  }

  private String toJson(Response response) {
    try {
      return objectMapper.writeValueAsString(response);
    } catch (Exception e) {
      return ""; // Return an empty string if serialization fails
    }
  }
}
