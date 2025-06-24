package vn.fpt.assignment.quyet.he186796.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.fpt.assignment.quyet.he186796.dto.BorrowingDTO;
import vn.fpt.assignment.quyet.he186796.entity.Book;
import vn.fpt.assignment.quyet.he186796.entity.Borrowing;
import vn.fpt.assignment.quyet.he186796.repository.BookRepository;
import vn.fpt.assignment.quyet.he186796.repository.BorrowingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;

    public List<BorrowingDTO> findAll() {
        return borrowingRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BorrowingDTO findById(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing not found"));
        return convertToDTO(borrowing);
    }

    @Transactional
    public BorrowingDTO save(BorrowingDTO borrowingDTO) {
        Book book = bookRepository.findById(borrowingDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (borrowingDTO.getId() == null) { // New borrowing
            if (book.getAvailableCopies() <= 0) {
                throw new IllegalStateException("No available copies of this book");
            }
            book.setAvailableCopies(book.getAvailableCopies() - 1);
        } else { // Update borrowing
            Borrowing existingBorrowing = borrowingRepository.findById(borrowingDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Borrowing not found"));
            if (existingBorrowing.getReturnDate() == null && borrowingDTO.getReturnDate() != null) {
                // Book is being returned
                book.setAvailableCopies(book.getAvailableCopies() + 1);
            } else if (existingBorrowing.getReturnDate() != null && borrowingDTO.getReturnDate() == null) {
                // Re-borrowing a returned book
                if (book.getAvailableCopies() <= 0) {
                    throw new IllegalStateException("No available copies of this book");
                }
                book.setAvailableCopies(book.getAvailableCopies() - 1);
            }
        }

        bookRepository.save(book);
        Borrowing borrowing = convertToEntity(borrowingDTO);
        borrowing = borrowingRepository.save(borrowing);
        return convertToDTO(borrowing);
    }

    @Transactional
    public void delete(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing not found"));
        if (borrowing.getReturnDate() == null) {
            // Increment available copies if the book wasn't returned
            Book book = borrowing.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
        }
        borrowingRepository.delete(borrowing);
    }

    private BorrowingDTO convertToDTO(Borrowing borrowing) {
        BorrowingDTO dto = new BorrowingDTO();
        dto.setId(borrowing.getId());
        dto.setBookId(borrowing.getBook().getId());
        dto.setBorrowerName(borrowing.getBorrowerName());
        dto.setBorrowDate(borrowing.getBorrowDate());
        dto.setReturnDate(borrowing.getReturnDate());
        return dto;
    }

    private Borrowing convertToEntity(BorrowingDTO dto) {
        Borrowing borrowing = new Borrowing();
        borrowing.setId(dto.getId());
        borrowing.setBorrowerName(dto.getBorrowerName());
        borrowing.setBorrowDate(dto.getBorrowDate());
        borrowing.setReturnDate(dto.getReturnDate());
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        borrowing.setBook(book);
        return borrowing;
    }
}