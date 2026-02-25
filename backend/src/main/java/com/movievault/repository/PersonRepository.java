package com.movievault.repository;

import com.movievault.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByLastNameContainingIgnoreCase(String lastName);
    List<Person> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
