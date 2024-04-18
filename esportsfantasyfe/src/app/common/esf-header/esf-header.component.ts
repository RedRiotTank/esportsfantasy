import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';


/**
 * This component is responsible for managing the header of the application.
 */
@Component({
  selector: 'app-esf-header',
  templateUrl: './esf-header.component.html',
  styleUrl: './esf-header.component.scss'
})
export class EsfHeaderComponent {
  openHam: boolean = false;


  constructor(
    private modalService: MdbModalService,
    private router: Router
    ){}

    

    /**
     * This method opens and closes the hamburger navigation menu.
     */
    toggleHam(){
      this.openHam = !this.openHam;
    }

    goToHome(){
      this.router.navigate(['/welcome']);
    }

    goToLogin(){
      this.router.navigate(['/login']);
    }
}
