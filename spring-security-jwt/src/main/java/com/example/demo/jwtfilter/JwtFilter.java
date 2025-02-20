package com.example.demo.jwtfilter;

import com.example.demo.customexceptionhandler.JwtTokenExpiredException;
import com.example.demo.jwtutils.JwtUtils;
import com.example.demo.service.UserdetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    
    private UserdetailsServiceImpl userdetailsService;
    
    private JwtUtils jwtUtils;

    @Autowired
    public JwtFilter(UserdetailsServiceImpl userdetailsService, JwtUtils jwtUtils) {
        this.userdetailsService = userdetailsService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * The doFilterInternal method is part of a custom filter in a Spring Boot application, 
     * typically used to process JWT tokens for authentication.
     * It overrides the doFilterInternal method from the OncePerRequestFilter class, ensuring that the filter logic is applied once per request.
     * @param request
     * @param response
     * @param filterChain The filter chain that allows the request to be passed to the next filter or servlet.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Retrieves the Authorization header from the HTTP request.
        String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        
        String jwt = null;

        /**
         * Checks if the Authorization header is not null and starts with "Bearer ".
         * Extracts the JWT token by removing the "Bearer " prefix.
         * Uses jwtUtils.extractUsername(jwt) to extract the username from the JWT token.
         */
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt=authorizationHeader.substring(7);
            
            try {
            	username = jwtUtils.extractUsername(jwt);
			}
            catch (ExpiredJwtException e) {

				// Handle JwtTokenExpiredException
            	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            	response.getWriter().write("token is expired");
            	return;
			}
            catch (Exception e) {

            	// Handle other exceptions related to JWT parsing and validation
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Token is invalid");
                return;
			}
            
            
        }

        /**
         * Checks if the username is not null.
         */
        if (username != null)
        {
            /**
             * Loads the UserDetails using userdetailsService.loadUserByUsername(username).
             */
               UserDetails userDetails = userdetailsService.loadUserByUsername(username);
               
            /**
             * Validates the JWT token using jwtUtils.validateToken(jwt).
             */
            if (jwtUtils.validateToken(jwt))
               {
                   log.info("jwt token is valid");
                   /**
                    * Creates a new UsernamePasswordAuthenticationToken using the UserDetails.
                    */
                   UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userdetailsService,null,userDetails.getAuthorities());
                   
                   log.info("new UsernamePasswordAuthenticationToken is :"+authenticationToken.toString());

                   /**
                    * Sets the details of the authentication token using WebAuthenticationDetailsSource.
                    */
                   authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                   /**
                    * Sets the authentication token in the SecurityContextHolder.
                    */
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               }
        }
        filterChain.doFilter(request,response);
    }
}
