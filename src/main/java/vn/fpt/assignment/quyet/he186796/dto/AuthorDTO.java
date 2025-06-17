package vn.fpt.assignment.quyet.he186796.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class AuthorDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private String address;

    private Set<Long> bookIds;
}