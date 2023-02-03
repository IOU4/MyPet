package ma.mypet.service;

import java.util.List;
import java.util.stream.Collectors;
import ma.mypet.domain.Adoption;
import ma.mypet.domain.Comment;
import ma.mypet.domain.User;
import ma.mypet.model.CommentDTO;
import ma.mypet.repos.AdoptionRepository;
import ma.mypet.repos.CommentRepository;
import ma.mypet.repos.UserRepository;
import ma.mypet.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final AdoptionRepository adoptionRepository;
  private final UserRepository userRepository;

  public CommentService(final CommentRepository commentRepository,
      final AdoptionRepository adoptionRepository, final UserRepository userRepository) {
    this.commentRepository = commentRepository;
    this.adoptionRepository = adoptionRepository;
    this.userRepository = userRepository;
  }

  public List<CommentDTO> findAll() {
    final List<Comment> comments = commentRepository.findAll(Sort.by("id"));
    return comments.stream()
        .map((comment) -> mapToDTO(comment, new CommentDTO()))
        .collect(Collectors.toList());
  }

  public CommentDTO get(final Long id) {
    return commentRepository.findById(id)
        .map(comment -> mapToDTO(comment, new CommentDTO()))
        .orElseThrow(() -> new NotFoundException());
  }

  public Long create(final CommentDTO commentDTO) {
    final Comment comment = new Comment();
    mapToEntity(commentDTO, comment);
    return commentRepository.save(comment).getId();
  }

  public void update(final Long id, final CommentDTO commentDTO) {
    final Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    mapToEntity(commentDTO, comment);
    commentRepository.save(comment);
  }

  public void delete(final Long id) {
    commentRepository.deleteById(id);
  }

  private CommentDTO mapToDTO(final Comment comment, final CommentDTO commentDTO) {
    commentDTO.setId(comment.getId());
    commentDTO.setComment(comment.getComment());
    commentDTO.setAdoption(comment.getAdoption() == null ? null : comment.getAdoption().getId());
    commentDTO.setCommenter(comment.getCommenter() == null ? null : comment.getCommenter().getId());
    return commentDTO;
  }

  private Comment mapToEntity(final CommentDTO commentDTO, final Comment comment) {
    comment.setComment(commentDTO.getComment());
    final Adoption adoption = commentDTO.getAdoption() == null ? null
        : adoptionRepository.findById(commentDTO.getAdoption())
            .orElseThrow(() -> new NotFoundException("adoption not found"));
    comment.setAdoption(adoption);
    final User commenter = commentDTO.getCommenter() == null ? null
        : userRepository.findById(commentDTO.getCommenter())
            .orElseThrow(() -> new NotFoundException("commenter not found"));
    comment.setCommenter(commenter);
    return comment;
  }

}
