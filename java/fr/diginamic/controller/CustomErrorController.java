package fr.diginamic.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;


@Controller
public class CustomErrorController implements ErrorController {

	 @RequestMapping("/error")
	    public ResponseEntity<ErrorDetails> handleError(HttpServletRequest request) {
	        ErrorDetails errorDetails = new ErrorDetails(
	                LocalDateTime.now(),
	                HttpStatus.NOT_FOUND.value(),
	                HttpStatus.NOT_FOUND.getReasonPhrase(),
	                "Page not found",
	                request.getRequestURI()
	        );

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	    }

	    public String getErrorPath() {
	        return "/error";
	    }

	    // Modèle pour les détails d'erreur
	    private static class ErrorDetails {
	        private LocalDateTime timestamp;
	        private int status;
	        private String error;
	        private String message;
	        private String path;

	        public ErrorDetails(LocalDateTime timestamp, int status, String error, String message, String path) {
	            this.timestamp = timestamp;
	            this.status = status;
	            this.error = error;
	            this.message = message;
	            this.path = path;
	        }

	        // Getters (pas nécessaire pour setters car les champs sont initialisés dans le constructeur)
	        public LocalDateTime getTimestamp() {
	            return timestamp;
	        }

	        public int getStatus() {
	            return status;
	        }

	        public String getError() {
	            return error;
	        }

	        public String getMessage() {
	            return message;
	        }

	        public String getPath() {
	            return path;
	        }
	    }
	}