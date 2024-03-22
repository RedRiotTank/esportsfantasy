import { Injectable } from '@angular/core';

/**
 * This service is responsible for managing the utilities of the application.
 * @author Alberto Plaza Montes.
 */
@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  private mobilepx :number = 768; //if change this, change it in styles.scss too.

  constructor() { }

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
  getMobilepx() :number{
    return this.mobilepx;
  }
}
