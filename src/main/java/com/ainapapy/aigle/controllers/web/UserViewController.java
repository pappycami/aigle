package com.ainapapy.aigle.controllers.web;


import com.ainapapy.aigle.models.dto.UserDTO;
import com.ainapapy.aigle.security.AuthController;
import com.ainapapy.aigle.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserViewController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserViewController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model, HttpSession session) {
        
        LOG.debug(">>>> Session ID: " + session.getId());
        
        List<UserDTO> users = userService.getAllUsersFormated();
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

    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validateUserAjax(
        @Valid @RequestBody UserDTO userDTO, 
        BindingResult result) 
    {
        Map<String, String> errors = new HashMap<>();

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
            );
        }
        return ResponseEntity.ok(errors);
    }


    @PostMapping("/save")
    public String saveUser(
            @Valid @ModelAttribute("dto") UserDTO dto,
            BindingResult result,
            RedirectAttributes redirectAttributes) 
    {
         if (result.hasErrors()) {
            return "/edit";
        }
        
        userService.saveUserFromDto(dto);
        redirectAttributes.addFlashAttribute("success", "Utilisateur modifié avec succès");
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes) 
    {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "Utilisateur Supprimée avec succès");
        return "redirect:/users";
    }
}
