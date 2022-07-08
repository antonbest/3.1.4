package ru.kata.spring.boot_security.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.servise.RoleServise;

@Controller
public class UserController {

    private final RoleServise roleServise;


    public UserController(RoleServise roleServise) {
        this.roleServise = roleServise;
    }


    @GetMapping("/user")
    public String userInfo() {
        return "userApp";
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("amountOfRoles", roleServise.getAllRoles());
        return "adminApp";
    }


}
