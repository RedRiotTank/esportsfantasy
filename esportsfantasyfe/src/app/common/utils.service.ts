import { Injectable } from '@angular/core';

/**
 * This service is responsible for managing the utilities of the application.
 * @author Alberto Plaza Montes.
 */
@Injectable({
  providedIn: 'root',
})
export class UtilsService {
  private mobilepx: number = 768; //if change this, change it in styles.scss too.

  constructor() {}

  /**
   * This method returns true if the screen is mobile, false otherwise.
   * @returns true if the screen is mobile, false otherwise.
   */
  isMobile() {
    return window.innerWidth <= this.mobilepx;
  }

  /**
   * This method returns the number of pixels that the screen has to be considered mobile.
   * @returns the number of pixels that the screen has to be considered mobile.
   */
  getMobilepx(): number {
    return this.mobilepx;
  }

  /**
   * This method checks if the user is logged in.
   * @returns true if the user is logged in, false otherwise.
   */
  isLoggedIn(): boolean {
    // Comprobar si el token JWT está presente en el localStorage
    const token = localStorage.getItem('token');
    return token !== null;
  }

  jourPOParser(jour: number): string {
    if (jour > 99 && jour <= 199) {
      jour -= 100;
      return 'Play-In ' + jour;
    }

    if (jour > 199 && jour <= 299) {
      jour -= 200;
      return 'Play-Off ' + jour;
    }

    if (jour > 299 && jour <= 399) {
      jour -= 300;
      return 'Semis ' + jour;
    }

    if (jour > 399 && jour <= 499) {
      jour -= 400;
      return 'Final ' + jour;
    }

    return 'week ' + jour.toString();
  }
}
