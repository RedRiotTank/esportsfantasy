import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CredentialsService } from '../credentials.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

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
   * This method  registers the user.
   */
  register(){
    this.errorMessage = ' ';
    this.unvalidEmail = false;
    this.unvalidPassword = false;
    this.nonEqualsPasswords = false;

    const validMail :boolean = this.credentialsService.validateMail(this.emailInput);
    const matchPassword : boolean = this.credentialsService.validatePasswordConfirmation(this.passwordInput, this.passwordConfirmInput);
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

    
    if(!matchPassword){
      this.errorMessage = "Passwords do not match";
      this.nonEqualsPasswords = true;
      this.unvalidPassword = true;
      return;
    }
    

    this.credentialsService.register(this.emailInput, this.passwordInput).subscribe(response =>{
      this.router.navigate(['/register-success']);
    }, error => {
      if(error.error.appStatus == "602"){
        this.unvalidEmail = true;
        this.errorMessage = "Email is already in use";
      } else {
        this.router.navigate(['/error']);
      }
    });

     
  }

  goToLogin(){
    this.router.navigate(['/login']);
  }

}
