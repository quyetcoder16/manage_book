package vn.fpt.assignment.quyet.he186796.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "borrowing")
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrower_name", nullable = false)
    private String borrowerName;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;
}