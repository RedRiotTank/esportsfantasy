import { Injectable } from '@angular/core';

/**
 * This service is responsible for managing the team style of the application.
 * It provides the team style to the components that need it.
 * @author Alberto Plaza Montes.
 */
@Injectable({
  providedIn: 'root'
})
export class EsfTeamStyleServiceService {

  private themes = [
    {
      src: 'x',
      bigButtonColor: 'red',
      smallButtonColor: 'blue'
    },
    {
      src: 'y',
      bigButtonColor: 'black',
      smallButtonColor: 'green'
    }
  ];

  constructor() { }

  /**
   * This method returns a random image from the team style.
   * @returns a random image from the team style.
   */
  getRandomImage() {
    const randomIndex = Math.floor(Math.random() * this.themes.length);
    return this.themes[randomIndex];
  }
}
