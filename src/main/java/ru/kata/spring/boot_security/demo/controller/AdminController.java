package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String usersInfo(Model model, Principal principal) {
        model.addAttribute("authUser", userService.
                findByUsername(principal.getName()));
        model.addAttribute("authRoles", userService.
                findByUsername(principal.getName()).getRoles());

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "adminPanel";
    }

    @GetMapping("/user-create")
    public String createUserForm(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("currentUser", userService.findByUsername(userDetails.getUsername()));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/user-create")
    public String createUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/user-update/{id}")
    public String updateUser(@PathVariable("id")Long id, @ModelAttribute("user") User user) {
        userService.updateUser(user,id);
        return "redirect:/admin";
    }
    @DeleteMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}



