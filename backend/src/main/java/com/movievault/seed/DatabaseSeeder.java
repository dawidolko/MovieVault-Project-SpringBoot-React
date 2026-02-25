package com.movievault.seed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Value("${app.db.seed:false}")
    private boolean seedEnabled;

    private final RoleSeeder roleSeeder;
    private final GenreSeeder genreSeeder;
    private final UserSeeder userSeeder;
    private final PersonSeeder personSeeder;
    private final MovieSeeder movieSeeder;
    private final ReviewSeeder reviewSeeder;

    public DatabaseSeeder(RoleSeeder roleSeeder, GenreSeeder genreSeeder,
                          UserSeeder userSeeder, PersonSeeder personSeeder,
                          MovieSeeder movieSeeder, ReviewSeeder reviewSeeder) {
        this.roleSeeder = roleSeeder;
        this.genreSeeder = genreSeeder;
        this.userSeeder = userSeeder;
        this.personSeeder = personSeeder;
        this.movieSeeder = movieSeeder;
        this.reviewSeeder = reviewSeeder;
    }

    @Override
    public void run(String... args) {
        if (!seedEnabled) {
            System.out.println("Seeding disabled (app.db.seed=false)");
            return;
        }

        System.out.println("Starting database seeding...");

        roleSeeder.seed();
        genreSeeder.seed();
        userSeeder.seed();
        personSeeder.seed();
        movieSeeder.seed();
        reviewSeeder.seed();

        System.out.println("Database seeding completed.");
    }
}
