package com.movievault.controller;

import com.movievault.dto.MovieDTO;
import com.movievault.model.*;
import com.movievault.repository.*;
import com.movievault.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final PersonRepository personRepository;
    private final MovieCastRepository movieCastRepository;
    private final MovieDirectorRepository movieDirectorRepository;

    public MovieController(MovieService movieService, MovieRepository movieRepository,
                           GenreRepository genreRepository, PersonRepository personRepository,
                           MovieCastRepository movieCastRepository, MovieDirectorRepository movieDirectorRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.personRepository = personRepository;
        this.movieCastRepository = movieCastRepository;
        this.movieDirectorRepository = movieDirectorRepository;
    }

    @GetMapping
    public ResponseEntity<?> getMovies(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long genreId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        String[] sortParts = sort.split(",");
        Sort sortObj = Sort.by(sortParts.length > 1 && sortParts[1].equals("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC, sortParts[0]);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<Movie> movies = movieService.findAll(search, genreId, pageable);
        Page<MovieDTO> dtoPage = movies.map(movieService::toDTO);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id) {
        try {
            Movie movie = movieService.findById(id);
            return ResponseEntity.ok(movieService.toDTO(movie));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/featured")
    public ResponseEntity<?> getFeatured() {
        List<MovieDTO> movies = movieService.getFeatured().stream()
                .map(movieService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<?> getTopRated() {
        List<MovieDTO> movies = movieService.getFeatured().stream()
                .map(movieService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatest() {
        List<MovieDTO> movies = movieService.getLatest(PageRequest.of(0, 10)).stream()
                .map(movieService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Map<String, Object> body) {
        Movie movie = new Movie();
        mapMovieFields(movie, body);
        Movie saved = movieRepository.save(movie);
        linkGenres(saved, body);
        linkDirectors(saved, body);
        linkCast(saved, body);
        return ResponseEntity.ok(movieService.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) return ResponseEntity.notFound().build();

        mapMovieFields(movie, body);
        movieRepository.save(movie);
        return ResponseEntity.ok(movieService.toDTO(movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Movie deleted"));
    }

    private void mapMovieFields(Movie movie, Map<String, Object> body) {
        if (body.containsKey("title")) movie.setTitle((String) body.get("title"));
        if (body.containsKey("originalTitle")) movie.setOriginalTitle((String) body.get("originalTitle"));
        if (body.containsKey("description")) movie.setDescription((String) body.get("description"));
        if (body.containsKey("duration")) movie.setDuration((Integer) body.get("duration"));
        if (body.containsKey("posterUrl")) movie.setPosterUrl((String) body.get("posterUrl"));
        if (body.containsKey("trailerUrl")) movie.setTrailerUrl((String) body.get("trailerUrl"));
        if (body.containsKey("country")) movie.setCountry((String) body.get("country"));
        if (body.containsKey("language")) movie.setLanguage((String) body.get("language"));
    }

    @SuppressWarnings("unchecked")
    private void linkGenres(Movie movie, Map<String, Object> body) {
        if (body.containsKey("genreIds")) {
            List<Integer> genreIds = (List<Integer>) body.get("genreIds");
            Set<Genre> genres = genreIds.stream()
                    .map(id -> genreRepository.findById(id.longValue()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            movie.setGenres(genres);
            movieRepository.save(movie);
        }
    }

    @SuppressWarnings("unchecked")
    private void linkDirectors(Movie movie, Map<String, Object> body) {
        if (body.containsKey("directorIds")) {
            List<Integer> directorIds = (List<Integer>) body.get("directorIds");
            for (Integer pid : directorIds) {
                personRepository.findById(pid.longValue()).ifPresent(person -> {
                    MovieDirector md = new MovieDirector();
                    md.setMovie(movie);
                    md.setPerson(person);
                    movieDirectorRepository.save(md);
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void linkCast(Movie movie, Map<String, Object> body) {
        if (body.containsKey("cast")) {
            List<Map<String, Object>> castList = (List<Map<String, Object>>) body.get("cast");
            for (Map<String, Object> c : castList) {
                Long personId = ((Number) c.get("personId")).longValue();
                personRepository.findById(personId).ifPresent(person -> {
                    MovieCast mc = new MovieCast();
                    mc.setMovie(movie);
                    mc.setPerson(person);
                    mc.setCharacterName((String) c.get("characterName"));
                    mc.setCastOrder(c.containsKey("castOrder") ? (Integer) c.get("castOrder") : 0);
                    movieCastRepository.save(mc);
                });
            }
        }
    }
}
