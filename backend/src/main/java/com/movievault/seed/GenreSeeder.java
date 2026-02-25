package com.movievault.seed;

import com.movievault.model.Genre;
import com.movievault.model.GenreName;
import com.movievault.repository.GenreRepository;
import org.springframework.stereotype.Component;

@Component
public class GenreSeeder {

    private final GenreRepository genreRepository;

    public GenreSeeder(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void seed() {
        for (GenreName genreName : GenreName.values()) {
            if (genreRepository.findByName(genreName).isEmpty()) {
                genreRepository.save(new Genre(genreName));
                System.out.println("Seeded genre: " + genreName);
            }
        }
    }
}
