import { Component, OnInit } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { LogregService } from '../logreg.service';
import { logregResponse } from '../../API/logregResponse';
import { Router } from '@angular/router';
import { GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';


@Component({
  selector: 'app-esf-lofreg-modal',
  templateUrl: './esf-lofreg-modal.component.html',
  styleUrl: './esf-lofreg-modal.component.scss'
})
export class EsfLofregModalComponent implements OnInit {
  isRegister: boolean = false;
  
  
  emailInput: string = '';
  passwordInput: string = '';
  passwordConfirmInput: string = '';

  //checks
  errorMessage: string = ' ';
  unvalidEmail: boolean = false;
  unvalidPassword: boolean = false;
  nonEqualsPasswords: boolean = false;

  constructor(
    public modalRef: MdbModalRef<EsfLofregModalComponent>,
    public logregService: LogregService,
    private router: Router,
    
    ) {}

  ngOnInit() {
      this.logregService.initSocial();
  }

  changeModalType(){
    this.isRegister = !this.isRegister;
    this.cleanFormSelections();
    this.cleanFormInputs();
  }

  cleanFormInputs(){
    this.emailInput = '';
    this.passwordInput = '';
    this.passwordConfirmInput = '';
  }

  cleanFormSelections(){
    this.errorMessage = ' ';
    this.unvalidEmail = false;
    this.unvalidPassword = false;
    this.nonEqualsPasswords = false;
    
  }

  logreg(){
    this.errorMessage = ' ';
    this.unvalidEmail = false;
    this.unvalidPassword = false;
    this.nonEqualsPasswords = false;

    const validMail :boolean = this.logregService.validateMail(this.emailInput);
    const matchPassword : boolean = this.logregService.validatePasswordConfirmation(this.passwordInput, this.passwordConfirmInput);
    const validPassword :string = this.logregService.validatePassword(this.passwordInput);

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

    if(this.isRegister){
      if(!matchPassword){
        this.errorMessage = "Passwords do not match";
        this.nonEqualsPasswords = true;
        this.unvalidPassword = true;
        return;
      }
    }

    if(this.isRegister){  //register

      this.logregService.register(this.emailInput, this.passwordInput).subscribe(response =>{
        this.modalRef.close();
        this.router.navigate(['/registerSuccess']);
      }, error => {
        if(error.error.appStatus == "602"){
          this.unvalidEmail = true;
          this.errorMessage = "Email is already in use";
        } else {
          this.router.navigate(['/error']);
        }
      });

    } else {  //login
      this.logregService.login(this.emailInput, this.passwordInput).subscribe(response => {
        localStorage.setItem('token',response.token);
        this.modalRef.close();
        this.router.navigate(['/home']);
      }, error => {
        var err: logregResponse = {
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
  }
}
