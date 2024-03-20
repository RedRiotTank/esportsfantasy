import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  private mobilepx :number = 768; //if change this, change it in styles.scss too.

  constructor() { }

  isMobile() {
    return window.innerWidth <= this.mobilepx; 
  }

  getMobilepx() :number{
    return this.mobilepx;
  }
}
