import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-bidup-modal',
  templateUrl: './bidup-modal.component.html',
  styleUrl: './bidup-modal.component.scss'
})
export class BidupModalComponent {

  constructor(
    public dialogRef: MatDialogRef<BidupModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ){}

  closeModal(): void {
    this.dialogRef.close();
  }
}
