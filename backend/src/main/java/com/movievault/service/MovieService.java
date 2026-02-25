package com.movievault.service;

import com.movievault.dto.CastMemberDTO;
import com.movievault.dto.MovieDTO;
import com.movievault.dto.PersonDTO;
import com.movievault.model.*;
import com.movievault.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieCastRepository movieCastRepository;
    private final MovieDirectorRepository movieDirectorRepository;
    private final ReviewRepository reviewRepository;

    public MovieService(MovieRepository movieRepository, MovieCastRepository movieCastRepository,
                        MovieDirectorRepository movieDirectorRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.movieCastRepository = movieCastRepository;
        this.movieDirectorRepository = movieDirectorRepository;
        this.reviewRepository = reviewRepository;
    }

    public Page<Movie> findAll(String search, Long genreId, Pageable pageable) {
        if (search != null && !search.isEmpty() && genreId != null) {
            return movieRepository.findByTitleContainingIgnoreCaseAndGenreId(search, genreId, pageable);
        } else if (search != null && !search.isEmpty()) {
            return movieRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else if (genreId != null) {
            return movieRepository.findByGenreId(genreId, pageable);
        }
        return movieRepository.findAll(pageable);
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie update(Long id, Movie updatedMovie) {
        Movie movie = findById(id);
        movie.setTitle(updatedMovie.getTitle());
        movie.setOriginalTitle(updatedMovie.getOriginalTitle());
        movie.setDescription(updatedMovie.getDescription());
        movie.setReleaseDate(updatedMovie.getReleaseDate());
        movie.setDuration(updatedMovie.getDuration());
        movie.setPosterUrl(updatedMovie.getPosterUrl());
        movie.setTrailerUrl(updatedMovie.getTrailerUrl());
        movie.setCountry(updatedMovie.getCountry());
        movie.setLanguage(updatedMovie.getLanguage());
        movie.setBudget(updatedMovie.getBudget());
        movie.setBoxOffice(updatedMovie.getBoxOffice());
        if (updatedMovie.getGenres() != null) {
            movie.setGenres(updatedMovie.getGenres());
        }
        return movieRepository.save(movie);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    public void recalculateRatings(Long movieId) {
        Movie movie = findById(movieId);
        Double avgUser = reviewRepository.getAverageUserRatingByMovieId(movieId);
        Double avgCritic = reviewRepository.getAverageCriticRatingByMovieId(movieId);
        movie.setAverageUserRating(avgUser != null ? Math.round(avgUser * 10.0) / 10.0 : 0.0);
        movie.setAverageCriticRating(avgCritic != null ? Math.round(avgCritic * 10.0) / 10.0 : 0.0);
        movieRepository.save(movie);
    }

    public List<Movie> getFeatured() {
        return movieRepository.findTop10ByOrderByAverageUserRatingDesc();
    }

    public List<Movie> getLatest(Pageable pageable) {
        return movieRepository.findLatest(pageable);
    }

    public MovieDTO toDTO(Movie movie) {
        List<MovieCast> castList = movieCastRepository.findByMovieIdOrderByCastOrderAsc(movie.getId());
        List<MovieDirector> directorList = movieDirectorRepository.findByMovieId(movie.getId());
        long reviewCount = reviewRepository.countByMovieId(movie.getId());

        List<String> genres = movie.getGenres().stream()
                .map(g -> g.getName().name())
                .collect(Collectors.toList());

        List<CastMemberDTO> cast = castList.stream()
                .map(c -> new CastMemberDTO(
                        c.getPerson().getId(),
                        c.getPerson().getFirstName(),
                        c.getPerson().getLastName(),
                        c.getPerson().getPhotoUrl(),
                        c.getCharacterName(),
                        c.getCastOrder()))
                .collect(Collectors.toList());

        List<PersonDTO> directors = directorList.stream()
                .map(d -> new PersonDTO(
                        d.getPerson().getId(),
                        d.getPerson().getFirstName(),
                        d.getPerson().getLastName(),
                        d.getPerson().getBirthDate(),
                        d.getPerson().getBirthPlace(),
                        d.getPerson().getBio(),
                        d.getPerson().getPhotoUrl()))
                .collect(Collectors.toList());

        return new MovieDTO(
                movie.getId(), movie.getTitle(), movie.getOriginalTitle(),
                movie.getDescription(), movie.getReleaseDate(), movie.getDuration(),
                movie.getPosterUrl(), movie.getTrailerUrl(), movie.getCountry(),
                movie.getLanguage(), movie.getBudget(), movie.getBoxOffice(),
                movie.getAverageUserRating(), movie.getAverageCriticRating(),
                genres, cast, directors, reviewCount
        );
    }
}
