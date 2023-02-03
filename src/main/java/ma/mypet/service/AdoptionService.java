package ma.mypet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ma.mypet.domain.Adoption;
import ma.mypet.domain.Animal;
import ma.mypet.domain.User;
import ma.mypet.model.AdoptionDTO;
import ma.mypet.model.AnimalDTO;
import ma.mypet.model.UserDTO;
import ma.mypet.repos.AdoptionRepository;
import ma.mypet.repos.AnimalRepository;
import ma.mypet.repos.UserRepository;
import ma.mypet.util.NotFoundException;

@Service
public class AdoptionService {

  private final AdoptionRepository adoptionRepository;
  // private final AnimalRepository animalRepository;

  @Autowired
  AnimalService animalService;
  @Autowired
  UserService userService;

  public AdoptionService(final AdoptionRepository adoptionRepository,
      final AnimalRepository animalRepository, final UserRepository userRepository) {
    this.adoptionRepository = adoptionRepository;
  }

  public List<AdoptionDTO> findAll() {
    final List<Adoption> adoptions = adoptionRepository.findAll(Sort.by("id"));
    return adoptions.stream()
        .map((adoption) -> mapToDTO(adoption, new AdoptionDTO()))
        .collect(Collectors.toList());
  }

  public AdoptionDTO get(final Long id) {
    return adoptionRepository.findById(id)
        .map(adoption -> mapToDTO(adoption, new AdoptionDTO()))
        .orElseThrow(() -> new NotFoundException());
  }

  public Long create(final AdoptionDTO adoptionDTO) {
    final Adoption adoption = new Adoption();
    mapToEntity(adoptionDTO, adoption);
    return adoptionRepository.save(adoption).getId();
  }

  public void update(final Long id, final AdoptionDTO adoptionDTO) {
    final Adoption adoption = adoptionRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    mapToEntity(adoptionDTO, adoption);
    adoptionRepository.save(adoption);
  }

  public void delete(final Long id) {
    adoptionRepository.deleteById(id);
  }

  private AdoptionDTO mapToDTO(final Adoption adoption, final AdoptionDTO adoptionDTO) {
    adoptionDTO.setId(adoption.getId());
    adoptionDTO.setDays(adoption.getDays());
    adoptionDTO.setDescription(adoption.getDescription());
    adoptionDTO
        .setAnimal(adoption.getAnimal() == null ? null : animalService.mapToDTO(adoption.getAnimal(), new AnimalDTO()));
    adoptionDTO
        .setAdopter(adoption.getAdopter() == null ? null : userService.mapToDTO(adoption.getAdopter(), new UserDTO()));
    return adoptionDTO;
  }

  private Adoption mapToEntity(final AdoptionDTO adoptionDTO, final Adoption adoption) {
    adoption.setDays(adoptionDTO.getDays());
    adoption.setDescription(adoptionDTO.getDescription());
    final Animal adoptedAnimal = adoptionDTO.getAnimal() == null ? null
        : animalService.mapToEntity(adoptionDTO.getAnimal(), new Animal());
    adoption.setAnimal(adoptedAnimal);
    final User adopter = adoptionDTO.getAdopter() == null ? null
        : userService.mapToEntity(adoptionDTO.getAdopter(), new User());
    adoption.setAdopter(adopter);
    return adoption;
  }

}
