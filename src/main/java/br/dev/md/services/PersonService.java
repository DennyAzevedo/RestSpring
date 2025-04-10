package br.dev.md.services;

import static br.dev.md.mapper.ObjectMapper.parseObject;
import static br.dev.md.mapper.ObjectMapper.parseListObjects;

import br.dev.md.controllers.PersonController;
import br.dev.md.data.v1.PersonDTO;
import br.dev.md.data.v2.PersonDTOV2;
import br.dev.md.exception.RequiredObjectIsNullException;
import br.dev.md.mapper.custom.PersonMapper;
import br.dev.md.model.Person;
import br.dev.md.repository.PersonRepository;
import br.dev.md.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PersonService {
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());
    
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonMapper converter;
    
    public List<PersonDTO> findAll(){
        logger.info("Finding all People!");
        var people = parseListObjects(personRepository.findAll(), PersonDTO.class);
        people.forEach(this::addHateoasLinks);
        
        return people;
    }

    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");
        var entity =  personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO person){
        if (person == null) throw new RequiredObjectIsNullException();
        
        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);
        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        
        return dto;
    }

    public PersonDTOV2 createV2(PersonDTOV2 person){
        logger.info("Creating one Person!");
        var entity = converter.convertDTOToEntity(person);
        
        return converter.convertEntityToDTO(personRepository.save(entity));
    }


    public PersonDTO update(PersonDTO person){
        if (person == null) throw new RequiredObjectIsNullException();
        
        logger.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId())
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one Person!");
        Person entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class)
                .findById(dto.getId())
            ).withSelfRel()
            .withType("GET")
        );
        dto.add(linkTo(methodOn(PersonController.class)
                .findAll()
            ).withRel("findAll")
            .withType("GET")
        );
        dto.add(linkTo(methodOn(PersonController.class)
                .create(dto)
            ).withRel("create")
            .withType("POST")
        );
        dto.add(linkTo(methodOn(PersonController.class)
                .update(dto)
            ).withRel("update")
            .withType("PUT")
        );
        dto.add(linkTo(methodOn(PersonController.class)
                .delete(dto.getId())
            ).withRel("delete")
            .withType("DELETE")
        );
    }
}
