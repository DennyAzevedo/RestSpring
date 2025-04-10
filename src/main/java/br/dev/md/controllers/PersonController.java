package br.dev.md.controllers;

import br.dev.md.controllers.docs.PersonControllerDocs;
import br.dev.md.data.v1.PersonDTO;
import br.dev.md.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for managing people")
public class PersonController implements PersonControllerDocs {
    
    @Autowired
    private PersonService personService;
    
    @GetMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public List<PersonDTO> findAll() {
        return personService.findAll();
    }
    
    @GetMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public PersonDTO findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    @PostMapping(
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        PersonDTO createPerson = personService.create(person);
        return ResponseEntity.status(201).body(createPerson);
    }
/*
    @PostMapping(
        value = "/v2",
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<PersonDTOV2> create(@RequestBody PersonDTOV2 person) {
        PersonDTOV2 createPerson = personService.createV2(person);
        return ResponseEntity.status(201).body(createPerson);
    }
 */

    @PutMapping(
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public PersonDTO update(@RequestBody PersonDTO person) {
        return personService.update(person);
    }
    
    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(
        @PathVariable("id") Long id
    ) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
