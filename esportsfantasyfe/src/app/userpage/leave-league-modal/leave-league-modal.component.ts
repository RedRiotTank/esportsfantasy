import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AppapiService } from '../../common/API/appapi.service';
import { CredentialsService } from '../../credentials/credentials.service';

@Component({
  selector: 'app-leave-league-modal',
  templateUrl: './leave-league-modal.component.html',
  styleUrl: './leave-league-modal.component.scss',
})
export class LeaveLeagueModalComponent {
  constructor(
    public dialogRef: MatDialogRef<LeaveLeagueModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private appApiService: AppapiService,
    private credentialsService: CredentialsService
  ) {}

  closeModal(): void {
    this.dialogRef.close({});
  }

  leaveLeague(): void {
    console.log(this.data);
    this.appApiService
      .leaveLeague(
        this.data.league.leagueUUID,
        this.credentialsService.getUserUUID()
      )
      .subscribe((res) => {
        this.closeModal();
      });
  }
}
