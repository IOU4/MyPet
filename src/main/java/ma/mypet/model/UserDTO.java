package ma.mypet.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  private String address;

  @NotNull
  @Size(max = 255)
  private String phone;
}
