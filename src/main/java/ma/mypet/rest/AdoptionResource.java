
package ma.mypet.rest;

import java.util.List;

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

import jakarta.validation.Valid;
import ma.mypet.model.AdoptionDTO;
import ma.mypet.service.AdoptionService;

@RestController
@RequestMapping(value = "/api/adoptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdoptionResource {

  private final AdoptionService adoptionService;

  public AdoptionResource(final AdoptionService adoptionService) {
    this.adoptionService = adoptionService;
  }

  @GetMapping
  public ResponseEntity<List<AdoptionDTO>> getAllAdoptions() {
    return ResponseEntity.ok(adoptionService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AdoptionDTO> getAdoption(@PathVariable final Long id) {
    return ResponseEntity.ok(adoptionService.get(id));
  }

  @PostMapping
  public ResponseEntity<Long> createAdoption(@RequestBody @Valid final AdoptionDTO adoptionDTO) {
    return new ResponseEntity<>(adoptionService.create(adoptionDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateAdoption(@PathVariable final Long id,
      @RequestBody @Valid final AdoptionDTO adoptionDTO) {
    adoptionService.update(id, adoptionDTO);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAdoption(@PathVariable final Long id) {
    adoptionService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
