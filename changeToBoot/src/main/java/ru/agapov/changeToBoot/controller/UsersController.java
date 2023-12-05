package ru.agapov.changeToBoot.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.agapov.changeToBoot.model.User;
import ru.agapov.changeToBoot.service.UserService;


@Controller
@RequestMapping("/")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/", "list"})
    public String showAllUsers(Model model, @ModelAttribute("flashMessage") String flashAttribute) {
        model.addAttribute("users", userService.getAllUsers());

        return "list";
    }

    @GetMapping(value = "/new")
    public String addUserForm(@ModelAttribute("user") User user) {
        return "form";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam(value = "id") long id, Model model,
                               RedirectAttributes attributes) {
        User user = userService.readUser(id);

        if (null == user) {
            attributes.addFlashAttribute("flashMessage", "User are not exists!");
            return "redirect:/";
        }

        System.out.println(id);

        model.addAttribute("user", userService.readUser(id));
        return "form";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        userService.createOrUpdateUser(user);
        attributes.addFlashAttribute("flashMessage",
                "User " + user.getFirstName() + " successfully created!");
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id", required = true, defaultValue = "") long id,
                             RedirectAttributes attributes) {
        User user = userService.deleteUser(id);

        attributes.addFlashAttribute("flashMessage", (null == user) ?
                "User are not exists!" :
                "User " + user.getFirstName() + " successfully deleted!");

        return "redirect:/";
    }
}