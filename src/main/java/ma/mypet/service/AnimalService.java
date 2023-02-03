package ma.mypet.service;

import java.util.List;
import java.util.stream.Collectors;
import ma.mypet.domain.Animal;
import ma.mypet.domain.User;
import ma.mypet.model.AnimalDTO;
import ma.mypet.model.UserDTO;
import ma.mypet.repos.AnimalRepository;
import ma.mypet.repos.UserRepository;
import ma.mypet.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

  private final AnimalRepository animalRepository;

  public AnimalService(final AnimalRepository animalRepository,
      final UserRepository userRepository) {
    this.animalRepository = animalRepository;
  }

  @Autowired
  UserService userService;

  public List<AnimalDTO> findAll() {
    final List<Animal> animals = animalRepository.findAll(Sort.by("id"));
    return animals.stream()
        .map((animal) -> mapToDTO(animal, new AnimalDTO()))
        .collect(Collectors.toList());
  }

  public AnimalDTO get(final Long id) {
    return animalRepository.findById(id)
        .map(animal -> mapToDTO(animal, new AnimalDTO()))
        .orElseThrow(() -> new NotFoundException());
  }

  public Long create(final AnimalDTO animalDTO) {
    final Animal animal = new Animal();
    mapToEntity(animalDTO, animal);
    return animalRepository.save(animal).getId();
  }

  public void update(final Long id, final AnimalDTO animalDTO) {
    final Animal animal = animalRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    mapToEntity(animalDTO, animal);
    animalRepository.save(animal);
  }

  public void delete(final Long id) {
    animalRepository.deleteById(id);
  }

  public AnimalDTO mapToDTO(final Animal animal, final AnimalDTO animalDTO) {
    animalDTO.setId(animal.getId());
    animalDTO.setType(animal.getType());
    animalDTO.setAge(animal.getAge());
    animalDTO.setOwner(animal.getOwner() == null ? null : userService.mapToDTO(animal.getOwner(), new UserDTO()));
    return animalDTO;
  }

  public Animal mapToEntity(final AnimalDTO animalDTO, final Animal animal) {
    animal.setType(animalDTO.getType());
    animal.setAge(animalDTO.getAge());
    final User animalOwner = animalDTO.getOwner() == null ? null
        : userService.mapToEntity(animalDTO.getOwner(), new User());
    animal.setOwner(animalOwner);
    return animal;
  }

}
