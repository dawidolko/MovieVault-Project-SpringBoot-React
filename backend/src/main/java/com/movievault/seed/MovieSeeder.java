package com.movievault.seed;

import com.movievault.model.*;
import com.movievault.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class MovieSeeder {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final PersonRepository personRepository;
    private final MovieCastRepository movieCastRepository;
    private final MovieDirectorRepository movieDirectorRepository;

    public MovieSeeder(MovieRepository movieRepository, GenreRepository genreRepository,
                       PersonRepository personRepository, MovieCastRepository movieCastRepository,
                       MovieDirectorRepository movieDirectorRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.personRepository = personRepository;
        this.movieCastRepository = movieCastRepository;
        this.movieDirectorRepository = movieDirectorRepository;
    }

    public void seed() {
        if (movieRepository.count() > 0) {
            System.out.println("Movies already seeded, skipping.");
            return;
        }

        seedMovie("The Shawshank Redemption", LocalDate.of(1994, 9, 23), 142, "USA", "English",
                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                "https://image.tmdb.org/t/p/w500/9cjIGRSQL56tv3onsxhMNa598DF.jpg",
                List.of(GenreName.DRAMA), List.of("Frank Darabont"),
                new String[]{"Tim Robbins", "Andy Dufresne"}, new String[]{"Morgan Freeman", "Ellis 'Red' Redding"});

        seedMovie("The Godfather", LocalDate.of(1972, 3, 24), 175, "USA", "English",
                "The aging patriarch of an organized crime dynasty transfers control to his reluctant youngest son.",
                "https://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Francis Ford Coppola"),
                new String[]{"Marlon Brando", "Don Vito Corleone"}, new String[]{"Al Pacino", "Michael Corleone"});

        seedMovie("The Dark Knight", LocalDate.of(2008, 7, 18), 152, "USA", "English",
                "When the menace known as the Joker wreaks havoc on Gotham, Batman must accept one of the greatest tests.",
                "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911BTUgMe1nFK1.jpg",
                List.of(GenreName.ACTION, GenreName.CRIME, GenreName.DRAMA), List.of("Christopher Nolan"),
                new String[]{"Christian Bale", "Bruce Wayne"}, new String[]{"Heath Ledger", "The Joker"});

        seedMovie("The Godfather Part II", LocalDate.of(1974, 12, 20), 202, "USA", "English",
                "The early life and career of Vito Corleone in 1920s New York, and the continuing story of Michael Corleone.",
                "https://image.tmdb.org/t/p/w500/hek3koDUyRQk7FIhPXsa6mT2Zc3.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Francis Ford Coppola"),
                new String[]{"Al Pacino", "Michael Corleone"}, new String[]{"Robert De Niro", "Vito Corleone"});

        seedMovie("12 Angry Men", LocalDate.of(1957, 4, 10), 96, "USA", "English",
                "A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.",
                "https://image.tmdb.org/t/p/w500/ow3wq89wM8qd5X7hWKxiRfsFf9C.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Sidney Lumet"),
                new String[]{"Henry Fonda", "Juror #8"});

        seedMovie("Schindler's List", LocalDate.of(1993, 12, 15), 195, "USA", "English",
                "In German-occupied Poland, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce.",
                "https://image.tmdb.org/t/p/w500/sF1U4EUQS8YHUYjNl3pMGNIQyr0.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.DRAMA, GenreName.HISTORY), List.of("Steven Spielberg"),
                new String[]{"Liam Neeson", "Oskar Schindler"}, new String[]{"Ralph Fiennes", "Amon Goeth"});

        seedMovie("The Lord of the Rings: The Return of the King", LocalDate.of(2003, 12, 17), 201, "New Zealand", "English",
                "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam.",
                "https://image.tmdb.org/t/p/w500/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.FANTASY), List.of("Peter Jackson"),
                new String[]{"Elijah Wood", "Frodo"}, new String[]{"Viggo Mortensen", "Aragorn"});

        seedMovie("Pulp Fiction", LocalDate.of(1994, 10, 14), 154, "USA", "English",
                "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
                "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Quentin Tarantino"),
                new String[]{"John Travolta", "Vincent Vega"}, new String[]{"Samuel L. Jackson", "Jules Winnfield"}, new String[]{"Uma Thurman", "Mia Wallace"});

        seedMovie("The Lord of the Rings: The Fellowship of the Ring", LocalDate.of(2001, 12, 19), 178, "New Zealand", "English",
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the One Ring.",
                "https://image.tmdb.org/t/p/w500/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.FANTASY), List.of("Peter Jackson"),
                new String[]{"Elijah Wood", "Frodo"}, new String[]{"Viggo Mortensen", "Aragorn"}, new String[]{"Cate Blanchett", "Galadriel"});

        seedMovie("Fight Club", LocalDate.of(1999, 10, 15), 139, "USA", "English",
                "An insomniac office worker and a devil-may-care soap maker form an underground fight club.",
                "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QI4S2t0POD5.jpg",
                List.of(GenreName.DRAMA), List.of("David Fincher"),
                new String[]{"Brad Pitt", "Tyler Durden"}, new String[]{"Edward Norton", "The Narrator"});

        seedMovie("Forrest Gump", LocalDate.of(1994, 7, 6), 142, "USA", "English",
                "The presidencies of Kennedy and Johnson, the Vietnam War, and other events unfold through the perspective of an Alabama man.",
                "https://image.tmdb.org/t/p/w500/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg",
                List.of(GenreName.DRAMA, GenreName.ROMANCE), List.of("Robert Zemeckis"),
                new String[]{"Tom Hanks", "Forrest Gump"});

        seedMovie("Inception", LocalDate.of(2010, 7, 16), 148, "USA", "English",
                "A thief who steals corporate secrets through dream-sharing technology is given the task of planting an idea.",
                "https://image.tmdb.org/t/p/w500/ljsZTbVsrQSqZgWeep2B1QiDKuh.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.SCI_FI), List.of("Christopher Nolan"),
                new String[]{"Leonardo DiCaprio", "Dom Cobb"});

        seedMovie("The Lord of the Rings: The Two Towers", LocalDate.of(2002, 12, 18), 179, "New Zealand", "English",
                "While Frodo and Sam edge closer to Mordor, the scattered Fellowship members make a stand against Sauron.",
                "https://image.tmdb.org/t/p/w500/5VTN0pR8gcqV3EPUHHfMGnJYN9L.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.FANTASY), List.of("Peter Jackson"),
                new String[]{"Elijah Wood", "Frodo"}, new String[]{"Viggo Mortensen", "Aragorn"});

        seedMovie("The Matrix", LocalDate.of(1999, 3, 31), 136, "USA", "English",
                "A computer hacker learns about the true nature of his reality and his role in the war against its controllers.",
                "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
                List.of(GenreName.ACTION, GenreName.SCI_FI), List.of("Lana Wachowski"),
                new String[]{"Keanu Reeves", "Neo"});

        seedMovie("Goodfellas", LocalDate.of(1990, 9, 19), 146, "USA", "English",
                "The story of Henry Hill and his life in the mob, covering his relationship with his wife and partners.",
                "https://image.tmdb.org/t/p/w500/aKuFiU82s5ISJpGZp7YkIr3kCUd.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Martin Scorsese"),
                new String[]{"Robert De Niro", "James Conway"});

        seedMovie("One Flew Over the Cuckoo's Nest", LocalDate.of(1975, 11, 19), 133, "USA", "English",
                "A criminal pleads insanity and is admitted to a mental institution, where he rebels against the oppressive nurse.",
                "https://image.tmdb.org/t/p/w500/3jcbDmRFiQ83drXNOvRDeKHxS0C.jpg",
                List.of(GenreName.DRAMA), List.of("Milos Forman"),
                new String[]{"Jack Nicholson", "Randle McMurphy"});

        seedMovie("Interstellar", LocalDate.of(2014, 11, 7), 169, "USA", "English",
                "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.SCI_FI), List.of("Christopher Nolan"),
                new String[]{"Matthew McConaughey", "Cooper"});

        seedMovie("The Silence of the Lambs", LocalDate.of(1991, 2, 14), 118, "USA", "English",
                "A young FBI cadet must receive the help of an incarcerated cannibalistic serial killer to catch another killer.",
                "https://image.tmdb.org/t/p/w500/uS9m8OBk1RVFDUGc2F3AC1yi27T.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Jonathan Demme"),
                new String[]{"Anthony Hopkins", "Hannibal Lecter"}, new String[]{"Jodie Foster", "Clarice Starling"});

        seedMovie("Saving Private Ryan", LocalDate.of(1998, 7, 24), 169, "USA", "English",
                "Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper.",
                "https://image.tmdb.org/t/p/w500/1wY4psJ5NVEhCuOYROwLH2XExM2.jpg",
                List.of(GenreName.DRAMA, GenreName.WAR), List.of("Steven Spielberg"),
                new String[]{"Tom Hanks", "Captain Miller"});

        seedMovie("Terminator 2: Judgment Day", LocalDate.of(1991, 7, 3), 137, "USA", "English",
                "A cyborg protects a young boy from a more advanced and deadly cyborg sent from the future.",
                "https://image.tmdb.org/t/p/w500/5M0j0B18abtBI5gi2RhfjjurTqb.jpg",
                List.of(GenreName.ACTION, GenreName.SCI_FI), List.of("James Cameron"),
                new String[]{"Arnold Schwarzenegger", "T-800"});

        seedMovie("The Lion King", LocalDate.of(1994, 6, 24), 88, "USA", "English",
                "Lion prince Simba flees his kingdom after the murder of his father, only to learn the true meaning of responsibility.",
                "https://image.tmdb.org/t/p/w500/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg",
                List.of(GenreName.ANIMATION, GenreName.ADVENTURE, GenreName.DRAMA, GenreName.FAMILY), List.of("Roger Allers"));

        seedMovie("Gladiator", LocalDate.of(2000, 5, 5), 155, "USA", "English",
                "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family.",
                "https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.DRAMA), List.of("Ridley Scott"),
                new String[]{"Russell Crowe", "Maximus"});

        seedMovie("The Departed", LocalDate.of(2006, 10, 6), 151, "USA", "English",
                "An undercover cop and a mole in the police attempt to identify each other while infiltrating a Boston gang.",
                "https://image.tmdb.org/t/p/w500/nT97ifVT2J1yMQmeq20Dqv60GGE.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Martin Scorsese"),
                new String[]{"Leonardo DiCaprio", "Billy Costigan"}, new String[]{"Jack Nicholson", "Frank Costello"});

        seedMovie("Alien", LocalDate.of(1979, 6, 22), 117, "USA", "English",
                "The crew of a commercial spacecraft encounters a deadly lifeform after investigating an unknown transmission.",
                "https://image.tmdb.org/t/p/w500/vfrQk5IPloGg1v9Rzbh2Eg3VGyM.jpg",
                List.of(GenreName.HORROR, GenreName.SCI_FI), List.of("Ridley Scott"),
                new String[]{"Sigourney Weaver", "Ellen Ripley"});

        seedMovie("Django Unchained", LocalDate.of(2012, 12, 25), 165, "USA", "English",
                "With the help of a German bounty-hunter, a freed slave sets out to rescue his wife from a plantation owner.",
                "https://image.tmdb.org/t/p/w500/7oWY8VDWW7thTzWh3OKYRkWUlD5.jpg",
                List.of(GenreName.DRAMA, GenreName.WESTERN), List.of("Quentin Tarantino"),
                new String[]{"Jamie Foxx", "Django"}, new String[]{"Leonardo DiCaprio", "Calvin Candie"});

        seedMovie("The Prestige", LocalDate.of(2006, 10, 20), 130, "USA", "English",
                "Two rival magicians engage in a bitter battle for supremacy, each devising increasingly complex illusions.",
                "https://image.tmdb.org/t/p/w500/bdN3gXuIZYaJP7ftKK2sU0nPtEA.jpg",
                List.of(GenreName.DRAMA, GenreName.MYSTERY, GenreName.SCI_FI), List.of("Christopher Nolan"),
                new String[]{"Christian Bale", "Alfred Borden"}, new String[]{"Hugh Jackman", "Robert Angier"});

        seedMovie("Parasite", LocalDate.of(2019, 5, 30), 132, "South Korea", "Korean",
                "Greed and class discrimination threaten the newly formed symbiotic relationship between two families.",
                "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
                List.of(GenreName.COMEDY, GenreName.DRAMA, GenreName.THRILLER), List.of("Bong Joon-ho"),
                new String[]{"Song Kang-ho", "Ki-taek"});

        seedMovie("Blade Runner 2049", LocalDate.of(2017, 10, 6), 164, "USA", "English",
                "Young blade runner K's discovery of a long-buried secret leads him to track down former blade runner Rick Deckard.",
                "https://image.tmdb.org/t/p/w500/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg",
                List.of(GenreName.DRAMA, GenreName.MYSTERY, GenreName.SCI_FI), List.of("Denis Villeneuve"),
                new String[]{"Ryan Gosling", "K"}, new String[]{"Harrison Ford", "Rick Deckard"});

        seedMovie("Joker", LocalDate.of(2019, 10, 4), 122, "USA", "English",
                "A mentally troubled standup comedian embarks on a downward spiral that leads to the creation of an iconic villain.",
                "https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Todd Phillips"),
                new String[]{"Joaquin Phoenix", "Arthur Fleck"});

        seedMovie("Whiplash", LocalDate.of(2014, 10, 10), 106, "USA", "English",
                "A young drummer enrolls at a cutthroat music conservatory where his dreams of greatness are mentored by an instructor.",
                "https://image.tmdb.org/t/p/w500/7fn624j5lj3xTme2SgiLCeuedmO.jpg",
                List.of(GenreName.DRAMA, GenreName.MUSICAL), List.of("Damien Chazelle"),
                new String[]{"Miles Teller", "Andrew"}, new String[]{"J.K. Simmons", "Fletcher"});

        seedMovie("The Truman Show", LocalDate.of(1998, 6, 5), 103, "USA", "English",
                "An insurance salesman discovers his whole life is actually a reality TV show and tries to escape.",
                "https://image.tmdb.org/t/p/w500/vuza0WqY239yBXOadKlGwJsZJFE.jpg",
                List.of(GenreName.COMEDY, GenreName.DRAMA), List.of("Peter Weir"),
                new String[]{"Jim Carrey", "Truman Burbank"});

        seedMovie("Titanic", LocalDate.of(1997, 12, 19), 194, "USA", "English",
                "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious Titanic.",
                "https://image.tmdb.org/t/p/w500/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg",
                List.of(GenreName.DRAMA, GenreName.ROMANCE), List.of("James Cameron"),
                new String[]{"Leonardo DiCaprio", "Jack Dawson"}, new String[]{"Kate Winslet", "Rose DeWitt Bukater"});

        seedMovie("Se7en", LocalDate.of(1995, 9, 22), 127, "USA", "English",
                "Two detectives hunt a serial killer who uses the seven deadly sins as his motives.",
                "https://image.tmdb.org/t/p/w500/6yoghtyTpznpBik8EngEmJskVUO.jpg",
                List.of(GenreName.CRIME, GenreName.MYSTERY, GenreName.THRILLER), List.of("David Fincher"),
                new String[]{"Brad Pitt", "Detective Mills"}, new String[]{"Morgan Freeman", "Detective Somerset"});

        seedMovie("Star Wars: Episode IV - A New Hope", LocalDate.of(1977, 5, 25), 121, "USA", "English",
                "Luke Skywalker joins forces with a Jedi Knight and a cocky pilot to rescue Princess Leia and save the galaxy.",
                "https://image.tmdb.org/t/p/w500/6FfCtAuVAW8XJjZ7eWeLibRLWTw.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.FANTASY), List.of("George Lucas"),
                new String[]{"Harrison Ford", "Han Solo"}, new String[]{"Mark Hamill", "Luke Skywalker"}, new String[]{"Carrie Fisher", "Princess Leia"});

        seedMovie("The Pianist", LocalDate.of(2002, 9, 17), 150, "France", "English",
                "A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto during World War II.",
                "https://image.tmdb.org/t/p/w500/2hFvxCCWrTmCYwfy7yum0GKRi3Y.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.DRAMA, GenreName.WAR), List.of("Roman Polanski"),
                new String[]{"Adrien Brody", "Wladyslaw Szpilman"});

        seedMovie("Spirited Away", LocalDate.of(2001, 7, 20), 125, "Japan", "Japanese",
                "During her family's move, a young girl wanders into a world ruled by gods, witches, and spirits.",
                "https://image.tmdb.org/t/p/w500/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
                List.of(GenreName.ANIMATION, GenreName.ADVENTURE, GenreName.FAMILY), List.of("Hayao Miyazaki"));

        seedMovie("The Green Mile", LocalDate.of(1999, 12, 10), 189, "USA", "English",
                "The lives of guards on Death Row are affected by one of their charges: a gentle giant with a mysterious gift.",
                "https://image.tmdb.org/t/p/w500/o0lO84GI7qrG6XkLB23jlB8cUHA.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.FANTASY), List.of("Frank Darabont"),
                new String[]{"Tom Hanks", "Paul Edgecomb"});

        seedMovie("Kill Bill: Vol. 1", LocalDate.of(2003, 10, 10), 111, "USA", "English",
                "After awakening from a four-year coma, a former assassin wreaks vengeance on the team of assassins who betrayed her.",
                "https://image.tmdb.org/t/p/w500/v7TaX8kXMXs5yFFGR41guUDNcnB.jpg",
                List.of(GenreName.ACTION, GenreName.CRIME, GenreName.THRILLER), List.of("Quentin Tarantino"),
                new String[]{"Uma Thurman", "The Bride"});

        seedMovie("La La Land", LocalDate.of(2016, 12, 9), 128, "USA", "English",
                "While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations.",
                "https://image.tmdb.org/t/p/w500/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg",
                List.of(GenreName.COMEDY, GenreName.DRAMA, GenreName.MUSICAL), List.of("Damien Chazelle"),
                new String[]{"Ryan Gosling", "Sebastian"}, new String[]{"Emma Stone", "Mia"});

        seedMovie("Inglourious Basterds", LocalDate.of(2009, 8, 21), 153, "USA", "English",
                "In Nazi-occupied France, a group of Jewish-American soldiers plan a bold mission to take down Nazi leaders.",
                "https://image.tmdb.org/t/p/w500/7sfbEnaARXDDhKm0CZ7D7uc2sbo.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.WAR), List.of("Quentin Tarantino"),
                new String[]{"Brad Pitt", "Lt. Aldo Raine"});

        seedMovie("The Wolf of Wall Street", LocalDate.of(2013, 12, 25), 180, "USA", "English",
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stockbroker to his fall involving crime and corruption.",
                "https://image.tmdb.org/t/p/w500/34m2tygAYBGqA9MXKhRDtzYd4MR.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.COMEDY, GenreName.CRIME), List.of("Martin Scorsese"),
                new String[]{"Leonardo DiCaprio", "Jordan Belfort"});

        seedMovie("Memento", LocalDate.of(2000, 10, 11), 113, "USA", "English",
                "A man with short-term memory loss uses tattoos and notes to hunt for his wife's killer.",
                "https://image.tmdb.org/t/p/w500/yuNs09hvpHVU1cBTCAk9zxsL2oW.jpg",
                List.of(GenreName.MYSTERY, GenreName.THRILLER), List.of("Christopher Nolan"),
                new String[]{"Guy Pearce", "Leonard"});

        seedMovie("Avatar", LocalDate.of(2009, 12, 18), 162, "USA", "English",
                "A paraplegic Marine dispatched to Pandora on a unique mission becomes torn between following orders and protecting the world.",
                "https://image.tmdb.org/t/p/w500/kyeqWdyUXW608qlYkRqosgbbJyK.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.FANTASY), List.of("James Cameron"),
                new String[]{"Sam Worthington", "Jake Sully"});

        seedMovie("Aliens", LocalDate.of(1986, 7, 18), 137, "USA", "English",
                "Ellen Ripley is rescued after drifting through space for 57 years, and returns to the planet where her crew found the alien.",
                "https://image.tmdb.org/t/p/w500/r1x5JGpyqkM9wYFELU8bBgLEyWA.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.SCI_FI), List.of("James Cameron"),
                new String[]{"Sigourney Weaver", "Ellen Ripley"});

        seedMovie("Back to the Future", LocalDate.of(1985, 7, 3), 116, "USA", "English",
                "Marty McFly is accidentally sent back to 1955 in a time-travelling DeLorean and must make sure his parents fall in love.",
                "https://image.tmdb.org/t/p/w500/fNOH9f1aA7XRTzl1sAOx9iF553Q.jpg",
                List.of(GenreName.ADVENTURE, GenreName.COMEDY, GenreName.SCI_FI), List.of("Robert Zemeckis"),
                new String[]{"Michael J. Fox", "Marty McFly"});

        seedMovie("The Shining", LocalDate.of(1980, 5, 23), 146, "USA", "English",
                "A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence.",
                "https://image.tmdb.org/t/p/w500/nRj5511mZdTl4saWEPoj9QroTIu.jpg",
                List.of(GenreName.DRAMA, GenreName.HORROR), List.of("Stanley Kubrick"),
                new String[]{"Jack Nicholson", "Jack Torrance"});

        seedMovie("Reservoir Dogs", LocalDate.of(1992, 10, 23), 99, "USA", "English",
                "When a simple jewelry heist goes wrong, the surviving criminals begin to suspect that one of them is a police informant.",
                "https://image.tmdb.org/t/p/w500/xi8Iu6qyTfyZVDVy60raIOYJJmk.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Quentin Tarantino"),
                new String[]{"Harvey Keitel", "Mr. White"}, new String[]{"Tim Roth", "Mr. Orange"});

        seedMovie("Dune", LocalDate.of(2021, 10, 22), 155, "USA", "English",
                "Paul Atreides must travel to the most dangerous planet in the universe to ensure the future of his family.",
                "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.DRAMA), List.of("Denis Villeneuve"),
                new String[]{"Timothee Chalamet", "Paul Atreides"});

        seedMovie("The Revenant", LocalDate.of(2015, 12, 25), 156, "USA", "English",
                "A frontiersman on a fur trading expedition fights for survival after being mauled by a bear and left for dead.",
                "https://image.tmdb.org/t/p/w500/ji3ecJphATlVgWNY0B4gEZJU2Th.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.DRAMA), List.of("Alejandro Gonzalez Inarritu"),
                new String[]{"Leonardo DiCaprio", "Hugh Glass"});

        seedMovie("No Country for Old Men", LocalDate.of(2007, 11, 21), 122, "USA", "English",
                "Violence and mayhem ensue after a hunter stumbles upon a drug deal gone wrong and a suitcase full of cash.",
                "https://image.tmdb.org/t/p/w500/bj1v6YKF8yHqA489GFiPGl2iDPJ.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Ethan Coen"),
                new String[]{"Javier Bardem", "Anton Chigurh"}, new String[]{"Josh Brolin", "Llewelyn Moss"});

        seedMovie("Oppenheimer", LocalDate.of(2023, 7, 21), 180, "USA", "English",
                "The story of J. Robert Oppenheimer's role in the development of the atomic bomb during World War II.",
                "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.DRAMA, GenreName.HISTORY), List.of("Christopher Nolan"),
                new String[]{"Cillian Murphy", "J. Robert Oppenheimer"});

        seedMovie("The Social Network", LocalDate.of(2010, 10, 1), 120, "USA", "English",
                "The founding of Facebook is told through the perspectives of the students who would become tech billionaires and outcasts.",
                "https://image.tmdb.org/t/p/w500/n0ybibhJtQ5icDqTp8eRhcootU.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.DRAMA), List.of("David Fincher"),
                new String[]{"Jesse Eisenberg", "Mark Zuckerberg"});

        System.out.println("Movie seeding completed: " + movieRepository.count() + " movies.");
    }

    private void seedMovie(String title, LocalDate releaseDate, int duration, String country, String language,
                           String description, String posterUrl, List<GenreName> genreNames, List<String> directorNames,
                           String[]... castEntries) {

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseDate(releaseDate);
        movie.setDuration(duration);
        movie.setCountry(country);
        movie.setLanguage(language);
        movie.setDescription(description);
        movie.setPosterUrl(posterUrl);

        Set<Genre> genres = new HashSet<>();
        for (GenreName gn : genreNames) {
            genreRepository.findByName(gn).ifPresent(genres::add);
        }
        movie.setGenres(genres);
        movieRepository.save(movie);

        for (String dirName : directorNames) {
            Person director = findOrCreatePerson(dirName);
            if (director != null) {
                MovieDirector md = new MovieDirector();
                md.setMovie(movie);
                md.setPerson(director);
                movieDirectorRepository.save(md);
            }
        }

        int order = 1;
        for (String[] entry : castEntries) {
            Person actor = findOrCreatePerson(entry[0]);
            if (actor != null) {
                MovieCast mc = new MovieCast();
                mc.setMovie(movie);
                mc.setPerson(actor);
                mc.setCharacterName(entry[1]);
                mc.setCastOrder(order++);
                movieCastRepository.save(mc);
            }
        }
    }

    private Person findOrCreatePerson(String fullName) {
        String[] parts = fullName.split(" ", 2);
        if (parts.length < 2) return null;

        String firstName = parts[0];
        String lastName = parts[1];

        List<Person> found = personRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName);
        for (Person p : found) {
            if (p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)) {
                return p;
            }
        }

        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setPhotoUrl("https://via.placeholder.com/300x400");
        return personRepository.save(p);
    }
}
