package vn.fpt.assignment.quyet.he186796.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpt.assignment.quyet.he186796.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}