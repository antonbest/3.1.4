package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.servise.RoleServise;
import ru.kata.spring.boot_security.demo.servise.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleServise roleServise;

    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository, RoleServise roleServise) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleServise = roleServise;
    }


    public Set<Role> getRoles(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add((Role) roleRepository.findByName(role));
        }
        return roleSet;
    }


    @GetMapping("/user")
    public String userInfo(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String adminInfo(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("userMain", user);
        model.addAttribute("roles", roleServise.getAllRoles());
        return "admin";
    }



    @PostMapping("/admin/create")
    public String addUser(User user, @RequestParam ("listRoles") long[] roles) {
        userService.saveUser(user,roles);
        return "redirect:/admin";
    }


    @PostMapping("/admin/update")
    public String update(@ModelAttribute("user") User user, @RequestParam("listRoles") long[] roleId) {
        userService.updateUser(user, roleId);
        return "redirect:/admin";
    }


    @PostMapping("/admin/delete/{id}")
    public String removeUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
