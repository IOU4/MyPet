package ma.mypet.rest;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.mypet.service.TokenService;

@RestController
@RequestMapping(value = "/api/token")
@CrossOrigin(origins = "*")
public class TokenReousrce {

  private final TokenService tokenService;

  public TokenReousrce(final TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public String getToken(Authentication authentication) {
    return tokenService.generateToken(authentication);
  }
}
