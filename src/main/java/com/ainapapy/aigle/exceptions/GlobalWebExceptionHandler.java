package com.ainapapy.aigle.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalWebExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleBadRequest(Exception ex) {
        return buildModelAndView(HttpStatus.BAD_REQUEST, "Requête invalide", ex.getMessage(), "400");
    }

    @ExceptionHandler(SecurityException.class)
    public ModelAndView handleForbidden(Exception ex) {
        return buildModelAndView(HttpStatus.FORBIDDEN, "Accès interdit", ex.getMessage(), "403");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView noHandlerFound(Exception ex) {
        return buildModelAndView(HttpStatus.NOT_FOUND, "Page non trouvée", ex.getMessage(), "404");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView userNotFound(Exception ex) {
        return buildModelAndView(HttpStatus.NOT_FOUND, "Utilisateur introuvable", ex.getMessage(), "404");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleInternalError(Exception ex) {
        return buildModelAndView(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur serveur", ex.getMessage(), "500");
    }

    private ModelAndView buildModelAndView(HttpStatus status, String message, String exMessage, String template) {
        ModelAndView mav = new ModelAndView("error/" + template);
        mav.addObject("errorCode", status.value());
        mav.addObject("errorMessage", exMessage != null ? exMessage : message);
        return mav;
    }
}
