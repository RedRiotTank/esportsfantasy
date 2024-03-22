import { Component } from '@angular/core';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { EsfLofregModalComponent } from '../../common/esf-logreg/esf-lofreg-modal/esf-lofreg-modal.component';

/**
 * This component is responsible for managing the showcase page of the application.
 * It control the login and register modals opening.
 * @author Alberto Plaza Montes.
 */
@Component({
  selector: 'app-esf-showcase',
  templateUrl: './esf-showcase.component.html',
  styleUrl: './esf-showcase.component.scss'
})
export class EsfShowcaseComponent {

  modalRef: MdbModalRef<EsfLofregModalComponent> | null = null;

  constructor(
    private modalService: MdbModalService
    ){}
  
  openLoginModal() {
    this.modalRef = this.modalService.open(EsfLofregModalComponent,{
      modalClass: 'modal-dialog-centered modal-xl'
    });
  }

  openRegisterModal(){
    this.modalRef = this.modalService.open(EsfLofregModalComponent,{
      modalClass: 'modal-dialog-centered modal-xl',
      data: { isRegister: true },
    });
  }
}


