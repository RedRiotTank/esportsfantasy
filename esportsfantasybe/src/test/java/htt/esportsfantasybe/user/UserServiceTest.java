package htt.esportsfantasybe.user;

import htt.esportsfantasybe.DTO.SocialUserDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.config.JwtService;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.repository.UserRepository;
import htt.esportsfantasybe.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testValidateMailValidEmail() {
        String validEmail = "test@mail.com";
        boolean result = userService.validateMail(validEmail);
        Assertions.assertTrue(result, "Mail should be ok");
    }

    @Test
    public void testValidateMailInvalidEmail() {
        String invalidEmail = "testmail.com";
        boolean result = userService.validateMail(invalidEmail);
        Assertions.assertFalse(result, "EMail should be ko");
    }

    @Test
    public void testInUseMail() {
        String mail = "test@mail.com";
        when(userRepository.existsUserByMail(mail)).thenReturn(true);
        boolean result = userService.inUseMail(mail);
        Assertions.assertTrue(result, "Mail should be in use");
    }

    @Test
    public void testNonInUseMail() {
        String mail = "test@mail.com";
        when(userRepository.existsUserByMail(mail)).thenReturn(false);
        boolean result = userService.inUseMail(mail);
        Assertions.assertFalse(result, "Mail should not be in use");
    }

    @Test
    public void testValidatePassInvalidPass() {
        String validPass = "testPass";
        boolean result = userService.validatePass(validPass);
        Assertions.assertFalse(result, "Pass should be ko");
    }

    @Test
    public void testValidatePassValidPass() {
        String invalidPass = "Password113";
        boolean result = userService.validatePass(invalidPass);
        Assertions.assertTrue(result, "Pass should be ok");
    }

    @Test
    public void testValidSignup() {
        UserDTO userDTO = new UserDTO("test@mail.com", "Password113");
        when(userRepository.existsUserByMail(userDTO.getMail())).thenReturn(false);
        userService.signup(userDTO);
        verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void testInvalidSignupMail() {
        UserDTO userDTO = new UserDTO("test", "Password113");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.signup(userDTO);
        });

        assertEquals("1005", thrown.getMessage(), "Exception message should be '1005'");
    }

    @Test
    public void testInvalidSignupMailInUse() {
        UserDTO userDTO = new UserDTO("test@mail.com", "Password113");
        when(userRepository.existsUserByMail(userDTO.getMail())).thenReturn(true);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.signup(userDTO);
        });

        assertEquals("1006", thrown.getMessage(), "Exception message should be '1006'");
    }

    @Test
    public void testInvalidSignupPass1() {
        UserDTO userDTO = new UserDTO("test@mail.com", "testPass");
        when(userRepository.existsUserByMail(userDTO.getMail())).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.signup(userDTO);
        });

        assertEquals("1007", thrown.getMessage(), "Exception message should be '1007'");

    }

    @Test
    public void testInvalidSignupPass2() {
        UserDTO userDTO = new UserDTO("test@mail.com", "PASSWORD113");
        when(userRepository.existsUserByMail(userDTO.getMail())).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.signup(userDTO);
        });

        assertEquals("1007", thrown.getMessage(), "Exception message should be '1007'");

    }

    @Test
    public void testInvalidSignupPass3() {
        UserDTO userDTO = new UserDTO("test@mail.com", "password113");
        when(userRepository.existsUserByMail(userDTO.getMail())).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.signup(userDTO);
        });

        assertEquals("1007", thrown.getMessage(), "Exception message should be '1007'");
    }

    @Test
    public void testValidLogin() {
        UserDTO userDTO = new UserDTO("test@mail.com", "Password113");
        when(userRepository.existsUserByMail(userDTO.getMail())).thenReturn(false);
        userService.signup(userDTO);
        verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void testNotFoundMailLogin() {
        UserDTO userDTO = new UserDTO("test@mail.com", "Password113");
        Optional<User> emptyUser = Optional.empty();
        when(userRepository.findByMail(userDTO.getMail())).thenReturn(emptyUser);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.login(userDTO);
        });

        assertEquals("1001", thrown.getMessage(), "Exception message should be '1001'");

    }

    @Test
    public void testInvalidPassLogin() {
        UserDTO userDTO = new UserDTO("test@mail.com", "Password113");
        Optional<User> otherPasswordUser = Optional.of(new User(userDTO));
        otherPasswordUser.get().setPass("OtherPassword");

        when(userRepository.findByMail(userDTO.getMail())).thenReturn(otherPasswordUser);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.login(userDTO);
        });

        assertEquals("1002", thrown.getMessage(), "Exception message should be '1002'");
    }

    @Test
    public void testValidLoginWithToken() {
        UserDTO userDTO = new UserDTO("test@mail.com", "Password113");
        userDTO.setUuid(UUID.randomUUID());
        User user = new User(userDTO);

        String token = "valid-token";

        when(jwtService.extractUserEmail(token)).thenReturn(userDTO.getMail());
        when(jwtService.isTokenValid(eq(token), any(UserDetails.class))).thenReturn(true);

        when(userRepository.findByMail(userDTO.getMail())).thenReturn(Optional.of(user));

        String result = userService.loginWithToken(token);

        assertEquals(token, result, "The token should be returned if valid.");

        verify(jwtService).extractUserEmail(token);
        verify(userRepository).findByMail(userDTO.getMail());
        verify(jwtService).isTokenValid(eq(token), any(UserDetails.class));
    }

    @Test
    void testGoogleLoginUnvalidGoogleToken() {

        when(jwtService.verifyGoogleToken(any())).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.googleLogin(new SocialUserDTO("test@mail.com", "idToken", "name",null));
        });

        assertEquals("1003", thrown.getMessage(), "Exception message should be '1003'");
    }
}