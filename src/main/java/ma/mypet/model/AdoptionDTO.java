package ma.mypet.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptionDTO {

  private Long id;

  @NotNull
  private Integer days;

  private String description;

  private AnimalDTO animal;

  private UserDTO adopter;

}
