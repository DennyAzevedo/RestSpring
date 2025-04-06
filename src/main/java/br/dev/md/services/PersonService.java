package br.dev.md.services;

import br.dev.md.model.Person;
import br.dev.md.repository.PersonRepository;
import br.dev.md.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());
    
    @Autowired
    PersonRepository personRepository;
    
    
    public List<Person> findAll(){
        logger.info("Finding all People!");

        return personRepository.findAll();
    }

    public Person findById(Long id){
        logger.info("Finding one Person!");
        
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }
    
    public Person create(Person person){
        logger.info("Creating one Person!");
        
        return personRepository.save(person);
    }
    
    public Person update(Person person){
        logger.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId())
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        
        return personRepository.save(entity);
    }

    public void delete(Long id){
        logger.info("Deleting one Person!");
        Person entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }
}
