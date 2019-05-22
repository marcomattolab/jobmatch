package it.aranciaict.jobmatch.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.JobmatchApp;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.repository.UserRepository;

/**
 * Test class for DomainUserDetailsService.
 *
 * @see DomainUserDetailsService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
@Transactional
public class DomainUserDetailsServiceIntTest {

    /** The Constant USER_ONE_LOGIN. */
    private static final String USER_ONE_LOGIN = "test-user-one";
    
    /** The Constant USER_ONE_EMAIL. */
    private static final String USER_ONE_EMAIL = "test-user-one@localhost";
    
    /** The Constant USER_TWO_LOGIN. */
    private static final String USER_TWO_LOGIN = "test-user-two";
    
    /** The Constant USER_TWO_EMAIL. */
    private static final String USER_TWO_EMAIL = "test-user-two@localhost";
    
    /** The Constant USER_THREE_LOGIN. */
    private static final String USER_THREE_LOGIN = "test-user-three";
    
    /** The Constant USER_THREE_EMAIL. */
    private static final String USER_THREE_EMAIL = "test-user-three@localhost";

    /** The user repository. */
    @Autowired
    private UserRepository userRepository;

    /** The domain user details service. */
    @Autowired
    private UserDetailsService domainUserDetailsService;

    /** The user one. */
    private User userOne;
    
    /** The user two. */
    private User userTwo;
    
    /** The user three. */
    private User userThree;

    /**
     * Inits the.
     */
    @Before
    @SuppressWarnings("checkstyle:magicNumber")
    public void init() {
        userOne = new User();
        userOne.setLogin(USER_ONE_LOGIN);
        userOne.setPassword(RandomStringUtils.random(60));
        userOne.setActivated(true);
        userOne.setEmail(USER_ONE_EMAIL);
        userOne.setFirstName("userOne");
        userOne.setLastName("doe");
        userOne.setLangKey("en");
        userRepository.save(userOne);

        userTwo = new User();
        userTwo.setLogin(USER_TWO_LOGIN);
        userTwo.setPassword(RandomStringUtils.random(60));
        userTwo.setActivated(true);
        userTwo.setEmail(USER_TWO_EMAIL);
        userTwo.setFirstName("userTwo");
        userTwo.setLastName("doe");
        userTwo.setLangKey("en");
        userRepository.save(userTwo);

        userThree = new User();
        userThree.setLogin(USER_THREE_LOGIN);
        userThree.setPassword(RandomStringUtils.random(60));
        userThree.setActivated(false);
        userThree.setEmail(USER_THREE_EMAIL);
        userThree.setFirstName("userThree");
        userThree.setLastName("doe");
        userThree.setLangKey("en");
        userRepository.save(userThree);
    }

    /**
     * Assert that user can be found by login.
     */
    @Test
    @Transactional
    public void assertThatUserCanBeFoundByLogin() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    /**
     * Assert that user can be found by login ignore case.
     */
    @Test
    @Transactional
    public void assertThatUserCanBeFoundByLoginIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN.toUpperCase(Locale.ENGLISH));
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    /**
     * Assert that user can be found by email.
     */
    @Test
    @Transactional
    public void assertThatUserCanBeFoundByEmail() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_LOGIN);
    }

    /**
     * Assert that user can not be found by email ignore case.
     */
    @Test(expected = UsernameNotFoundException.class)
    @Transactional
    public void assertThatUserCanNotBeFoundByEmailIgnoreCase() {
    domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL.toUpperCase(Locale.ENGLISH));
    }

    /**
     * Assert that email is prioritized over login.
     */
    @Test
    @Transactional
    public void assertThatEmailIsPrioritizedOverLogin() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    /**
     * Assert that user not activated exception is thrown for not activated users.
     */
    @Test(expected = UserNotActivatedException.class)
    @Transactional
    public void assertThatUserNotActivatedExceptionIsThrownForNotActivatedUsers() {
        domainUserDetailsService.loadUserByUsername(USER_THREE_LOGIN);
    }

}
