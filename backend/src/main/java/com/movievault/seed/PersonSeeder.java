package com.movievault.seed;

import com.movievault.model.Person;
import com.movievault.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PersonSeeder {

    private final PersonRepository personRepository;

    public PersonSeeder(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void seed() {
        List<Person> all = personRepository.findAll();

        // Directors
        create(all, "Frank", "Darabont", LocalDate.of(1959, 1, 28), "Montbeliard, France", "Hungarian-American film director and screenwriter.");
        create(all, "Francis", "Ford Coppola", LocalDate.of(1939, 4, 7), "Detroit, Michigan, USA", "Legendary filmmaker behind The Godfather trilogy.");
        create(all, "Christopher", "Nolan", LocalDate.of(1970, 7, 30), "London, England", "British-American filmmaker known for complex narratives.");
        create(all, "Sidney", "Lumet", LocalDate.of(1924, 6, 25), "Philadelphia, Pennsylvania, USA", "American director known for courtroom dramas.");
        create(all, "Steven", "Spielberg", LocalDate.of(1946, 12, 18), "Cincinnati, Ohio, USA", "One of the most influential directors in cinema history.");
        create(all, "Peter", "Jackson", LocalDate.of(1961, 10, 31), "Pukerua Bay, New Zealand", "New Zealand director of The Lord of the Rings trilogy.");
        create(all, "Quentin", "Tarantino", LocalDate.of(1963, 3, 27), "Knoxville, Tennessee, USA", "American filmmaker known for stylized violence and sharp dialogue.");
        create(all, "David", "Fincher", LocalDate.of(1962, 8, 28), "Denver, Colorado, USA", "American director known for dark psychological thrillers.");
        create(all, "Robert", "Zemeckis", LocalDate.of(1951, 5, 14), "Chicago, Illinois, USA", "American director known for visual effects innovation.");
        create(all, "Lana", "Wachowski", LocalDate.of(1965, 6, 21), "Chicago, Illinois, USA", "American filmmaker who co-created The Matrix franchise.");
        create(all, "Martin", "Scorsese", LocalDate.of(1942, 11, 17), "New York City, USA", "Legendary American filmmaker and film historian.");
        create(all, "Ridley", "Scott", LocalDate.of(1937, 11, 30), "South Shields, England", "British filmmaker known for visually stunning sci-fi films.");
        create(all, "Milos", "Forman", LocalDate.of(1932, 2, 18), "Caslav, Czechoslovakia", "Czech-American director of One Flew Over the Cuckoo's Nest.");
        create(all, "James", "Cameron", LocalDate.of(1954, 8, 16), "Kapuskasing, Ontario, Canada", "Canadian filmmaker and pioneer of visual effects.");
        create(all, "Sergio", "Leone", LocalDate.of(1929, 1, 3), "Rome, Italy", "Italian director who created the Spaghetti Western genre.");
        create(all, "Bong", "Joon-ho", LocalDate.of(1969, 9, 14), "Daegu, South Korea", "South Korean filmmaker known for genre-bending stories.");
        create(all, "Denis", "Villeneuve", LocalDate.of(1967, 10, 3), "Gentilly, Quebec, Canada", "Canadian director known for thoughtful sci-fi epics.");
        create(all, "Todd", "Phillips", LocalDate.of(1970, 12, 20), "Brooklyn, New York, USA", "American filmmaker behind Joker and The Hangover.");
        create(all, "Damien", "Chazelle", LocalDate.of(1985, 1, 19), "Providence, Rhode Island, USA", "Youngest Best Director Oscar winner for La La Land.");
        create(all, "George", "Lucas", LocalDate.of(1944, 5, 14), "Modesto, California, USA", "Creator of Star Wars and Indiana Jones.");

        // Actors
        create(all, "Tim", "Robbins", LocalDate.of(1958, 10, 16), "West Covina, California, USA", "American actor and filmmaker known for Shawshank.");
        create(all, "Morgan", "Freeman", LocalDate.of(1937, 6, 1), "Memphis, Tennessee, USA", "Iconic American actor with distinctive voice.");
        create(all, "Marlon", "Brando", LocalDate.of(1924, 4, 3), "Omaha, Nebraska, USA", "Revolutionary actor who transformed modern cinema.");
        create(all, "Al", "Pacino", LocalDate.of(1940, 4, 25), "New York City, USA", "Legendary actor known for intense performances.");
        create(all, "Christian", "Bale", LocalDate.of(1974, 1, 30), "Haverfordwest, Wales", "British actor known for extreme physical transformations.");
        create(all, "Heath", "Ledger", LocalDate.of(1979, 4, 4), "Perth, Australia", "Australian actor whose Joker became iconic.");
        create(all, "Henry", "Fonda", LocalDate.of(1905, 5, 16), "Grand Island, Nebraska, USA", "American film legend and patriarch of acting dynasty.");
        create(all, "Liam", "Neeson", LocalDate.of(1952, 6, 7), "Ballymena, Northern Ireland", "Irish actor known for dramatic and action roles.");
        create(all, "Ralph", "Fiennes", LocalDate.of(1962, 12, 22), "Ipswich, England", "British actor of stage and screen.");
        create(all, "Elijah", "Wood", LocalDate.of(1981, 1, 28), "Cedar Rapids, Iowa, USA", "American actor best known as Frodo Baggins.");
        create(all, "John", "Travolta", LocalDate.of(1954, 2, 18), "Englewood, New Jersey, USA", "American actor and cultural icon.");
        create(all, "Samuel L.", "Jackson", LocalDate.of(1948, 12, 21), "Washington, D.C., USA", "One of the highest-grossing actors of all time.");
        create(all, "Leonardo", "DiCaprio", LocalDate.of(1974, 11, 11), "Los Angeles, California, USA", "Oscar-winning actor and environmental activist.");
        create(all, "Brad", "Pitt", LocalDate.of(1963, 12, 18), "Shawnee, Oklahoma, USA", "American actor and film producer.");
        create(all, "Tom", "Hanks", LocalDate.of(1956, 7, 9), "Concord, California, USA", "Two-time Oscar winner beloved worldwide.");
        create(all, "Keanu", "Reeves", LocalDate.of(1964, 9, 2), "Beirut, Lebanon", "Canadian actor known for The Matrix and John Wick.");
        create(all, "Robert", "De Niro", LocalDate.of(1943, 8, 17), "New York City, USA", "Two-time Oscar winner and method acting legend.");
        create(all, "Jack", "Nicholson", LocalDate.of(1937, 4, 22), "Neptune City, New Jersey, USA", "Three-time Oscar winner with unmistakable screen presence.");
        create(all, "Matthew", "McConaughey", LocalDate.of(1969, 11, 4), "Uvalde, Texas, USA", "Oscar-winning actor known for dramatic range.");
        create(all, "Arnold", "Schwarzenegger", LocalDate.of(1947, 7, 30), "Thal, Austria", "Austrian-American actor, bodybuilder, and politician.");
        create(all, "Joaquin", "Phoenix", LocalDate.of(1974, 10, 28), "San Juan, Puerto Rico", "Intense method actor and Oscar winner.");
        create(all, "Ryan", "Gosling", LocalDate.of(1980, 11, 12), "London, Ontario, Canada", "Canadian actor known for dramatic and romantic roles.");
        create(all, "Harrison", "Ford", LocalDate.of(1942, 7, 13), "Chicago, Illinois, USA", "Iconic actor behind Han Solo and Indiana Jones.");
        create(all, "Sigourney", "Weaver", LocalDate.of(1949, 10, 8), "New York City, USA", "American actress and sci-fi icon.");
        create(all, "Uma", "Thurman", LocalDate.of(1970, 4, 29), "Boston, Massachusetts, USA", "American actress known for Tarantino collaborations.");
        create(all, "Kate", "Winslet", LocalDate.of(1975, 10, 5), "Reading, England", "Oscar-winning British actress.");
        create(all, "Song", "Kang-ho", LocalDate.of(1967, 1, 17), "Gimhae, South Korea", "South Korea's most acclaimed actor.");
        create(all, "Edward", "Norton", LocalDate.of(1969, 8, 18), "Boston, Massachusetts, USA", "Versatile American actor and filmmaker.");
        create(all, "Cate", "Blanchett", LocalDate.of(1969, 5, 14), "Ivanhoe, Victoria, Australia", "Two-time Oscar-winning Australian actress.");
        create(all, "Viggo", "Mortensen", LocalDate.of(1958, 10, 20), "New York City, USA", "Danish-American actor and artist.");

        System.out.println("Person seeding completed.");
    }

    private void create(List<Person> existing, String firstName, String lastName, LocalDate birthDate, String birthPlace, String bio) {
        boolean exists = existing.stream().anyMatch(p ->
                p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));
        if (exists) return;

        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setBirthDate(birthDate);
        p.setBirthPlace(birthPlace);
        p.setBio(bio);
        p.setPhotoUrl("https://via.placeholder.com/300x400");
        personRepository.save(p);
    }
}
