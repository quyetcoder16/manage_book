package vn.fpt.assignment.quyet.he186796.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "ISBN is mandatory")
    private String isbn;

    @PastOrPresent(message = "Year must be in the past or present")
    private LocalDate year;

    @Min(value = 0, message = "Available copies cannot be negative")
    private int availableCopies = 1; // Default to 1

    private Set<Long> authorIds;
}