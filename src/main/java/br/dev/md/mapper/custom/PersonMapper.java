package br.dev.md.mapper.custom;

import br.dev.md.data.v2.PersonDTOV2;
import br.dev.md.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonMapper {
    public PersonDTOV2 convertEntityToDTO(Person person){
        PersonDTOV2 personDTO = new PersonDTOV2();
        
        personDTO.setId(person.getId());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.setBirthDate(person.getBirthDate());
        personDTO.setAddress(person.getAddress());
        personDTO.setGender(person.getGender());
        
        return personDTO;
    }
    
    public Person convertDTOToEntity(PersonDTOV2 personDTO){
        Person person = new Person();
        
        person.setId(personDTO.getId());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setBirthDate(personDTO.getBirthDate());
        person.setAddress(personDTO.getAddress());
        person.setGender(personDTO.getGender());
        
        return person;
    }
    
    public List<PersonDTOV2> convertListEntityToDTO(List<Person> personList){
        List<PersonDTOV2> personDTOList = new ArrayList<>();
        
        for (Person person : personList) {
            personDTOList.add(convertEntityToDTO(person));
        }
        
        return personDTOList;
    }
    
    public List<Person> convertListDTOToEntity(List<PersonDTOV2> personDTOList){
        List<Person> personList = new ArrayList<>();
        
        for (PersonDTOV2 personDTO : personDTOList) {
            personList.add(convertDTOToEntity(personDTO));
        }
        
        return personList;
    }
}
