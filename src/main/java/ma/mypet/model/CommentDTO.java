package ma.mypet.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

  private Long id;
  private String comment;
  private Long adoption;
  private Long commenter;

}
