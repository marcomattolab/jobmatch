package it.aranciaict.jobmatch.web.rest;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.aranciaict.jobmatch.security.jwt.JWTFilter;
import it.aranciaict.jobmatch.security.jwt.TokenProvider;
import it.aranciaict.jobmatch.web.rest.vm.LoginVM;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    /** The token provider. */
    private final TokenProvider tokenProvider;

    /** The authentication manager. */
    private final AuthenticationManager authenticationManager;

    /**
     * Instantiates a new user JWT controller.
     *
     * @param tokenProvider the token provider
     * @param authenticationManager the authentication manager
     */
    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authorize.
     *
     * @param loginVM the login VM
     * @return the response entity
     */
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        /** The id token. */
        private String idToken;

        /**
         * Instantiates a new JWT token.
         *
         * @param idToken the id token
         */
        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        /**
         * Gets the id token.
         *
         * @return the id token
         */
        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        /**
         * Sets the id token.
         *
         * @param idToken the new id token
         */
        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
