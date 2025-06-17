package vn.fpt.assignment.quyet.he186796.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.fpt.assignment.quyet.he186796.dto.AuthorDTO;
import vn.fpt.assignment.quyet.he186796.service.AuthorService;
import vn.fpt.assignment.quyet.he186796.service.BookService;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("author", new AuthorDTO());
        model.addAttribute("books", bookService.findAll());
        return "authors/create";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("author") AuthorDTO authorDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.findAll());
            return "authors/create";
        }
        authorService.save(authorDTO);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        model.addAttribute("books", bookService.findAll());
        return "authors/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("author") AuthorDTO authorDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.findAll());
            return "authors/edit";
        }
        authorDTO.setId(id);
        authorService.save(authorDTO);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        authorService.delete(id);
        return "redirect:/authors";
    }
}