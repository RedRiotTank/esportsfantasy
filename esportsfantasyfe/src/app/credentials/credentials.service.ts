import { Injectable } from '@angular/core';
import { credentialsResponse } from './credentialsResponse';
import { Observable } from 'rxjs';
import { ApiService } from '../common/API/api.service';
import { SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';
import { jwtDecode } from 'jwt-decode';
import { AppapiService } from '../common/API/appapi.service';
import { HeaderService } from '../common/esf-header/header.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class CredentialsService {
  social_started: boolean = false;
  social_user: SocialUser;
  loggedIn: boolean = false;

  constructor(
    private socialAuthService: SocialAuthService,
    private headerService: HeaderService,
    private appapiService: AppapiService,
    private api: ApiService,
    private router: Router
  ) {}

  // --- KO MANAGEMENT ---

  public sendToErrorPage(error: any) {
    this.appapiService.sendToErrorPage(error);
    localStorage.removeItem('token');
  }

  // --- LOGIN ---

  /**
   * This method sends a login request to the server.
   * @param mail user mail.
   * @param pass user password.
   * @returns Obeservable to subscribe.
   */
  login(mail: string, pass: string): Observable<credentialsResponse> {
    const data = {
      mail: mail,
      pass: pass,
    };

    return this.api.sendRequest('user/login', data);
  }

  loginWithToken(token: string): Observable<credentialsResponse> {
    return this.api.sendRequest('user/loginWithToken', token);
  }

  logout() {
    localStorage.removeItem('token');
    this.loggedIn = false;
    this.headerService.setLoggedIn(false);
  }

  public getLoggedIn() {
    return this.loggedIn;
  }

  updateLoginCredentials() {
    const decodedToken = this.getDecodedToken();

    //console.log(decodedToken);

    if (decodedToken.sub != null) this.loggedIn = true; // PROVISIONAL CHECKEAR ESTO.

    this.appapiService.getUserPfp(decodedToken.sub).subscribe((response) => {
      this.headerService.setLoggedIn(true);
      this.headerService.setPfpUrl(response);
    });
  }

  // ---- SOCIAL LOGIN ----

  /**
   * This method initializes the social login.
   */
  initSocial() {
    if (!this.social_started) {
      this.socialAuthService.authState.subscribe((user) => {
        this.social_user = user;
        this.loggedIn = user != null;
        this.loginSocial();
      });
    }

    this.social_started = true;
  }

  /**
   * This method send a login request to the server with the social user.
   */
  loginSocial() {
    const data = {
      mail: this.social_user.email,
      idToken: this.social_user.idToken,
      name: this.social_user.name,
      iconLink: this.social_user.photoUrl,
    };

    this.api.sendRequest('user/googleLogin', data).subscribe(
      (response) => {
        localStorage.setItem('token', response.token);

        this.updateLoginCredentials();
        this.router.navigate(['/home']);
      },
      (error) => {}
    );
  }

  // --- REGISTER ---

  /**
   * This method sends a register request to the server.
   * @param mail mail of the user.
   * @param pass password of the user.
   * @returns observable to subscribe.
   */
  register(mail: string, pass: string): Observable<credentialsResponse> {
    const data = {
      mail: mail,
      pass: pass,
    };

    return this.api.sendRequest('user/signup', data);
  }

  // --- VALIDATIONS ---

  /**
   * This method sends a logout request to the server.
   * @returns true if the mail is valid, false otherwise.
   */
  validateMail(email: string): boolean {
    const emailRegex: RegExp =
      /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  /**
   * This method validates the password.
   * @param password the password to validate.
   * @returns null if the password is correct, a string with the error otherwise.
   */
  validatePassword(password: string): string | null {
    if (!/[A-Z]/.test(password)) {
      return 'Password must contain at least one uppercase letter.';
    }

    if (!/[a-z]/.test(password)) {
      return 'Password must contain at least one lowercase letter.';
    }

    if (!/\d/.test(password)) {
      return 'Password must contain at least one digit.';
    }

    if (password.length < 8) {
      return 'Password must be at least 8 characters long.';
    }

    return null;
  }

  /**
   * This method
   * @param pass
   * @param passConfirm
   * @returns true if the password was correct, false otherwise.
   */
  validatePasswordConfirmation(pass: string, passConfirm: string) {
    return pass == passConfirm;
  }

  // ----- JWT -----

  getDecodedToken() {
    const token = localStorage.getItem('token');

    return jwtDecode(token);
  }

  getUserUUID() {
    return this.getDecodedToken().jti;
  }
}
