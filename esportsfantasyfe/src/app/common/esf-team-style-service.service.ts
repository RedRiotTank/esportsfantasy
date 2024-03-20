import { Injectable } from '@angular/core';

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

  getRandomImage() {
    const randomIndex = Math.floor(Math.random() * this.themes.length);
    return this.themes[randomIndex];
  }
}
