package com.ainapapy.aigle.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Gérer les erreurs 400 (Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex, WebRequest request) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Requête invalide", ex.getMessage());
    }

    // Gérer les erreurs 403 (Accès interdit)
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(Exception ex, WebRequest request) {
        return generateErrorResponse(HttpStatus.FORBIDDEN, "Accès interdit", ex.getMessage());
    }

    // Gérer les erreurs 404 (Non trouvé)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(Exception ex, WebRequest request) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Ressource introuvable", ex.getMessage());
    }
    
    // Gérer les erreurs 404 (Non trouvé)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> noHandlerFound(Exception ex, WebRequest request) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "truc introuvable", ex.getMessage());
    }
    
    // Gérer les erreurs 404 (Non trouvé)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> userNotFound(Exception ex, WebRequest request) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Utilisateur introuvable", ex.getMessage());
    }

    // Gérer les erreurs 500 (Erreur interne)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleInternalError(Exception ex, WebRequest request) {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur serveur", ex.getMessage());
    }

    // Générer une réponse JSON standardisée
    private ResponseEntity<Map<String, Object>> generateErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
    
}
