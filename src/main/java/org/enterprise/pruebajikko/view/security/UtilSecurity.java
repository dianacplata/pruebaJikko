package org.enterprise.pruebajikko.view.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class UtilSecurity {

  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final int MINUTES = 60;
  private static MessageDigest passwordEncoder;

  public static String generateToken(String user) {
    var now = Instant.now();
    return Jwts.builder()
      .subject(user)
      .issuedAt(Date.from(now))
      .expiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
      .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
      .compact();
  }

  public static String extractUsername(String token) throws AccessDeniedException {
    return getTokenBody(token).getSubject();
  }

  public static boolean validateToken(String token, UserDetails userDetails) throws AccessDeniedException {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private static Claims getTokenBody(String token) throws AccessDeniedException {
    try {
      return Jwts
        .parser()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    } catch (ExpiredJwtException e) { // Invalid signature or expired token
      throw new AccessDeniedException("Access denied: " + e.getMessage());
    }
  }

  private static boolean isTokenExpired(String token) throws AccessDeniedException {
    Claims claims = getTokenBody(token);
    return claims.getExpiration().before(new Date());
  }

  public static String encriptar(String clave) {
    try {
      passwordEncoder = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = passwordEncoder.digest(clave.getBytes());
      return toHexString(encodedHash);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private static String toHexString(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

}
