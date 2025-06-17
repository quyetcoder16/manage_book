package vn.fpt.assignment.quyet.he186796.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.fpt.assignment.quyet.he186796.dto.BookDTO;
import vn.fpt.assignment.quyet.he186796.entity.Author;
import vn.fpt.assignment.quyet.he186796.entity.Book;
import vn.fpt.assignment.quyet.he186796.repository.AuthorRepository;
import vn.fpt.assignment.quyet.he186796.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return convertToDTO(book);
    }

    @Transactional
    public BookDTO save(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        book = bookRepository.save(book);
        return convertToDTO(book);
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setIsbn(book.getIsbn());
        dto.setYear(book.getYear());
        dto.setAuthorIds(book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()));
        return dto;
    }

    private Book convertToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setName(dto.getName());
        book.setIsbn(dto.getIsbn());
        book.setYear(dto.getYear());
        if (dto.getAuthorIds() != null) {
            book.setAuthors(dto.getAuthorIds().stream()
                    .map(id -> authorRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Author not found")))
                    .collect(Collectors.toSet()));
        }
        return book;
    }
}