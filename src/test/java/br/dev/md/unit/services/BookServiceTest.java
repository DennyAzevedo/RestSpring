package br.dev.md.unit.services;

import br.dev.md.data.v1.BookDTO;
import br.dev.md.exception.RequiredObjectIsNullException;
import br.dev.md.model.Book;
import br.dev.md.repository.BookRepository;
import br.dev.md.services.BookService;
import br.dev.md.share.mocks.MockBook;
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
public class BookServiceTest {
    MockBook input;

    @InjectMocks
    private BookService bookService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        var result = bookService.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/book/v1/1") &&
                Objects.equals(link.getType(), "GET")
            ));
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/book/v1") && 
                Objects.equals(link.getType(), "GET")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(), "POST")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(), "PUT")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/book/v1/1") &&
                Objects.equals(link.getType(), "DELETE")
            )
        );
        assertEquals("Some Author1", result.getAuthor());
        assertEquals(25D, result.getPrice());
        assertEquals("Some Title1", result.getTitle());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void create() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);
        BookDTO dto = input.mockDTO(1);
        when(bookRepository.save(book)).thenReturn(persisted);
        var result = bookService.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/book/v1/1") &&
                Objects.equals(link.getType(),"GET")
            ));
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"GET")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"POST")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"PUT")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/book/v1/1") &&
                Objects.equals(link.getType(),"DELETE")
            )
        );
        assertEquals("Some Author1", result.getAuthor());
        assertEquals(25D, result.getPrice());
        assertEquals("Some Title1", result.getTitle());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
            () -> {
                bookService.create(null);
            });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);
        BookDTO dto = input.mockDTO(1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(persisted);
        var result = bookService.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/book/v1/1") &&
                Objects.equals(link.getType(),"GET")
            ));
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"GET")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link -> 
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"POST")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"PUT")
            )
        );
        assert(result.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/book/v1/1") &&
                Objects.equals(link.getType(),"DELETE")
            )
        );
        assertEquals("Some Author1", result.getAuthor());
        assertEquals(25D, result.getPrice());
        assertEquals("Some Title1", result.getTitle());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
            () -> {
                bookService.update(null);
            });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.delete(1L);

        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();
        when(bookRepository.findAll()).thenReturn(list);
        List<BookDTO> books = bookService.findAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        var bookOne = books.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getLinks());

        assert(bookOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/book/v1/1") &&
                Objects.equals(link.getType(),"GET")
            ));
        assert(bookOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"GET")
            )
        );
        assert(bookOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"POST")
            )
        );
        assert(bookOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"PUT")
            )
        );
        assert(bookOne.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/book/v1/1") &&
                Objects.equals(link.getType(),"DELETE")
            )
        );
        assertEquals("Some Author1", bookOne.getAuthor());
        assertEquals(25D, bookOne.getPrice());
        assertEquals("Some Title1", bookOne.getTitle());
        assertNotNull(bookOne.getLaunchDate());

        var bookFour = books.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getId());
        assertNotNull(bookFour.getLinks());
        assert(bookFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/book/v1/4") &&
                Objects.equals(link.getType(),"GET")
            ));
        assert(bookFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"GET")
            )
        );
        assert(bookFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"POST")
            )
        );
        assert(bookFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"PUT")
            )
        );
        assert(bookFour.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/book/v1/4") &&
                Objects.equals(link.getType(),"DELETE")
            )
        );
        assertEquals("Some Author4", bookFour.getAuthor());
        assertEquals(25D, bookFour.getPrice());
        assertEquals("Some Title4", bookFour.getTitle());
        assertNotNull(bookFour.getLaunchDate());

        var bookSeven = books.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getId());
        assertNotNull(bookSeven.getLinks());
        assert(bookSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("self") &&
                link.getHref().endsWith("/api/book/v1/7") &&
                Objects.equals(link.getType(),"GET")
            ));
        assert(bookSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("findAll") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"GET")
            )
        );
        assert(bookSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("create") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"POST")
            )
        );
        assert(bookSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("update") &&
                link.getHref().endsWith("/api/book/v1") &&
                Objects.equals(link.getType(),"PUT")
            )
        );
        assert(bookSeven.getLinks().stream()
            .anyMatch(link ->
                link.getRel().value().equals("delete") &&
                link.getHref().endsWith("/api/book/v1/7") &&
                Objects.equals(link.getType(),"DELETE")
            )
        );
        assertEquals("Some Author7", bookSeven.getAuthor());
        assertEquals(25D, bookSeven.getPrice());
        assertEquals("Some Title7", bookSeven.getTitle());
        assertNotNull(bookFour.getLaunchDate());
    }
}
