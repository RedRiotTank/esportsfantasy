package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.SocialUserDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.config.JwtService;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    public String login(UserDTO newUserDTO){
        String credentialMail = newUserDTO.getMail();
        String credentialPass = newUserDTO.getPass();

        Optional<User> loginUser = userRepository.findByMail(credentialMail);
        if(loginUser.isEmpty()) throw new RuntimeException("610");

        boolean matches = passwordEncoder.matches(credentialPass,loginUser.get().getPass());
        if(!matches) throw new RuntimeException("611");

        return JwtService.generateToken(newUserDTO);
    }

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

    public static String generaetUsername() {
        Random random = new Random();
        String randNums = String.format("%04d", random.nextInt(10000));
        String username = "username" + randNums;
        return username;
    }

    public boolean validateMail(String mail){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(mail);

        return matcher.matches();
    }

    public boolean inUseMail(String mail){
        return userRepository.existsUserByMail(mail);
    }

    public static boolean validatePass(String pass){
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(pass);

        return matcher.matches();
    }
}
