package ma.mypet.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.mypet.service.TokenService;

@RestController
@RequestMapping(value = "/api/token")
public class TokenReousrce {

  private final TokenService tokenService;

  private static Logger logger = LoggerFactory.getLogger(TokenService.class);

  public TokenReousrce(final TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @GetMapping
  public String getToken(Authentication authentication) {
    logger.info("from token resource");
    return tokenService.generateToken(authentication);
  }
}
