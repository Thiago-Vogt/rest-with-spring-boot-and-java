package br.com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
	
}
