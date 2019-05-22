package it.aranciaict.jobmatch.service.mapper;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.aranciaict.jobmatch.JobmatchApp;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.service.dto.UserDTO;

/**
 * Test class for the UserMapper.
 *
 * @see UserMapper
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class UserMapperTest {

    /** The Constant DEFAULT_LOGIN. */
    private static final String DEFAULT_LOGIN = "johndoe";

    /** The user mapper. */
    @Autowired
    private UserMapper userMapper;

    /** The user. */
    private User user;
    
    /** The user dto. */
    private UserDTO userDto;

    /** The Constant DEFAULT_ID. */
    private static final Long DEFAULT_ID = 1L;

    /**
     * Inits the.
     */
    @Before
    @SuppressWarnings("checkstyle:magicNumber")
    public void init() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setImageUrl("image_url");
        user.setLangKey("en");

        userDto = new UserDTO(user);
    }

    /**
     * Users to user DT os should map only non null users.
     */
    @Test
    public void usersToUserDTOsShouldMapOnlyNonNullUsers(){
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(null);

        List<UserDTO> userDTOS = userMapper.usersToUserDTOs(users);

        assertThat(userDTOS).isNotEmpty();
        assertThat(userDTOS).size().isEqualTo(1);
    }

    /**
     * User DT os to users should map only non null users.
     */
    @Test
    public void userDTOsToUsersShouldMapOnlyNonNullUsers(){
        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);
        usersDto.add(null);

        List<User> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
    }

    /**
     * User DT os to users with authorities string should map to users with authorities domain.
     */
    @Test
    public void userDTOsToUsersWithAuthoritiesStringShouldMapToUsersWithAuthoritiesDomain(){
        Set<String> authoritiesAsString = new HashSet<>();
        authoritiesAsString.add("ADMIN");
        userDto.setAuthorities(authoritiesAsString);

        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        List<User> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
        assertThat(users.get(0).getAuthorities()).isNotNull();
        assertThat(users.get(0).getAuthorities()).isNotEmpty();
        assertThat(users.get(0).getAuthorities().iterator().next().getName()).isEqualTo("ADMIN");
    }

    /**
     * User DT os to users map with null authorities string should return user with empty authorities.
     */
    @Test
    public void userDTOsToUsersMapWithNullAuthoritiesStringShouldReturnUserWithEmptyAuthorities(){
        userDto.setAuthorities(null);

        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        List<User> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
        assertThat(users.get(0).getAuthorities()).isNotNull();
        assertThat(users.get(0).getAuthorities()).isEmpty();
    }

    /**
     * User DTO to user map with authorities string should return user with authorities.
     */
    @Test
    public void userDTOToUserMapWithAuthoritiesStringShouldReturnUserWithAuthorities(){
        Set<String> authoritiesAsString = new HashSet<>();
        authoritiesAsString.add("ADMIN");
        userDto.setAuthorities(authoritiesAsString);

        userDto.setAuthorities(authoritiesAsString);

        User user = userMapper.userDTOToUser(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getAuthorities()).isNotNull();
        assertThat(user.getAuthorities()).isNotEmpty();
        assertThat(user.getAuthorities().iterator().next().getName()).isEqualTo("ADMIN");
    }

    /**
     * User DTO to user map with null authorities string should return user with empty authorities.
     */
    @Test
    public void userDTOToUserMapWithNullAuthoritiesStringShouldReturnUserWithEmptyAuthorities(){
        userDto.setAuthorities(null);

        User user = userMapper.userDTOToUser(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getAuthorities()).isNotNull();
        assertThat(user.getAuthorities()).isEmpty();
    }

    /**
     * User DTO to user map with null user should return null.
     */
    @Test
    public void userDTOToUserMapWithNullUserShouldReturnNull(){
        assertThat(userMapper.userDTOToUser(null)).isNull();
    }

    /**
     * Test user from id.
     */
    @Test
    public void testUserFromId() {
        assertThat(userMapper.userFromId(DEFAULT_ID).getId()).isEqualTo(DEFAULT_ID);
        assertThat(userMapper.userFromId(null)).isNull();
    }
}
