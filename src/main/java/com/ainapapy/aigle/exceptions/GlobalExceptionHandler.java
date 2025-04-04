package com.ainapapy.aigle.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.ModelAndView;

@Getter
@Setter
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private Model model;
    private HttpStatus status;
    private String message;
    private String exMessage;
    private String template = "default";
      
    // Gérer les erreurs 400 (Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleBadRequest(Exception ex, HttpServletRequest request, Model m) {
        return performErrorResponse(m,request, HttpStatus.BAD_REQUEST, "Requête invalide", ex.getMessage(), "400");
    }

    // Gérer les erreurs 403 (Accès interdit)
    @ExceptionHandler(SecurityException.class)
    public Object handleForbidden(Exception ex, HttpServletRequest request, Model m) {
        return performErrorResponse(m,request,HttpStatus.FORBIDDEN, "Accès interdit", ex.getMessage(), "403");
    }

    // Gérer les erreurs 404 (Non trouvé)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleNotFound(Exception ex, HttpServletRequest request, Model m) {
        return performErrorResponse(m,request,HttpStatus.NOT_FOUND, "Ressource introuvable", ex.getMessage(),"404");
    }
    
    // Gérer les erreurs 404 (Non trouvé)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object noHandlerFound(Exception ex, HttpServletRequest request, Model m) {
        return performErrorResponse(m,request,HttpStatus.NOT_FOUND, "truc introuvable", ex.getMessage(), "404");
    }
    
    // Gérer les erreurs 404 (Non trouvé)
    @ExceptionHandler(UserNotFoundException.class)
    public Object userNotFound(Exception ex, HttpServletRequest request, Model m) {
        return performErrorResponse(m,request,HttpStatus.NOT_FOUND, "Utilisateur introuvable", ex.getMessage(),"404");
    }

    // Gérer les erreurs 500 (Erreur interne)
    @ExceptionHandler(Exception.class)
    public Object handleInternalError(Exception ex, HttpServletRequest request, Model m) {
        return performErrorResponse(m,request,HttpStatus.INTERNAL_SERVER_ERROR, "Erreur serveur", ex.getMessage(), "500");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors); // 400 avec détails
    }


    // Générer une réponse JSON standardisée
    private ResponseEntity<Map<String, Object>> generateErrorApiResponse() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", this.status.value());
        body.put("error", this.message);
        body.put("message", this.exMessage);
        return new ResponseEntity<>(body, this.status);
    }
      
    // Générer une réponse JSON standardisée
    private Object generateErrorWebResponse() {
        // 📌 Retourne la page 404.html si c'est un navigateur;
        model.addAttribute("errorCode", this.status.value());
        model.addAttribute("errorMessage", this.exMessage);
        return new ModelAndView("error/"+this.template, this.model.asMap());
    }

    // 🔹 Vérifier si la requête est une API ou une requête Web
    private boolean isApiRequest(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        return acceptHeader != null && acceptHeader.contains("application/json");
    }
    
    private Object performErrorResponse(Model model, HttpServletRequest request, HttpStatus status, String message, String exMessage, String template) {
        this.model     = model;
        this.status    = status;
        this.message   = message;
        this.exMessage = exMessage;
        this.template  = template;
        
        if(isApiRequest(request)){
            return generateErrorApiResponse();
        } else {
            return generateErrorWebResponse();
        } 
    }
}
