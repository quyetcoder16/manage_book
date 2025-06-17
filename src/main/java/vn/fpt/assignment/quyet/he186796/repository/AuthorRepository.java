package vn.fpt.assignment.quyet.he186796.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpt.assignment.quyet.he186796.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}