
package vn.fpt.assignment.quyet.he186796.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.fpt.assignment.quyet.he186796.dto.BorrowingDTO;
import vn.fpt.assignment.quyet.he186796.service.BookService;
import vn.fpt.assignment.quyet.he186796.service.BorrowingService;

@Controller
@RequestMapping("/borrowings")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;
    private final BookService bookService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("borrowings", borrowingService.findAll());
        return "borrowings/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("borrowing", new BorrowingDTO());
        model.addAttribute("books", bookService.findAll());
        return "borrowings/create";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("borrowing") BorrowingDTO borrowingDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.findAll());
            return "borrowings/create";
        }
        try {
            borrowingService.save(borrowingDTO);
            return "redirect:/borrowings";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("books", bookService.findAll());
            return "borrowings/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("borrowing", borrowingService.findById(id));
        model.addAttribute("books", bookService.findAll());
        return "borrowings/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("borrowing") BorrowingDTO borrowingDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.findAll());
            return "borrowings/edit";
        }
        try {
            borrowingDTO.setId(id);
            borrowingService.save(borrowingDTO);
            return "redirect:/borrowings";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("books", bookService.findAll());
            return "borrowings/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        borrowingService.delete(id);
        return "redirect:/borrowings";
    }
}