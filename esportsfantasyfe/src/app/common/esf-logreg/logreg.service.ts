import { Injectable } from '@angular/core';
import { ApiService } from '../API/api.service';
import { logregResponse } from '../API/logregResponse';
import { Observable } from 'rxjs';
import { GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';

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



  login(mail: string, pass: string) :Observable<logregResponse> {

    const data = {
      mail: mail,
      pass: pass
    };

    return this.api.sendRequest("login",data);

  }

  register(mail: string, pass: string) :Observable<logregResponse> {

    const data = {
      mail: mail,
      pass: pass
    };

    return this.api.sendRequest("signup",data);

  }

  validateMail(email: string): boolean {
    const emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  validatePasswordConfirmation(pass: string, passConfirm: string){
    return pass == passConfirm;
  }

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

  loginSocial(){

    console.log("social: ", this.social_user);
    

    const data = {
      mail: this.social_user.email,
      idToken: this.social_user.idToken,
      name: this.social_user.name
    };

    console.log(data);
    
    console.log("sent");
    
    this.api.sendRequest("googleLogin",data).subscribe(response =>{

    }, error =>{

    });


    
  }
  

}
