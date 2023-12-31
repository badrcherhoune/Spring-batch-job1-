package com.example.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.batch.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
