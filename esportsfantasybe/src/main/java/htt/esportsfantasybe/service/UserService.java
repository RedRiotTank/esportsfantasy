package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.DTO.SocialUserDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.config.JwtService;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a service class that handles the business logic of the User entity.
 * It is used to interact with the User repository.
 * @author Alberto Plaza Montes
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * This method is used to sign up a new user.
     * It validates the mail and password of the user.
     * It also checks if the mail is already in use.
     * @param newUserDTO The user to be signed up.
     */
    public void signup(UserDTO newUserDTO){

        if(!validateMail(newUserDTO.getMail())){
            throw new RuntimeException("601");
        }

        if(inUseMail(newUserDTO.getMail())){
            throw new RuntimeException("602");
        }

        if(!validatePass(newUserDTO.getPass())){
            throw new RuntimeException("603");
        }

        newUserDTO.setPass(passwordEncoder.encode(newUserDTO.getPass()));

        User newUser = new User(newUserDTO);


        userRepository.save(newUser);
    }

    /**
     * This method is used to log in a user.
     * It checks if the user exists and if the password is correct.
     * @param newUserDTO The user to be logged in.
     * @return The JWT token of the user.
     */
    public String login(UserDTO newUserDTO){
        String credentialMail = newUserDTO.getMail();
        String credentialPass = newUserDTO.getPass();

        Optional<User> loginUser = userRepository.findByMail(credentialMail);
        if(loginUser.isEmpty()) throw new RuntimeException("610");

        boolean matches = passwordEncoder.matches(credentialPass,loginUser.get().getPass());
        if(!matches) throw new RuntimeException("611");

        return JwtService.generateToken(newUserDTO);
    }

    /**
     * This method is used to log in a user with Google.
     * It checks if the Google token is valid.
     * If the user does not exist, it creates a new user.
     * If the user exists and has a password, it throws an exception.
     * @param newSocialUserDTO The user to be logged in.
     * @return The JWT token of the user.
     */
    public String googleLogin(SocialUserDTO newSocialUserDTO){
        String credentialMail = newSocialUserDTO.getMail();
        String credentialToken = newSocialUserDTO.getIdToken();

        boolean validToken = JwtService.verifyGoogleToken(credentialToken);
        if(!validToken) throw new RuntimeException("620");

        Optional<User> loginUser = userRepository.findByMail(credentialMail);

        if(loginUser.isEmpty()) {
            User newUser = new User(new UserDTO(newSocialUserDTO));
            userRepository.save(newUser);
        } else if(loginUser.get().getPass() != null) throw new RuntimeException("621");

        return JwtService.generateToken(newSocialUserDTO);
    }

    /**
     * This method generates a random username.
     * @return The generated username.
     */
    public static String generaetUsername() {
        Random random = new Random();
        String randNums = String.format("%04d", random.nextInt(10000));
        return "username" + randNums;

    }

    /**
     * This method is used to validate the mail of a user.
     * @param mail The mail to be validated.
     * @return True if the mail is valid, false otherwise.
     */
    public boolean validateMail(String mail){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(mail);

        return matcher.matches();
    }

    /**
     * This method is used to check if a mail is already in use.
     * @param mail The mail to be checked.
     * @return True if the mail is in use, false otherwise.
     */
    public boolean inUseMail(String mail){
        return userRepository.existsUserByMail(mail);
    }

    /**
     * This method is used to validate the password of a user.
     * @param pass The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    public static boolean validatePass(String pass){
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(pass);

        return matcher.matches();
    }

    public List<UserDTO> getAllUsers(){

        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(UserDTO::new).toList();

    }

    public List<LeagueDTO> getUserLeagues(UUID useruuid){
        Optional<User> user = userRepository.findById(useruuid);
        return user.get().getLeagues().stream().map(LeagueDTO::new).toList();
    }

    public byte[] getUserPfp(String mail) throws IOException {
        Optional<User> user = userRepository.findByMail(mail);

        if (user.isEmpty()) throw new RuntimeException("numerr");

        UUID useruuid = user.get().getUuid();

        Path imagePath;
        imagePath = Paths.get("src/main/resources/media/pfp/" + useruuid + ".png");

        byte[] imageBytes;

        try {
            imageBytes = Files.readAllBytes(imagePath);

        } catch (IOException e) {
            try {
                imageBytes = Files.readAllBytes(Paths.get("src/main/resources/media/pfp/default.png"));
            } catch (IOException ioException) {
                throw new RuntimeException("numerr");
            }
        }
            return imageBytes;
    }

    public UserDTO getUser(String mail){
        Optional<User> user = userRepository.findByMail(mail);

        User getUser = user.orElseThrow(() -> new RuntimeException("Could not find user with mail: " + mail + "."));

        return new UserDTO(getUser);
    }



}
