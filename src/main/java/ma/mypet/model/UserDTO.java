package ma.mypet.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.mypet.service.UserService;
import ma.mypet.util.NotFoundException;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

  private Long id;

  @NotNull
  @Size(max = 255)
  private String name;

  @NotNull
  @Size(max = 255)
  private String email;

  @NotNull
  @Size(max = 255)
  private String password;

  @NotNull
  @Size(max = 255)
  private String address;

  @NotNull
  @Size(max = 255)
  private String phone;

  @Autowired
  UserService userService;

  UserDTO(Long id) {
    var user = Optional.ofNullable(userService.get(id)).orElseThrow(() -> new NotFoundException("user not found"));
    this.id = id;
    this.phone = user.getPhone();
    this.address = user.getAddress();
    this.email = user.getEmail();
    this.name = user.getName();
    this.password = user.getPassword();
  }

}
