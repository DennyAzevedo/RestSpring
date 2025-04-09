package br.dev.md.unit.services;

import br.dev.md.model.Person;
import br.dev.md.data.v1.PersonDTO;
import br.dev.md.exception.RequiredObjectIsNullException;
import br.dev.md.repository.PersonRepository;
import br.dev.md.services.PersonService;

import br.dev.md.share.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;
    
    @InjectMocks
    private PersonService personService;
    
    @Mock
    PersonRepository personRepository;
    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        var result = personService.findById(1L);
        
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assert(result.getLinks()
            .stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/person/v1/1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "POST")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "PUT")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/person/v1/1") && 
                Objects.equals(link.getType(), "DELETE")
            )
        );
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        when(personRepository.save(person)).thenReturn(persisted);
        var result = personService.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assert(result.getLinks()
            .stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/person/v1/1") &&
                Objects.equals(link.getType(), "GET")
        )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "POST")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "PUT")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/person/v1/1") &&
                Objects.equals(link.getType(), "DELETE")
            )
        );
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(
            RequiredObjectIsNullException.class,
            () -> {
                personService.create(null);
            }
        );
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(persisted);
        var result = personService.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assert(result.getLinks()
            .stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/person/v1/1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "POST")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "PUT")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/person/v1/1") &&
                Objects.equals(link.getType(), "DELETE")
            )
        );
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(
            RequiredObjectIsNullException.class,
            () -> {
                personService.update(null);
            }
        );
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        personService.delete(1L);

        verify(personRepository, times(1)).findById(anyLong());
        verify(personRepository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();
        when(personRepository.findAll()).thenReturn(list);
        List<PersonDTO> people = personService.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);

        assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getLinks());
        assert(personOne.getLinks()
            .stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/person/v1/1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(personOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(personOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "POST")
            )
        );
        assert(personOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "PUT")
            )
        );
        assert(personOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/person/v1/1") &&
                Objects.equals(link.getType(), "DELETE")
            )
        );
        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());

        var personFour = people.get(4);

        assertNotNull(personFour);
        assertNotNull(personFour.getId());
        assertNotNull(personFour.getLinks());
        assert(personFour.getLinks()
            .stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/person/v1/4") &&
                Objects.equals(link.getType(), "GET")
            )   
        );
        assert(personFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(personFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "POST")
            )
        );
        assert(personFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "PUT")
            )
        );
        assert(personFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/person/v1/4") &&
                Objects.equals(link.getType(), "DELETE")
            )
        );
        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

        var personSeven = people.get(7);

        assertNotNull(personSeven);
        assertNotNull(personSeven.getId());
        assertNotNull(personSeven.getLinks());
        assert(personSeven.getLinks()
            .stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/person/v1/7") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(personSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(personSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "POST")
            )
        );
        assert(personSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/person/v1") &&
                Objects.equals(link.getType(), "PUT")
            )
        );
        assert(personSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/person/v1/7") &&
                Objects.equals(link.getType(), "DELETE")
            )
        );
        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Last Name Test7", personSeven.getLastName());
        assertEquals("Female", personSeven.getGender());
    }
}