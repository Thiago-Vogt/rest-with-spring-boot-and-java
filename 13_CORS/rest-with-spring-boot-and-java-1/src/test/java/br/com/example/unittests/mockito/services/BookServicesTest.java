package br.com.example.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.example.data.vo.v1.BookVO;
import br.com.example.exceptions.RequiredObjectIsNullException;
import br.com.example.model.Book;
import br.com.example.repositories.BookRepository;
import br.com.example.services.BookServices;
import br.com.example.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	MockBook input;
	
	@InjectMocks
	private BookServices service;
	
	@Mock
	BookRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1", result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}
	

	@Test
	void testCreate() {
		Book entity = input.mockEntity(1);
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1", result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}
	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "Is is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
	}

	@Test
	void testUpdate() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1", result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}
	
	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "Is is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
	}

	@Test
	void testDelete() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	
	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var books = service.findAll();
		
		assertNotNull(books);
		assertEquals(14, books.size());
		
		var book1 = books.get(1);
		assertNotNull(book1);
		assertNotNull(book1.getKey());
		assertNotNull(book1.getLinks());
		assertTrue(book1.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1", book1.getTitle());
		assertEquals("Author Test1", book1.getAuthor());
		assertEquals(25D, book1.getPrice());
		assertNotNull(book1.getLaunchDate());
		
		var book4 = books.get(4);
		assertNotNull(book4);
		assertNotNull(book4.getKey());
		assertNotNull(book4.getLinks());
		assertTrue(book4.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Title Test4", book4.getTitle());
		assertEquals("Author Test4", book4.getAuthor());
		assertEquals(25D, book4.getPrice());
		assertNotNull(book4.getLaunchDate());
	}
	
}
