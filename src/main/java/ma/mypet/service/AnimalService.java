package ma.mypet.service;

import java.util.List;
import java.util.stream.Collectors;
import ma.mypet.domain.Animal;
import ma.mypet.domain.User;
import ma.mypet.model.AnimalDTO;
import ma.mypet.repos.AnimalRepository;
import ma.mypet.repos.UserRepository;
import ma.mypet.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    public AnimalService(final AnimalRepository animalRepository,
            final UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

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

    private AnimalDTO mapToDTO(final Animal animal, final AnimalDTO animalDTO) {
        animalDTO.setId(animal.getId());
        animalDTO.setType(animal.getType());
        animalDTO.setAge(animal.getAge());
        animalDTO.setAnimalOwner(animal.getAnimalOwner() == null ? null : animal.getAnimalOwner().getId());
        return animalDTO;
    }

    private Animal mapToEntity(final AnimalDTO animalDTO, final Animal animal) {
        animal.setType(animalDTO.getType());
        animal.setAge(animalDTO.getAge());
        final User animalOwner = animalDTO.getAnimalOwner() == null ? null : userRepository.findById(animalDTO.getAnimalOwner())
                .orElseThrow(() -> new NotFoundException("animalOwner not found"));
        animal.setAnimalOwner(animalOwner);
        return animal;
    }

}
