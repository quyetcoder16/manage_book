package vn.fpt.assignment.quyet.he186796.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BorrowingDTO {

    private Long id;

    @NotNull(message = "Book is required")
    private Long bookId;

    @NotBlank(message = "Borrower name is required")
    private String borrowerName;

    @NotNull(message = "Borrow date is required")
    private LocalDate borrowDate;

    private LocalDate returnDate;
}