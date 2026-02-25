package com.movievault.service;

import com.movievault.model.Person;
import com.movievault.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> search(String name) {
        return personRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public Person update(Long id, Person updated) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setFirstName(updated.getFirstName());
        person.setLastName(updated.getLastName());
        person.setBirthDate(updated.getBirthDate());
        person.setBirthPlace(updated.getBirthPlace());
        person.setBio(updated.getBio());
        person.setPhotoUrl(updated.getPhotoUrl());
        return personRepository.save(person);
    }
}
