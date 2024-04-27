import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OwlOptions } from 'ngx-owl-carousel-o';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.scss'
})
export class WelcomeComponent {


  customOptions: OwlOptions = {
    items: 1,
    loop: true,
    autoplay: true,
    autoplayTimeout: 5000,
    autoplaySpeed : 1000,
    mouseDrag: true,
    touchDrag: true,
    pullDrag: true,
    dots: false,
    navSpeed: 700,
    navText: ['', ''],
    
    nav: false
  }




  
  constructor
  (
    private router: Router
  ) {}
  goToLogin(){
    this.router.navigate(['/login']);
  }

  goToRegister(){
    this.router.navigate(['/register']);
  }

}
