package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.SocialUserDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.config.JwtService;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * The UserController class is a RESTful controller that handles HTTP requests
 * for the User entity.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * The UserController constructor injects the UserService class.
     * @param userService the service class that handles the business logic for the User entity
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * The signup method is a POST request that creates a new User entity.
     * @param newUserDTO the UserDTO object that contains the new User entity's data
     * @return a ResponseEntity object that contains the new User entity
     * @author Alberto Plaza Montes
     */
    @CrossOrigin
    @PostMapping(value ="/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@RequestBody UserDTO newUserDTO) {
        try {
            userService.signup(newUserDTO);

            return okresponses.createdNewUser();
        } catch(Exception e) {
            return koresponses.createdNewUser(e.getMessage());
        }
    }

    /**
     * The login method is a POST request that logs in an existing User entity.
     * @param newUserDTO the UserDTO object that contains the User entity's data
     * @return a ResponseEntity object that contains the User entity's data
     */
    @CrossOrigin
    @PostMapping(value ="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UserDTO newUserDTO){
        try{
            String token = userService.login(newUserDTO);

            return okresponses.loginUser(token);
        } catch (Exception e){
            return koresponses.login(e.getMessage());
        }
    }

    /**
     * The googlelogin method is a POST request that logs in an existing User entity using Google.
     * @param newSocialUserDTO the SocialUserDTO object that contains the User entity's data
     * @return a ResponseEntity object that contains the User entity's data
     */
    @CrossOrigin
    @PostMapping(value ="/googleLogin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> googlelogin(@RequestBody SocialUserDTO newSocialUserDTO){
        try{
            String token = userService.googleLogin(newSocialUserDTO);

            return okresponses.loginUser(token);
        } catch (Exception e){
            return koresponses.googleLogin(e.getMessage());
        }
    }

}

