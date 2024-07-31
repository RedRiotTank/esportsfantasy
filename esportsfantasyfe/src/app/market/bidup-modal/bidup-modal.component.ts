import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MoneyService } from '../../common/money.service';

@Component({
  selector: 'app-bidup-modal',
  templateUrl: './bidup-modal.component.html',
  styleUrls: ['./bidup-modal.component.scss'],
})
export class BidupModalComponent {
  value: number = this.data.price;

  constructor(
    public moneyService: MoneyService,
    public dialogRef: MatDialogRef<BidupModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  closeModal(): void {
    this.dialogRef.close({ number: this.value });
  }
}
