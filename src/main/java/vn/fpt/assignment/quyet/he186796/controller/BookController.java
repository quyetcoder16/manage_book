package vn.fpt.assignment.quyet.he186796.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.fpt.assignment.quyet.he186796.dto.BookDTO;
import vn.fpt.assignment.quyet.he186796.service.AuthorService;
import vn.fpt.assignment.quyet.he186796.service.BookService;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authorService.findAll());
        return "books/create";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/create";
        }
        bookService.save(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("authors", authorService.findAll());
        return "books/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/edit";
        }
        bookDTO.setId(id);
        bookService.save(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}