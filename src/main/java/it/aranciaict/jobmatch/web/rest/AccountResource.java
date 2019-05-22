package it.aranciaict.jobmatch.web.rest;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.repository.UserRepository;
import it.aranciaict.jobmatch.security.SecurityUtils;
import it.aranciaict.jobmatch.service.MailService;
import it.aranciaict.jobmatch.service.UserService;
import it.aranciaict.jobmatch.service.dto.PasswordChangeDTO;
import it.aranciaict.jobmatch.service.dto.UserDTO;
import it.aranciaict.jobmatch.web.rest.errors.EmailAlreadyUsedException;
import it.aranciaict.jobmatch.web.rest.errors.EmailNotFoundException;
import it.aranciaict.jobmatch.web.rest.errors.InternalServerErrorException;
import it.aranciaict.jobmatch.web.rest.errors.InvalidPasswordException;
import it.aranciaict.jobmatch.web.rest.errors.LoginAlreadyUsedException;
import it.aranciaict.jobmatch.web.rest.vm.CandidateManagedUserVM;
import it.aranciaict.jobmatch.web.rest.vm.CompanyManagedUserVM;
import it.aranciaict.jobmatch.web.rest.vm.KeyAndPasswordVM;
import it.aranciaict.jobmatch.web.rest.vm.ManagedUserVM;
import it.aranciaict.jobmatch.web.rest.vm.SponsoringInstitutionManagedUserVM;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    /** The user repository. */
    private final UserRepository userRepository;

    /** The user service. */
    private final UserService userService;

    /** The mail service. */
    private final MailService mailService;

    /**
     * Instantiates a new account resource.
     *
     * @param userRepository the user repository
     * @param userService the user service
     * @param mailService the mail service
     */
    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already used
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword(), managedUserVM.getRoleAccount());
        mailService.sendActivationEmail(user);
    }

    
    /**
     * Register candidate account.
     *
     * @param candidateUserVM the candidate user VM
     */
    @PostMapping("/register-candidate")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCandidateAccount(@Valid @RequestBody CandidateManagedUserVM candidateUserVM) {
    	registerAccount(candidateUserVM);
    }
    
    /**
     * Register company account.
     *
     * @param companyUserVM the company user VM
     */
    @PostMapping("/register-company")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCompanyAccount(@Valid @RequestBody CompanyManagedUserVM companyUserVM) {
    	registerAccount(companyUserVM);
    }
    
    /**
     * Register instituition account.
     *
     * @param instituteUserVM the institute user VM
     */
    @PostMapping("/register-institution")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerInstituitionAccount(@Valid @RequestBody SponsoringInstitutionManagedUserVM instituteUserVM) {
    	registerAccount(instituteUserVM);
    }
    
    
    
    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        UserDTO userDTO = userService.getUserWithAuthoritiesAsDTO();
        if(userDTO == null) {
            throw new InternalServerErrorException("User cannot be found");
        }
        
        return userDTO;
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
    }

    /**
     * POST  /account/change-password : changes the current user's password.
     *
     * @param passwordChangeDto current and new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    /**
     * Check password length.
     *
     * @param password the password
     * @return true, if successful
     */
    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
