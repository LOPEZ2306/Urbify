package com.example.urbify.repository;

import com.example.urbify.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
    boolean existsByIdentification(String identification);
}
