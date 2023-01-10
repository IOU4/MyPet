package ma.mypet.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AnimalDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String type;

    @NotNull
    private Integer age;

    private Long animalOwner;

}
