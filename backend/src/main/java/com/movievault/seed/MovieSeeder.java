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

    // Verified TMDB profile photo URLs for people
    private static final Map<String, String> PERSON_PHOTO_URLS = new HashMap<>();

    static {
        // Actors
        PERSON_PHOTO_URLS.put("Tim Robbins",          "https://image.tmdb.org/t/p/w500/3FfJMIVwXgsIXbAT8ECBSZJAncR.jpg");
        PERSON_PHOTO_URLS.put("Morgan Freeman",       "https://image.tmdb.org/t/p/w500/jPsLqiYGSofU4s6BjrxnefMfabb.jpg");
        PERSON_PHOTO_URLS.put("Marlon Brando",        "https://image.tmdb.org/t/p/w500/iyO183LVAJ0I4ZkNibINPjfAjCP.jpg");
        PERSON_PHOTO_URLS.put("Al Pacino",            "https://image.tmdb.org/t/p/w500/m8HAAjq1T75JypKk0v1FFQn4ysZ.jpg");
        PERSON_PHOTO_URLS.put("Christian Bale",       "https://image.tmdb.org/t/p/w500/7Pxez9J8fuPd2Mn9kex13YALrCQ.jpg");
        PERSON_PHOTO_URLS.put("Heath Ledger",         "https://image.tmdb.org/t/p/w500/p2z2bURSg7nuMsN9P2s61e2RvNz.jpg");
        PERSON_PHOTO_URLS.put("Robert De Niro",       "https://image.tmdb.org/t/p/w500/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg");
        PERSON_PHOTO_URLS.put("Henry Fonda",          "https://image.tmdb.org/t/p/w500/aj9NP3cBMLRYIAhV45S9mvSHByT.jpg");
        PERSON_PHOTO_URLS.put("Liam Neeson",          "https://image.tmdb.org/t/p/w500/sRLev3wJioBgun3ZoeAUFpkLy0D.jpg");
        PERSON_PHOTO_URLS.put("Ralph Fiennes",        "https://image.tmdb.org/t/p/w500/u29BOqiV5GCQ8k8WUJM50i9xlBf.jpg");
        PERSON_PHOTO_URLS.put("Elijah Wood",          "https://image.tmdb.org/t/p/w500/ayARmqAe9Aab1zg6FjJG0u9MEBo.jpg");
        PERSON_PHOTO_URLS.put("Viggo Mortensen",      "https://image.tmdb.org/t/p/w500/4StH9C3FVNJ3njTkFGi6guvdHXH.jpg");
        PERSON_PHOTO_URLS.put("John Travolta",        "https://image.tmdb.org/t/p/w500/zyDLuyohFiON7QliYyP8hnxu2eX.jpg");
        PERSON_PHOTO_URLS.put("Samuel L. Jackson",    "https://image.tmdb.org/t/p/w500/AiAYAqwpM5xmiFrAIeQvUXDCVvo.jpg");
        PERSON_PHOTO_URLS.put("Uma Thurman",          "https://image.tmdb.org/t/p/w500/sBgAZWi3o4FsnaTvnTNtK6jpQcF.jpg");
        PERSON_PHOTO_URLS.put("Brad Pitt",            "https://image.tmdb.org/t/p/w500/cckcYc2v0yh1tc9QjRelptcOBko.jpg");
        PERSON_PHOTO_URLS.put("Edward Norton",        "https://image.tmdb.org/t/p/w500/8nytsqL59SFJTVYVrN72k6qkGgJ.jpg");
        PERSON_PHOTO_URLS.put("Tom Hanks",            "https://image.tmdb.org/t/p/w500/oFvZoKI6lvU03n4YoNGAll9rkas.jpg");
        PERSON_PHOTO_URLS.put("Leonardo DiCaprio",    "https://image.tmdb.org/t/p/w500/vo4fltT9zZ1kH8nhLetz8MED6jp.jpg");
        PERSON_PHOTO_URLS.put("Keanu Reeves",         "https://image.tmdb.org/t/p/w500/8RZLOyYGsoRe9p44q3xin9QkMHv.jpg");
        PERSON_PHOTO_URLS.put("Jack Nicholson",       "https://image.tmdb.org/t/p/w500/6h12pZsgj3WWjMtykUgfLkLEBWz.jpg");
        PERSON_PHOTO_URLS.put("Matthew McConaughey",  "https://image.tmdb.org/t/p/w500/lCySuYjhXix3FzQdS4oceDDrXKI.jpg");
        PERSON_PHOTO_URLS.put("Anthony Hopkins",      "https://image.tmdb.org/t/p/w500/u8AAGd2tF6uQcj9TXgyR4q8kn4H.jpg");
        PERSON_PHOTO_URLS.put("Jodie Foster",         "https://image.tmdb.org/t/p/w500/8DAd9knKivHR4CCStxlNEQXzjIh.jpg");
        PERSON_PHOTO_URLS.put("Arnold Schwarzenegger","https://image.tmdb.org/t/p/w500/dgCABuZp2HBehCT84O4WBp7KIoe.jpg");
        PERSON_PHOTO_URLS.put("Russell Crowe",        "https://image.tmdb.org/t/p/w500/uxiXuVH4vNWrKlJMVVPG1sxAJFe.jpg");
        PERSON_PHOTO_URLS.put("Sigourney Weaver",     "https://image.tmdb.org/t/p/w500/wTSnfktNBLd6kwQxgvkqYw6vEon.jpg");
        PERSON_PHOTO_URLS.put("Jamie Foxx",           "https://image.tmdb.org/t/p/w500/25zzvFA6yx2Q9BYnugsbd4JWDfu.jpg");
        PERSON_PHOTO_URLS.put("Hugh Jackman",         "https://image.tmdb.org/t/p/w500/4Xujtewxqt6aU0Y81tsS9gkjizk.jpg");
        PERSON_PHOTO_URLS.put("Song Kang-ho",         "https://image.tmdb.org/t/p/w500/7dw9wIpFZ5nJZ3zqrue8t7hUUgQ.jpg");
        PERSON_PHOTO_URLS.put("Ryan Gosling",         "https://image.tmdb.org/t/p/w500/asoKC7CLCqpZKZDL6iovNurQUdf.jpg");
        PERSON_PHOTO_URLS.put("Harrison Ford",        "https://image.tmdb.org/t/p/w500/zVnHagUvXkR2StdOtquEwsiwSVt.jpg");
        PERSON_PHOTO_URLS.put("Joaquin Phoenix",      "https://image.tmdb.org/t/p/w500/u38k3hQBDwNX0VA22aQceDp9Iyv.jpg");
        PERSON_PHOTO_URLS.put("Miles Teller",         "https://image.tmdb.org/t/p/w500/aciu7YM8fD0BzrrA6cJ5wDKZIA6.jpg");
        PERSON_PHOTO_URLS.put("J.K. Simmons",         "https://image.tmdb.org/t/p/w500/9EQRlEOHOc06YR5xCg67cxJiZb7.jpg");
        PERSON_PHOTO_URLS.put("Jim Carrey",           "https://image.tmdb.org/t/p/w500/j4QY2GN2ZfObQbgedyV3Lihwrt.jpg");
        PERSON_PHOTO_URLS.put("Kate Winslet",         "https://image.tmdb.org/t/p/w500/e3tdop3WhseRnn8KwMVLAV25Ybv.jpg");
        PERSON_PHOTO_URLS.put("Cate Blanchett",       "https://image.tmdb.org/t/p/w500/vUuEHiAR0eD3XEJhg2DWIjymUAA.jpg");
        PERSON_PHOTO_URLS.put("Emma Stone",           "https://image.tmdb.org/t/p/w500/6K21G4V1KTXQ0DtS0NkMmIanYaE.jpg");
        PERSON_PHOTO_URLS.put("Mark Hamill",          "https://image.tmdb.org/t/p/w500/zMQ93JTLW8KxusKhOlHFZhih3YQ.jpg");
        PERSON_PHOTO_URLS.put("Carrie Fisher",        "https://image.tmdb.org/t/p/w500/of4yHmryKPy92eeskUQ7MRmjC3l.jpg");
        PERSON_PHOTO_URLS.put("Adrien Brody",         "https://image.tmdb.org/t/p/w500/fttLDDPsLM3OPnzCeC1XuJRaR3K.jpg");
        PERSON_PHOTO_URLS.put("Guy Pearce",           "https://image.tmdb.org/t/p/w500/bFZKaEfCGNAiqidwBzC7WXRN0Ta.jpg");
        PERSON_PHOTO_URLS.put("Sam Worthington",      "https://image.tmdb.org/t/p/w500/r0rl8TqjO2wMxVvBeTjcYuM6Jlz.jpg");
        PERSON_PHOTO_URLS.put("Michael J. Fox",       "https://image.tmdb.org/t/p/w500/esyfNqnUCbCzOvGn8TaRLW1bD8S.jpg");
        PERSON_PHOTO_URLS.put("Harvey Keitel",        "https://image.tmdb.org/t/p/w500/5tVaJxE0gSBn4aYCMPYWmHmFP4e.jpg");
        PERSON_PHOTO_URLS.put("Tim Roth",             "https://image.tmdb.org/t/p/w500/kTasmMCC3P4RAZ3IeKMaHXSLpQf.jpg");
        PERSON_PHOTO_URLS.put("Timothee Chalamet",    "https://image.tmdb.org/t/p/w500/dFxpwRpmzpVfP1zjluH68DeQhyj.jpg");
        PERSON_PHOTO_URLS.put("Javier Bardem",        "https://image.tmdb.org/t/p/w500/4Cc9BsEa7qFLBFyomX9CfSfFBJU.jpg");
        PERSON_PHOTO_URLS.put("Josh Brolin",          "https://image.tmdb.org/t/p/w500/sX2etBbIkxRaCsATyw5ZpOVMPTD.jpg");
        PERSON_PHOTO_URLS.put("Cillian Murphy",       "https://image.tmdb.org/t/p/w500/llkbyWKwpfowZ6C8peBjIV9jj99.jpg");
        PERSON_PHOTO_URLS.put("Jesse Eisenberg",      "https://image.tmdb.org/t/p/w500/kiOEHbPoTuGYEOFdpJJMFAHmPXI.jpg");

        // Directors
        PERSON_PHOTO_URLS.put("Frank Darabont",       "https://image.tmdb.org/t/p/w500/5MaugmbFQrjJYS7R1gzQMFRkn9h.jpg");
        PERSON_PHOTO_URLS.put("Francis Ford Coppola", "https://image.tmdb.org/t/p/w500/IwGgkmW6IoJ9vuNF0T9CU3FYUX.jpg");
        PERSON_PHOTO_URLS.put("Christopher Nolan",    "https://image.tmdb.org/t/p/w500/xuAIuYSmsUzKlUMBFGVZaWsY3DZ.jpg");
        PERSON_PHOTO_URLS.put("Steven Spielberg",     "https://image.tmdb.org/t/p/w500/tZxcg19YQ3e8fJ0pOs7hjlnmmr6.jpg");
        PERSON_PHOTO_URLS.put("Peter Jackson",        "https://image.tmdb.org/t/p/w500/bNc908d59Ba8VDNr4eCcm4G1cR.jpg");
        PERSON_PHOTO_URLS.put("Quentin Tarantino",    "https://image.tmdb.org/t/p/w500/1gjcpAa99FAOWGnrUvHEXXsRs7o.jpg");
        PERSON_PHOTO_URLS.put("David Fincher",        "https://image.tmdb.org/t/p/w500/tpEczFclQZeKAiCeKZZ0adRvtfz.jpg");
        PERSON_PHOTO_URLS.put("Robert Zemeckis",      "https://image.tmdb.org/t/p/w500/3aWDp2pRW1C1KxH5yZK5v5DZULH.jpg");
        PERSON_PHOTO_URLS.put("Lana Wachowski",       "https://image.tmdb.org/t/p/w500/4wkzB7k2MBgFD5n9r5Tcvn7TRZU.jpg");
        PERSON_PHOTO_URLS.put("Martin Scorsese",      "https://image.tmdb.org/t/p/w500/9U9Y5GQuWX3EZy39B8nkk4NY01S.jpg");
        PERSON_PHOTO_URLS.put("Milos Forman",         "https://image.tmdb.org/t/p/w500/kJZAMjkKJf1ENJcA9xklqiSCNlN.jpg");
        PERSON_PHOTO_URLS.put("Jonathan Demme",       "https://image.tmdb.org/t/p/w500/6jMExHh7p1JHGblPqUjRoqPcqQj.jpg");
        PERSON_PHOTO_URLS.put("James Cameron",        "https://image.tmdb.org/t/p/w500/9NAZnTjBQ9WcXAQEzZpKy4vdQto.jpg");
        PERSON_PHOTO_URLS.put("Roger Allers",         "https://image.tmdb.org/t/p/w500/zYLqN8k2fGFMxQnFkALJi2gLjFw.jpg");
        PERSON_PHOTO_URLS.put("Ridley Scott",         "https://image.tmdb.org/t/p/w500/zABJmN9opmqD4orWl3KSdCaSo7Q.jpg");
        PERSON_PHOTO_URLS.put("Todd Phillips",        "https://image.tmdb.org/t/p/w500/jUsGqPaxjmvWjCJzLFX7EFNhIGa.jpg");
        PERSON_PHOTO_URLS.put("Damien Chazelle",      "https://image.tmdb.org/t/p/w500/o5r3JKQrFpVlcR6MRfvW76MqyXn.jpg");
        PERSON_PHOTO_URLS.put("Peter Weir",           "https://image.tmdb.org/t/p/w500/eVoxDRG3GbP9IyakJUiFHAjijx2.jpg");
        PERSON_PHOTO_URLS.put("George Lucas",         "https://image.tmdb.org/t/p/w500/M97Z8cxX5Mx2x73xVLRlsEuNR5.jpg");
        PERSON_PHOTO_URLS.put("Roman Polanski",       "https://image.tmdb.org/t/p/w500/q8hzpXzFRAnVMafYsrD0R5tKxUm.jpg");
        PERSON_PHOTO_URLS.put("Hayao Miyazaki",       "https://image.tmdb.org/t/p/w500/IfB9hy4JH1eH6HEfIgIGORXi5h.jpg");
        PERSON_PHOTO_URLS.put("Stanley Kubrick",      "https://image.tmdb.org/t/p/w500/yFT0VyIelI9aegZrsAwOG5iVP4v.jpg");
        PERSON_PHOTO_URLS.put("Denis Villeneuve",     "https://image.tmdb.org/t/p/w500/zdDx9Xs93UIrJFWYApYR28J8M6b.jpg");
        PERSON_PHOTO_URLS.put("Alejandro Gonzalez Inarritu", "https://image.tmdb.org/t/p/w500/rHCEajgbR0LKqDnvJuuB0CoPMxC.jpg");
        PERSON_PHOTO_URLS.put("Ethan Coen",           "https://image.tmdb.org/t/p/w500/pGbhEuU3g5gJTMDhzh9Wz0Gumqn.jpg");
        PERSON_PHOTO_URLS.put("Bong Joon-ho",         "https://image.tmdb.org/t/p/w500/stwnTvZAoD8gEJEDHpDQyLCyDy5.jpg");
        PERSON_PHOTO_URLS.put("Sidney Lumet",         "https://image.tmdb.org/t/p/w500/5SjvLI8vZFxYI3KSMxPBV8g0bXU.jpg");
    }

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
                "https://image.tmdb.org/t/p/w500/sBnhJ4f5KAzg6C3FwnEb8QFj8SB.jpg",
                List.of(GenreName.DRAMA), List.of("Frank Darabont"),
                new String[]{"Tim Robbins", "Andy Dufresne"}, new String[]{"Morgan Freeman", "Ellis 'Red' Redding"});

        seedMovie("The Godfather", LocalDate.of(1972, 3, 24), 175, "USA", "English",
                "The aging patriarch of an organized crime dynasty transfers control to his reluctant youngest son.",
                "https://image.tmdb.org/t/p/w500/wUgtkwwfQ8SHgyXDcDYN1NfkSx1.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Francis Ford Coppola"),
                new String[]{"Marlon Brando", "Don Vito Corleone"}, new String[]{"Al Pacino", "Michael Corleone"});

        seedMovie("The Dark Knight", LocalDate.of(2008, 7, 18), 152, "USA", "English",
                "When the menace known as the Joker wreaks havoc on Gotham, Batman must accept one of the greatest tests.",
                "https://image.tmdb.org/t/p/w500/gKMDmGde8aAD8M6dvm6j7wciSbJ.jpg",
                List.of(GenreName.ACTION, GenreName.CRIME, GenreName.DRAMA), List.of("Christopher Nolan"),
                new String[]{"Christian Bale", "Bruce Wayne"}, new String[]{"Heath Ledger", "The Joker"});

        seedMovie("The Godfather Part II", LocalDate.of(1974, 12, 20), 202, "USA", "English",
                "The early life and career of Vito Corleone in 1920s New York, and the continuing story of Michael Corleone.",
                "https://image.tmdb.org/t/p/w500/sWQjhhK9AidSLExyn7M26Tz4V6U.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Francis Ford Coppola"),
                new String[]{"Al Pacino", "Michael Corleone"}, new String[]{"Robert De Niro", "Vito Corleone"});

        seedMovie("12 Angry Men", LocalDate.of(1957, 4, 10), 96, "USA", "English",
                "A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.",
                "https://image.tmdb.org/t/p/w500/zASEq60XYKIPZ4kHOzP9f1pC06P.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Sidney Lumet"),
                new String[]{"Henry Fonda", "Juror #8"});

        seedMovie("Schindler's List", LocalDate.of(1993, 12, 15), 195, "USA", "English",
                "In German-occupied Poland, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce.",
                "https://image.tmdb.org/t/p/w500/7mYojklW12jrWzvjxipWFeUnO9M.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.DRAMA, GenreName.HISTORY), List.of("Steven Spielberg"),
                new String[]{"Liam Neeson", "Oskar Schindler"}, new String[]{"Ralph Fiennes", "Amon Goeth"});

        seedMovie("The Lord of the Rings: The Return of the King", LocalDate.of(2003, 12, 17), 201, "New Zealand", "English",
                "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam.",
                "https://image.tmdb.org/t/p/w500/75KNIheWdy7eLe4RWsbJmVjvJPU.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.FANTASY), List.of("Peter Jackson"),
                new String[]{"Elijah Wood", "Frodo"}, new String[]{"Viggo Mortensen", "Aragorn"});

        seedMovie("Pulp Fiction", LocalDate.of(1994, 10, 14), 154, "USA", "English",
                "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
                "https://image.tmdb.org/t/p/w500/5Whi9po8MTPyUTxAioXfyXGHNQE.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Quentin Tarantino"),
                new String[]{"John Travolta", "Vincent Vega"}, new String[]{"Samuel L. Jackson", "Jules Winnfield"}, new String[]{"Uma Thurman", "Mia Wallace"});

        seedMovie("The Lord of the Rings: The Fellowship of the Ring", LocalDate.of(2001, 12, 19), 178, "New Zealand", "English",
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the One Ring.",
                "https://image.tmdb.org/t/p/w500/bjWRzhXUXTNPnwptBkXQzCflVBE.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.FANTASY), List.of("Peter Jackson"),
                new String[]{"Elijah Wood", "Frodo"}, new String[]{"Viggo Mortensen", "Aragorn"}, new String[]{"Cate Blanchett", "Galadriel"});

        seedMovie("Fight Club", LocalDate.of(1999, 10, 15), 139, "USA", "English",
                "An insomniac office worker and a devil-may-care soap maker form an underground fight club.",
                "https://image.tmdb.org/t/p/w500/efBb4gjjKneUoBVgfFOUu2OwihS.jpg",
                List.of(GenreName.DRAMA), List.of("David Fincher"),
                new String[]{"Brad Pitt", "Tyler Durden"}, new String[]{"Edward Norton", "The Narrator"});

        seedMovie("Forrest Gump", LocalDate.of(1994, 7, 6), 142, "USA", "English",
                "The presidencies of Kennedy and Johnson, the Vietnam War, and other events unfold through the perspective of an Alabama man.",
                "https://image.tmdb.org/t/p/w500/iixrNXX79OR7knBx1i9S51PfVlz.jpg",
                List.of(GenreName.DRAMA, GenreName.ROMANCE), List.of("Robert Zemeckis"),
                new String[]{"Tom Hanks", "Forrest Gump"});

        seedMovie("Inception", LocalDate.of(2010, 7, 16), 148, "USA", "English",
                "A thief who steals corporate secrets through dream-sharing technology is given the task of planting an idea.",
                "https://image.tmdb.org/t/p/w500/efvcvRgOAZgFC2hrPUa6YqrE1KG.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.SCI_FI), List.of("Christopher Nolan"),
                new String[]{"Leonardo DiCaprio", "Dom Cobb"});

        seedMovie("The Lord of the Rings: The Two Towers", LocalDate.of(2002, 12, 18), 179, "New Zealand", "English",
                "While Frodo and Sam edge closer to Mordor, the scattered Fellowship members make a stand against Sauron.",
                "https://image.tmdb.org/t/p/w500/4oDm6P1FUAegMFfQBVhWIkNcaqk.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.FANTASY), List.of("Peter Jackson"),
                new String[]{"Elijah Wood", "Frodo"}, new String[]{"Viggo Mortensen", "Aragorn"});

        seedMovie("The Matrix", LocalDate.of(1999, 3, 31), 136, "USA", "English",
                "A computer hacker learns about the true nature of his reality and his role in the war against its controllers.",
                "https://image.tmdb.org/t/p/w500/xiCMLkZOVlxFbvWJMHuf4FJvnuX.jpg",
                List.of(GenreName.ACTION, GenreName.SCI_FI), List.of("Lana Wachowski"),
                new String[]{"Keanu Reeves", "Neo"});

        seedMovie("Goodfellas", LocalDate.of(1990, 9, 19), 146, "USA", "English",
                "The story of Henry Hill and his life in the mob, covering his relationship with his wife and partners.",
                "https://image.tmdb.org/t/p/w500/m9taboMOrt6h7sTLkGfOBSqp3qr.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA), List.of("Martin Scorsese"),
                new String[]{"Robert De Niro", "James Conway"});

        seedMovie("One Flew Over the Cuckoo's Nest", LocalDate.of(1975, 11, 19), 133, "USA", "English",
                "A criminal pleads insanity and is admitted to a mental institution, where he rebels against the oppressive nurse.",
                "https://image.tmdb.org/t/p/w500/3ICRZ5DOBwapO25lphA8yiCWi4v.jpg",
                List.of(GenreName.DRAMA), List.of("Milos Forman"),
                new String[]{"Jack Nicholson", "Randle McMurphy"});

        seedMovie("Interstellar", LocalDate.of(2014, 11, 7), 169, "USA", "English",
                "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                "https://image.tmdb.org/t/p/w500/q4emCJmjNomEE2pVGgqr3nDEIzI.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.SCI_FI), List.of("Christopher Nolan"),
                new String[]{"Matthew McConaughey", "Cooper"});

        seedMovie("The Silence of the Lambs", LocalDate.of(1991, 2, 14), 118, "USA", "English",
                "A young FBI cadet must receive the help of an incarcerated cannibalistic serial killer to catch another killer.",
                "https://image.tmdb.org/t/p/w500/qWG1nSFlp4oNwSSTCPk3Q3n4FlD.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Jonathan Demme"),
                new String[]{"Anthony Hopkins", "Hannibal Lecter"}, new String[]{"Jodie Foster", "Clarice Starling"});

        seedMovie("Saving Private Ryan", LocalDate.of(1998, 7, 24), 169, "USA", "English",
                "Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper.",
                "https://image.tmdb.org/t/p/w500/9GNCKzU8vUUMgexZ5npv08mPzKr.jpg",
                List.of(GenreName.DRAMA, GenreName.WAR), List.of("Steven Spielberg"),
                new String[]{"Tom Hanks", "Captain Miller"});

        seedMovie("Terminator 2: Judgment Day", LocalDate.of(1991, 7, 3), 137, "USA", "English",
                "A cyborg protects a young boy from a more advanced and deadly cyborg sent from the future.",
                "https://image.tmdb.org/t/p/w500/A221ktIYjvt8RnAXv0yD2QHzLts.jpg",
                List.of(GenreName.ACTION, GenreName.SCI_FI), List.of("James Cameron"),
                new String[]{"Arnold Schwarzenegger", "T-800"});

        seedMovie("The Lion King", LocalDate.of(1994, 6, 24), 88, "USA", "English",
                "Lion prince Simba flees his kingdom after the murder of his father, only to learn the true meaning of responsibility.",
                "https://image.tmdb.org/t/p/w500/lmr1fwOi03T3BRS1cnPqxW64MkZ.jpg",
                List.of(GenreName.ANIMATION, GenreName.ADVENTURE, GenreName.DRAMA, GenreName.FAMILY), List.of("Roger Allers"));

        seedMovie("Gladiator", LocalDate.of(2000, 5, 5), 155, "USA", "English",
                "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family.",
                "https://image.tmdb.org/t/p/w500/1f0U34P6OFPXvfQta556VfXZqiC.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.DRAMA), List.of("Ridley Scott"),
                new String[]{"Russell Crowe", "Maximus"});

        seedMovie("The Departed", LocalDate.of(2006, 10, 6), 151, "USA", "English",
                "An undercover cop and a mole in the police attempt to identify each other while infiltrating a Boston gang.",
                "https://image.tmdb.org/t/p/w500/1zfgt3lncUZBKbATGnifk1umVpZ.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Martin Scorsese"),
                new String[]{"Leonardo DiCaprio", "Billy Costigan"}, new String[]{"Jack Nicholson", "Frank Costello"});

        seedMovie("Alien", LocalDate.of(1979, 6, 22), 117, "USA", "English",
                "The crew of a commercial spacecraft encounters a deadly lifeform after investigating an unknown transmission.",
                "https://image.tmdb.org/t/p/w500/nJmcM3Kw0vOeaErYu9pIzKs9ZRW.jpg",
                List.of(GenreName.HORROR, GenreName.SCI_FI), List.of("Ridley Scott"),
                new String[]{"Sigourney Weaver", "Ellen Ripley"});

        seedMovie("Django Unchained", LocalDate.of(2012, 12, 25), 165, "USA", "English",
                "With the help of a German bounty-hunter, a freed slave sets out to rescue his wife from a plantation owner.",
                "https://image.tmdb.org/t/p/w500/3h3tSVY1r1qUGnUFTwXVSi9rdO.jpg",
                List.of(GenreName.DRAMA, GenreName.WESTERN), List.of("Quentin Tarantino"),
                new String[]{"Jamie Foxx", "Django"}, new String[]{"Leonardo DiCaprio", "Calvin Candie"});

        seedMovie("The Prestige", LocalDate.of(2006, 10, 20), 130, "USA", "English",
                "Two rival magicians engage in a bitter battle for supremacy, each devising increasingly complex illusions.",
                "https://image.tmdb.org/t/p/w500/m624LrPxlbXkMZVyM9tyDkI8g0M.jpg",
                List.of(GenreName.DRAMA, GenreName.MYSTERY, GenreName.SCI_FI), List.of("Christopher Nolan"),
                new String[]{"Christian Bale", "Alfred Borden"}, new String[]{"Hugh Jackman", "Robert Angier"});

        seedMovie("Parasite", LocalDate.of(2019, 5, 30), 132, "South Korea", "Korean",
                "Greed and class discrimination threaten the newly formed symbiotic relationship between two families.",
                "https://image.tmdb.org/t/p/w500/ujWkLw5WkffXdt8DYlwM0acVOyr.jpg",
                List.of(GenreName.COMEDY, GenreName.DRAMA, GenreName.THRILLER), List.of("Bong Joon-ho"),
                new String[]{"Song Kang-ho", "Ki-taek"});

        seedMovie("Blade Runner 2049", LocalDate.of(2017, 10, 6), 164, "USA", "English",
                "Young blade runner K's discovery of a long-buried secret leads him to track down former blade runner Rick Deckard.",
                "https://image.tmdb.org/t/p/w500/g3YrbSqzRXVEM74AaY8rK5OqY9u.jpg",
                List.of(GenreName.DRAMA, GenreName.MYSTERY, GenreName.SCI_FI), List.of("Denis Villeneuve"),
                new String[]{"Ryan Gosling", "K"}, new String[]{"Harrison Ford", "Rick Deckard"});

        seedMovie("Joker", LocalDate.of(2019, 10, 4), 122, "USA", "English",
                "A mentally troubled standup comedian embarks on a downward spiral that leads to the creation of an iconic villain.",
                "https://image.tmdb.org/t/p/w500/opwCl56Zi8mextLETtM3d0ryVFU.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Todd Phillips"),
                new String[]{"Joaquin Phoenix", "Arthur Fleck"});

        seedMovie("Whiplash", LocalDate.of(2014, 10, 10), 106, "USA", "English",
                "A young drummer enrolls at a cutthroat music conservatory where his dreams of greatness are mentored by an instructor.",
                "https://image.tmdb.org/t/p/w500/4Twi6WFaiGHc0SrIhdtZRNt45xg.jpg",
                List.of(GenreName.DRAMA, GenreName.MUSICAL), List.of("Damien Chazelle"),
                new String[]{"Miles Teller", "Andrew"}, new String[]{"J.K. Simmons", "Fletcher"});

        seedMovie("The Truman Show", LocalDate.of(1998, 6, 5), 103, "USA", "English",
                "An insurance salesman discovers his whole life is actually a reality TV show and tries to escape.",
                "https://image.tmdb.org/t/p/w500/4BuOoIULkVhVYMKGg0JISxgl34f.jpg",
                List.of(GenreName.COMEDY, GenreName.DRAMA), List.of("Peter Weir"),
                new String[]{"Jim Carrey", "Truman Burbank"});

        seedMovie("Titanic", LocalDate.of(1997, 12, 19), 194, "USA", "English",
                "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious Titanic.",
                "https://image.tmdb.org/t/p/w500/4D6T8LKGcSFEyAXXsoRNzRwZ6FW.jpg",
                List.of(GenreName.DRAMA, GenreName.ROMANCE), List.of("James Cameron"),
                new String[]{"Leonardo DiCaprio", "Jack Dawson"}, new String[]{"Kate Winslet", "Rose DeWitt Bukater"});

        seedMovie("Se7en", LocalDate.of(1995, 9, 22), 127, "USA", "English",
                "Two detectives hunt a serial killer who uses the seven deadly sins as his motives.",
                "https://image.tmdb.org/t/p/w500/jKstsinBR4nXRzmOItLjmpt9CQ4.jpg",
                List.of(GenreName.CRIME, GenreName.MYSTERY, GenreName.THRILLER), List.of("David Fincher"),
                new String[]{"Brad Pitt", "Detective Mills"}, new String[]{"Morgan Freeman", "Detective Somerset"});

        seedMovie("Star Wars: Episode IV - A New Hope", LocalDate.of(1977, 5, 25), 121, "USA", "English",
                "Luke Skywalker joins forces with a Jedi Knight and a cocky pilot to rescue Princess Leia and save the galaxy.",
                "https://image.tmdb.org/t/p/w500/shXRdVjx8fPJmwSNOfH7xa9GYT1.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.FANTASY), List.of("George Lucas"),
                new String[]{"Harrison Ford", "Han Solo"}, new String[]{"Mark Hamill", "Luke Skywalker"}, new String[]{"Carrie Fisher", "Princess Leia"});

        seedMovie("The Pianist", LocalDate.of(2002, 9, 17), 150, "France", "English",
                "A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto during World War II.",
                "https://image.tmdb.org/t/p/w500/72ponXpzVO1kPKtEYFKTEqWIwdK.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.DRAMA, GenreName.WAR), List.of("Roman Polanski"),
                new String[]{"Adrien Brody", "Wladyslaw Szpilman"});

        seedMovie("Spirited Away", LocalDate.of(2001, 7, 20), 125, "Japan", "Japanese",
                "During her family's move, a young girl wanders into a world ruled by gods, witches, and spirits.",
                "https://image.tmdb.org/t/p/w500/gSqglWhte6Q3zT71ovD1Ww4ckzd.jpg",
                List.of(GenreName.ANIMATION, GenreName.ADVENTURE, GenreName.FAMILY), List.of("Hayao Miyazaki"));

        seedMovie("The Green Mile", LocalDate.of(1999, 12, 10), 189, "USA", "English",
                "The lives of guards on Death Row are affected by one of their charges: a gentle giant with a mysterious gift.",
                "https://image.tmdb.org/t/p/w500/brSYDZZ2epodl8lxdzaQqYapNlM.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.FANTASY), List.of("Frank Darabont"),
                new String[]{"Tom Hanks", "Paul Edgecomb"});

        seedMovie("Kill Bill: Vol. 1", LocalDate.of(2003, 10, 10), 111, "USA", "English",
                "After awakening from a four-year coma, a former assassin wreaks vengeance on the team of assassins who betrayed her.",
                "https://image.tmdb.org/t/p/w500/4mjGr9jLJZO3bECWwyX1NN3GApn.jpg",
                List.of(GenreName.ACTION, GenreName.CRIME, GenreName.THRILLER), List.of("Quentin Tarantino"),
                new String[]{"Uma Thurman", "The Bride"});

        seedMovie("La La Land", LocalDate.of(2016, 12, 9), 128, "USA", "English",
                "While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations.",
                "https://image.tmdb.org/t/p/w500/omSdA7qmNx03Tpbdr0byGzp3bxz.jpg",
                List.of(GenreName.COMEDY, GenreName.DRAMA, GenreName.MUSICAL), List.of("Damien Chazelle"),
                new String[]{"Ryan Gosling", "Sebastian"}, new String[]{"Emma Stone", "Mia"});

        seedMovie("Inglourious Basterds", LocalDate.of(2009, 8, 21), 153, "USA", "English",
                "In Nazi-occupied France, a group of Jewish-American soldiers plan a bold mission to take down Nazi leaders.",
                "https://image.tmdb.org/t/p/w500/xYoYYRo0vZNa0VJAOvXtfRPCi3S.jpg",
                List.of(GenreName.ADVENTURE, GenreName.DRAMA, GenreName.WAR), List.of("Quentin Tarantino"),
                new String[]{"Brad Pitt", "Lt. Aldo Raine"});

        seedMovie("The Wolf of Wall Street", LocalDate.of(2013, 12, 25), 180, "USA", "English",
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stockbroker to his fall involving crime and corruption.",
                "https://image.tmdb.org/t/p/w500/eQTzLZ8szPpQuxKsxvUDUEq74nd.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.COMEDY, GenreName.CRIME), List.of("Martin Scorsese"),
                new String[]{"Leonardo DiCaprio", "Jordan Belfort"});

        seedMovie("Memento", LocalDate.of(2000, 10, 11), 113, "USA", "English",
                "A man with short-term memory loss uses tattoos and notes to hunt for his wife's killer.",
                "https://image.tmdb.org/t/p/w500/fKTPH2WvH8nHTXeBYBVhawtRqtR.jpg",
                List.of(GenreName.MYSTERY, GenreName.THRILLER), List.of("Christopher Nolan"),
                new String[]{"Guy Pearce", "Leonard"});

        seedMovie("Avatar", LocalDate.of(2009, 12, 18), 162, "USA", "English",
                "A paraplegic Marine dispatched to Pandora on a unique mission becomes torn between following orders and protecting the world.",
                "https://image.tmdb.org/t/p/w500/6DfNtptjkIrevq4mDAB0XDXT0NO.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.FANTASY), List.of("James Cameron"),
                new String[]{"Sam Worthington", "Jake Sully"});

        seedMovie("Aliens", LocalDate.of(1986, 7, 18), 137, "USA", "English",
                "Ellen Ripley is rescued after drifting through space for 57 years, and returns to the planet where her crew found the alien.",
                "https://image.tmdb.org/t/p/w500/36pLA8iRP7ROgXVCLdwByzIWZQ.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.SCI_FI), List.of("James Cameron"),
                new String[]{"Sigourney Weaver", "Ellen Ripley"});

        seedMovie("Back to the Future", LocalDate.of(1985, 7, 3), 116, "USA", "English",
                "Marty McFly is accidentally sent back to 1955 in a time-travelling DeLorean and must make sure his parents fall in love.",
                "https://image.tmdb.org/t/p/w500/2fEoQxuMRzebtqwHICuyM6B0HTH.jpg",
                List.of(GenreName.ADVENTURE, GenreName.COMEDY, GenreName.SCI_FI), List.of("Robert Zemeckis"),
                new String[]{"Michael J. Fox", "Marty McFly"});

        seedMovie("The Shining", LocalDate.of(1980, 5, 23), 146, "USA", "English",
                "A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence.",
                "https://image.tmdb.org/t/p/w500/lMniGq1BYnHOdhEiLOQxGYAKATC.jpg",
                List.of(GenreName.DRAMA, GenreName.HORROR), List.of("Stanley Kubrick"),
                new String[]{"Jack Nicholson", "Jack Torrance"});

        seedMovie("Reservoir Dogs", LocalDate.of(1992, 10, 23), 99, "USA", "English",
                "When a simple jewelry heist goes wrong, the surviving criminals begin to suspect that one of them is a police informant.",
                "https://image.tmdb.org/t/p/w500/elQbITBVugEJhC2o8Vq2VzhqLcr.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Quentin Tarantino"),
                new String[]{"Harvey Keitel", "Mr. White"}, new String[]{"Tim Roth", "Mr. Orange"});

        seedMovie("Dune", LocalDate.of(2021, 10, 22), 155, "USA", "English",
                "Paul Atreides must travel to the most dangerous planet in the universe to ensure the future of his family.",
                "https://image.tmdb.org/t/p/w500/4L3Dkdujbrq5EKcrVdWEdTEvhtb.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.DRAMA), List.of("Denis Villeneuve"),
                new String[]{"Timothee Chalamet", "Paul Atreides"});

        seedMovie("The Revenant", LocalDate.of(2015, 12, 25), 156, "USA", "English",
                "A frontiersman on a fur trading expedition fights for survival after being mauled by a bear and left for dead.",
                "https://image.tmdb.org/t/p/w500/7koZ9mAQR37eyFfT2TO9MESjs9M.jpg",
                List.of(GenreName.ACTION, GenreName.ADVENTURE, GenreName.DRAMA), List.of("Alejandro Gonzalez Inarritu"),
                new String[]{"Leonardo DiCaprio", "Hugh Glass"});

        seedMovie("No Country for Old Men", LocalDate.of(2007, 11, 21), 122, "USA", "English",
                "Violence and mayhem ensue after a hunter stumbles upon a drug deal gone wrong and a suitcase full of cash.",
                "https://image.tmdb.org/t/p/w500/3ep7Ot5m3qgcBubF7cxcpcc2CIY.jpg",
                List.of(GenreName.CRIME, GenreName.DRAMA, GenreName.THRILLER), List.of("Ethan Coen"),
                new String[]{"Javier Bardem", "Anton Chigurh"}, new String[]{"Josh Brolin", "Llewelyn Moss"});

        seedMovie("Oppenheimer", LocalDate.of(2023, 7, 21), 180, "USA", "English",
                "The story of J. Robert Oppenheimer's role in the development of the atomic bomb during World War II.",
                "https://image.tmdb.org/t/p/w500/gBrW3l0GsN7fvrn6A7ofaw90qj4.jpg",
                List.of(GenreName.BIOGRAPHY, GenreName.DRAMA, GenreName.HISTORY), List.of("Christopher Nolan"),
                new String[]{"Cillian Murphy", "J. Robert Oppenheimer"});

        seedMovie("The Social Network", LocalDate.of(2010, 10, 1), 120, "USA", "English",
                "The founding of Facebook is told through the perspectives of the students who would become tech billionaires and outcasts.",
                "https://image.tmdb.org/t/p/w500/4UqFxN1ayyXyPI6aZim2q0HvCZs.jpg",
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

        String photoUrl = PERSON_PHOTO_URLS.getOrDefault(fullName, null);

        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setPhotoUrl(photoUrl);
        return personRepository.save(p);
    }
}
