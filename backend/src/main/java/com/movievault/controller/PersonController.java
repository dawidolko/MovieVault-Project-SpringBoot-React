package com.movievault.controller;

import com.movievault.dto.MovieDTO;
import com.movievault.model.Person;
import com.movievault.repository.MovieCastRepository;
import com.movievault.repository.MovieDirectorRepository;
import com.movievault.service.MovieService;
import com.movievault.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;
    private final MovieCastRepository movieCastRepository;
    private final MovieDirectorRepository movieDirectorRepository;
    private final MovieService movieService;

    public PersonController(PersonService personService, MovieCastRepository movieCastRepository,
                            MovieDirectorRepository movieDirectorRepository, MovieService movieService) {
        this.personService = personService;
        this.movieCastRepository = movieCastRepository;
        this.movieDirectorRepository = movieDirectorRepository;
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPerson(@PathVariable Long id) {
        return personService.findById(id).map(person -> {
            Map<String, Object> response = new HashMap<>();
            response.put("person", person);

            List<MovieDTO> actedIn = movieCastRepository.findByPersonId(id).stream()
                    .map(mc -> movieService.toDTO(mc.getMovie()))
                    .collect(Collectors.toList());
            response.put("actedIn", actedIn);

            List<MovieDTO> directed = movieDirectorRepository.findByPersonId(id).stream()
                    .map(md -> movieService.toDTO(md.getMovie()))
                    .collect(Collectors.toList());
            response.put("directed", directed);

            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name) {
        return ResponseEntity.ok(personService.search(name));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Person person) {
        return ResponseEntity.ok(personService.create(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Person person) {
        return ResponseEntity.ok(personService.update(id, person));
    }
}
