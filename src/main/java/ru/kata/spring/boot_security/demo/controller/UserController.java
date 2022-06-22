package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.servise.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public Set<Role> getRoles(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add((Role) roleRepository.findByName(role));
        }
        return roleSet;
    }

    @GetMapping("/user")
    public String userInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String adminInfo(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "user-list";
    }

    @GetMapping("/admin/user-update/{id}")
    public String getUserById(@PathVariable("id") Integer id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/admin/{id}")
    public String updateUser(User user, @PathVariable Integer id, @RequestParam(value = "role") String[] roles) {
        user.setRoles(getRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser (@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles) {
        user.setRoles(getRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

}
