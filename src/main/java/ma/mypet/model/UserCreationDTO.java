package ma.mypet.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDTO {

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

}
