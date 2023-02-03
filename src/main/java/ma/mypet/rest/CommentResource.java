package ma.mypet.rest;

import jakarta.validation.Valid;
import java.util.List;
import ma.mypet.model.CommentDTO;
import ma.mypet.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentResource {

  private final CommentService commentService;

  public CommentResource(final CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping
  public ResponseEntity<List<CommentDTO>> getAllComments() {
    return ResponseEntity.ok(commentService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommentDTO> getComment(@PathVariable final Long id) {
    return ResponseEntity.ok(commentService.get(id));
  }

  @PostMapping
  public ResponseEntity<Long> createComment(@RequestBody @Valid final CommentDTO commentDTO) {
    return new ResponseEntity<>(commentService.create(commentDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateComment(@PathVariable final Long id,
      @RequestBody @Valid final CommentDTO commentDTO) {
    commentService.update(id, commentDTO);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteComment(@PathVariable final Long id) {
    commentService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
