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
        Borrowing borrowing = convertToEntity(borrowingDTO);
        borrowing = borrowingRepository.save(borrowing);
        return convertToDTO(borrowing);
    }

    @Transactional
    public void delete(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing not found"));
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
