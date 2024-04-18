import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { credentialsResponse } from '../credentialsResponse';
import { CredentialsService } from '../credentials.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {


  emailInput: string = '';
  passwordInput: string = '';
  passwordConfirmInput: string = '';

  //checks
  errorMessage: string = ' ';
  unvalidEmail: boolean = false;
  unvalidPassword: boolean = false;
  nonEqualsPasswords: boolean = false;

  constructor(
    public credentialsService: CredentialsService,
    private router: Router,
    
    ) {}

  ngOnInit() {
      this.credentialsService.initSocial();
  }



  /**
   * This method cleans the form inputs.
   */
  cleanFormInputs(){
    this.emailInput = '';
    this.passwordInput = '';
    this.passwordConfirmInput = '';
  }

  /**
   * This method cleans the form selections.
   */
  cleanFormSelections(){
    this.errorMessage = ' ';
    this.unvalidEmail = false;
    this.unvalidPassword = false;
    this.nonEqualsPasswords = false;
    
  }

  /**
   * This method login the user.
   */
  login(){
    this.errorMessage = ' ';
    this.unvalidEmail = false;
    this.unvalidPassword = false;
    this.nonEqualsPasswords = false;

    const validMail :boolean = this.credentialsService.validateMail(this.emailInput);
    const validPassword :string = this.credentialsService.validatePassword(this.passwordInput);

    if(this.emailInput == ''){
      this.unvalidEmail = true;
      this.errorMessage = "Email can't be void";
      return;
    }

    if(!validMail){
      this.unvalidEmail = true;
      this.errorMessage = "Unvalid email";
      return;
    }

    if(this.passwordInput == ''){
      this.errorMessage = "Password can't be void";
      this.unvalidPassword = true;
      return;
    }

    if(validPassword != null){
      this.errorMessage = validPassword;
      this.unvalidPassword = true;
      return;
    }
   
    this.credentialsService.login(this.emailInput, this.passwordInput).subscribe(response => {
      localStorage.setItem('token',response.token);
      
      this.router.navigate(['/home']);
    }, error => {
      var err: credentialsResponse = {
        result: error.error.result,
        status: error.error.status,
        message: error.error.message,
        token: "-",
        appStatus: error.error.appStatus
      }

      if(err.appStatus == "610"){
        this.unvalidEmail = true;
        this.errorMessage = "This email is not registered";
      }

      if(err.appStatus == "611"){
        this.unvalidPassword = true;
        this.errorMessage = "Wrong password";
      }
    }); 
     
  }


  goToRegister(){
    this.router.navigate(['/register']);
  }
}
