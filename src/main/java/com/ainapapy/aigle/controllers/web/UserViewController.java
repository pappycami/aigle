package com.ainapapy.aigle.controllers.web;


import com.ainapapy.aigle.models.dto.UserDTO;
import com.ainapapy.aigle.services.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserViewController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<UserDTO> users = userService.getAllUsersFormated();
        System.out.println("Fetched users: " + users);
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "users/form";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<UserDTO> userDTO = userService.getUserByIdFormated(id);
        model.addAttribute("user", userDTO);
        return "users/form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute UserDTO dto) {
        userService.saveUserFromDto(dto);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
