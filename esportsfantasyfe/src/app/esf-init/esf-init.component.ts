import { Component } from '@angular/core';
import { Router } from '@angular/router';

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
