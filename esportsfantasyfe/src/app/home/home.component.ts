import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  //comprobar ligas del jugador.


  constructor(private router: Router) { }

  goToCreateLeague(){
    this.router.navigate(['/joinLeague']);
  }

  

}
