package br.dev.md.services;

import br.dev.md.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());
    
    public List<Person> findAll(){
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    private Person mockPerson(int i) {
        logger.info("Finding all People!");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstNane("FirstName "+ i);
        person.setLastName("LastName "+ i);
        person.setAddress("Rua "+ i + " - São Paulo - Brasil");
        person.setGender("Male");
        
        return person;
    }

    public Person findById(String id){
        logger.info("Finding one Person!");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstNane("Denny");
        person.setLastName("Azevedo");
        person.setAddress("Caraguatatuba - São Paulo - Brasil");
        person.setGender("Male");
        
        return person;
    }
    
    public Person create(Person person){
        logger.info("Creating one Person!");
        
        return person;
    }
    
    public Person update(Person person){
        logger.info("Updating one Person!");
        
        return person;
    }

    public void delete(String id){
        logger.info("Deleting one Person!");
    }
}
