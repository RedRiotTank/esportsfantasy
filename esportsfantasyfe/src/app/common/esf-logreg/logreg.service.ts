import { Injectable } from '@angular/core';
import { ApiService } from '../API/api.service';
import { logregResponse } from '../API/logregResponse';
import { Observable } from 'rxjs';
import { GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';

/**
 * This service is responsible for managing the login and register of the application.
 * It provides the methods to login and register.
 * It also provides the methods to validate the email and password.
 * It also provides the methods to login with Google.
 * @author Alberto Plaza Montes.
 */
@Injectable({
  providedIn: 'root'
})
export class LogregService {

  social_user: SocialUser;
  loggedIn: boolean;
  social_started: boolean = false;

  constructor(
    private api: ApiService,
    private socialAuthService: SocialAuthService
    ) { }


  /**
   * This method sends a login request to the server.
   * @param mail user mail.
   * @param pass user password.
   * @returns Obeservable to subscribe.
   */
  login(mail: string, pass: string) :Observable<logregResponse> {

    const data = {
      mail: mail,
      pass: pass
    };

    return this.api.sendRequest("user/login",data);

  }

  /**
   * This method sends a register request to the server.
   * @param mail mail of the user.
   * @param pass password of the user.
   * @returns observable to subscribe.
   */
  register(mail: string, pass: string) :Observable<logregResponse> {

    const data = {
      mail: mail,
      pass: pass
    };

    return this.api.sendRequest("user/signup",data);

  }

  /**
   * This method sends a logout request to the server.
   * @returns true if the mail is valid, false otherwise.
   */
  validateMail(email: string): boolean {
    const emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  /**
   * This method 
   * @param pass 
   * @param passConfirm 
   * @returns true if the password was correct, false otherwise.
   */
  validatePasswordConfirmation(pass: string, passConfirm: string){
    return pass == passConfirm;
  }

  /**
   * This method validates the password.
   * @param password the password to validate.
   * @returns null if the password is correct, a string with the error otherwise.
   */
  validatePassword(password: string): string | null {
    if (!/[A-Z]/.test(password)) {
      return "Password must contain at least one uppercase letter.";
    }
  

    if (!/[a-z]/.test(password)) {
      return "Password must contain at least one lowercase letter.";
    }
  
    
    if (!/\d/.test(password)) {
      return "Password must contain at least one digit.";
    }
  
   
    if (password.length < 8) {
      return "Password must be at least 8 characters long.";
    }
  
   
    return null;
  }

  // ---- Social ----

  /**
   * This method initializes the social login.
   */
  initSocial(){
    if(!this.social_started){
      this.socialAuthService.authState.subscribe((user) => {
        this.social_user = user;
        this.loggedIn = (user != null);
        this.loginSocial();
      });
    }

    this.social_started = true;
  }

  /**
   * This method send a login request to the server with the social user.
   */
  loginSocial(){

    console.log("social: ", this.social_user);
    

    const data = {
      mail: this.social_user.email,
      idToken: this.social_user.idToken,
      name: this.social_user.name
    };

    console.log(data);
    
    console.log("sent");
    
    this.api.sendRequest("user/googleLogin",data).subscribe(response =>{

    }, error =>{

    });


    
  }
  

}
