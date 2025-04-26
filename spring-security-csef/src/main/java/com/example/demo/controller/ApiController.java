package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.CsrfTokenResponse;
import jakarta.servlet.http.HttpServletRequest;



@RestController
public class ApiController {


    public Logger logger = LoggerFactory.getLogger(ApiController.class);


    @GetMapping("/")
    public String testcontrolle() {
        return new String("api running successfully");
    }
    

    /**
     * Retrieves the CSRF token associated with the current request.
     *
     * @param request The HttpServletRequest object containing the CSRF token attribute.
     * @return A ResponseEntity containing the CSRF token details if available, or a 404 status if
     *         not.
     */
    @GetMapping("/csrf-token")
    public ResponseEntity<CsrfTokenResponse> getCsrfToken(HttpServletRequest request) {

        logger.info("Processing CSRF token request");

        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");

        if (csrfToken == null) {
            logger.error("CSRF token not found in request");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("csrf token : {}", csrfToken.toString());
        logger.info("CSRF token retrieved successfully");
        return ResponseEntity.ok(new CsrfTokenResponse(csrfToken));
    }

}
