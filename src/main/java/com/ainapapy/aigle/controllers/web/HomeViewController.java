package com.ainapapy.aigle.controllers.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeViewController {

    @GetMapping
    public String homepage() {
        return "index";
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }
    
    // Affiche le formulaire de login personnalisé
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login"; // => /templates/auth/login.html
    }

    // Facultatif : redirection après logout
    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "auth/logout-success"; // => page optionnelle après déconnexion
    }
}
