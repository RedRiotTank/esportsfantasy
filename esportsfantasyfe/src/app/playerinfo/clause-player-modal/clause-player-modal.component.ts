import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AppapiService } from '../../common/API/appapi.service';
import { LeagueListServiceService } from '../../league-list-service.service';
import { CredentialsService } from '../../credentials/credentials.service';
import { TeamService } from '../../team/team.service';

@Component({
  selector: 'app-clause-player-modal',
  templateUrl: './clause-player-modal.component.html',
  styleUrl: './clause-player-modal.component.scss',
})
export class ClausePlayerModalComponent {
  public selectedClauseIncrease: number = 0;

  constructor(
    public dialogRef: MatDialogRef<ClausePlayerModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private appApiService: AppapiService,
    private leagueListService: LeagueListServiceService,
    private credentialsService: CredentialsService,
    private teamService: TeamService
  ) {}

  closeModal(): void {
    this.dialogRef.close({});
  }

  clausePlayer(): void {
    this.appApiService
      .clausePlayer(
        this.data.playerUUID,
        this.leagueListService.getSelectedLeagueUUID(),
        this.data.ownerUUID,
        this.credentialsService.getUserUUID(),
        this.teamService.currentJour,
        this.data.clause
      )
      .subscribe((res) => {
        this.closeModal();
      });
  }

  calculatePlayerClauseIncrease(): number {
    switch (this.selectedClauseIncrease) {
      case 1: {
        return this.data.clause + (1 / 4) * this.data.clause;
      }
      case 2: {
        return this.data.clause + (1 / 3) * this.data.clause;
      }
      case 3: {
        return this.data.clause + (1 / 2) * this.data.clause;
      }

      case 4: {
        return this.data.clause * 2;
      }
    }
    return this.data.clause;
  }

  calculatePlayerClauseIncreaseCost(): number {
    switch (this.selectedClauseIncrease) {
      case 1: {
        return (1 / 4) * this.data.clause * (5 / 6);
      }
      case 2: {
        return (1 / 3) * this.data.clause * (4 / 6);
      }
      case 3: {
        return (1 / 2) * this.data.clause * (3 / 6);
      }
      case 4: {
        return this.data.clause * (2 / 6);
      }
    }
    return 0;
  }

  increaseClause(): void {
    this.appApiService
      .increaseClause(
        this.data.playerUUID,
        this.leagueListService.getSelectedLeagueUUID(),
        this.credentialsService.getUserUUID(),
        this.selectedClauseIncrease
      )
      .subscribe((res) => {
        this.closeModal();
      });
  }
}
