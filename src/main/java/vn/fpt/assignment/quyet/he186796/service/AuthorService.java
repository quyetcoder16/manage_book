package vn.fpt.assignment.quyet.he186796.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpt.assignment.quyet.he186796.dto.AuthorDTO;
import vn.fpt.assignment.quyet.he186796.entity.Author;
import vn.fpt.assignment.quyet.he186796.entity.Book;
import vn.fpt.assignment.quyet.he186796.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.fpt.assignment.quyet.he186796.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AuthorDTO findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        return convertToDTO(author);
    }

    @Transactional
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = convertToEntity(authorDTO);
        author = authorRepository.save(author);
        return convertToDTO(author);
    }

    @Transactional
    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        // Clear both sides of the relationship
        for (Book book : author.getBooks()) {
            book.getAuthors().remove(author);
        }
        author.getBooks().clear();

        authorRepository.delete(author);
    }

    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setDateOfBirth(author.getDateOfBirth());
        dto.setAddress(author.getAddress());
        dto.setBookIds(author.getBooks().stream().map(Book::getId).collect(Collectors.toSet()));
        return dto;
    }

    private Author convertToEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        author.setDateOfBirth(dto.getDateOfBirth());
        author.setAddress(dto.getAddress());
        if (dto.getBookIds() != null) {
            author.setBooks(dto.getBookIds().stream()
                    .map(id -> bookRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Book not found")))
                    .collect(Collectors.toSet()));
        }
        return author;
    }
}