package br.dev.md.controllers;

import br.dev.md.model.Person;
import br.dev.md.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    
    @Autowired
    private PersonService personService;
    
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person createPerson = personService.create(person);
        return ResponseEntity.status(201).body(createPerson);
    }

    @PutMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(
        @PathVariable("id") Long id
    ) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
