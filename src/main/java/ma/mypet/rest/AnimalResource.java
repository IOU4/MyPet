package ma.mypet.rest;

import jakarta.validation.Valid;
import java.util.List;
import ma.mypet.model.AnimalDTO;
import ma.mypet.service.AnimalService;
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
@RequestMapping(value = "/api/animals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalResource {

    private final AnimalService animalService;

    public AnimalResource(final AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<List<AnimalDTO>> getAllAnimals() {
        return ResponseEntity.ok(animalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable final Long id) {
        return ResponseEntity.ok(animalService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAnimal(@RequestBody @Valid final AnimalDTO animalDTO) {
        return new ResponseEntity<>(animalService.create(animalDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnimal(@PathVariable final Long id,
            @RequestBody @Valid final AnimalDTO animalDTO) {
        animalService.update(id, animalDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable final Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
