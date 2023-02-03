package ma.mypet.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ma.mypet.model.UserDTO;

@Service
public class TokenService {

  public TokenService() {
  }

  @Autowired
  JwtEncoder jwtEncoder;

  @Autowired
  UserService userService;

  @Autowired
  ObjectMapper objectMapper;

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    long expiry = 36000L;
    String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject(authentication.getName())
        .claim("scope", scope)
        .build();
    var token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    try {
      var json = new HashMap<String, Object>();
      json.put("token", token);
      json.put("user", getAuthenticatedUser());
      var obj = objectMapper.writeValueAsString(json);
      return token;
    } catch (JsonProcessingException ex) {
      return "error generating token";
    }
  }

  private UserDTO getAuthenticatedUser() {
    var principale = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principale instanceof UserDetails) {
      return userService.findByEmail(((UserDetails) principale).getUsername());
    }
    return userService.findByEmail(principale.toString());
  }

}
