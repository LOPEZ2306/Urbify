package com.example.urbify.service;


import com.example.urbify.models.Person;
import com.example.urbify.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public Person save(Person person) {
        return personRepository.save(person);
    }

    public List<Person> listAllPerson() {
        return personRepository.findAll();
    }

    public Optional<Person> getById(Long id) {
        return personRepository.findById(id);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public boolean existsByIdentification(String identification) {
        return personRepository.existsByIdentification(identification);
    }


}
