import { Component } from '@angular/core';
import { Router } from '@angular/router';

/**
 * This component is responsible for managing the initial page of the application.
 * It checks if the user is logged in and redirects to the home page if it is.
 * @author Alberto Plaza Montes.
 */
@Component({
  selector: 'app-esf-init',
  templateUrl: './esf-init.component.html',
  styleUrl: './esf-init.component.scss'
})
export class EsfInitComponent {

  constructor(private router: Router
    
    ) {}

    ngOnInit(){

      if(localStorage.getItem('token')){
        this.router.navigate(['/home']);
      }

    }

}
