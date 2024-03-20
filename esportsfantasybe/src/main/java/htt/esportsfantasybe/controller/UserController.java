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


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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

