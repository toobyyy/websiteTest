package com.example.ylswebsite.model.persistence;

import com.example.ylswebsite.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {

}
