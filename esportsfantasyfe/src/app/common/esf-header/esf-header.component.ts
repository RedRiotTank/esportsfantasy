import { Component } from '@angular/core';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { EsfLofregModalComponent } from '../esf-logreg/esf-lofreg-modal/esf-lofreg-modal.component';


/**
 * This component is responsible for managing the header of the application.
 */
@Component({
  selector: 'app-esf-header',
  templateUrl: './esf-header.component.html',
  styleUrl: './esf-header.component.scss'
})
export class EsfHeaderComponent {
  modalRef: MdbModalRef<EsfLofregModalComponent> | null = null;
  openHam: boolean = false;


  constructor(
    private modalService: MdbModalService
    ){}

    /**
     * This method opens the login modal.
     */
    openLoginModal() {
      this.modalRef = this.modalService.open(EsfLofregModalComponent,{
        modalClass: 'modal-dialog-centered modal-xl'
      })
    }

    /**
     * This method opens and closes the hamburger navigation menu.
     */
    toggleHam(){
      this.openHam = !this.openHam;
    }
}
