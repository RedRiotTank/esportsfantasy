import { Component, OnInit } from '@angular/core';
import { TeamService } from './team.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { SelectplayerModalComponent } from './selectplayer-modal/selectplayer-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { AppapiService } from '../common/API/appapi.service';
import { SocialAuthService } from '@abacritt/angularx-social-login';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.scss'],
})
export class TeamComponent implements OnInit {
  constructor(
    private teamService: TeamService,
    private leaguelistService: LeagueListServiceService,
    private appApiService: AppapiService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.teamService.loadCurrentJour().subscribe(() => {
      this.teamService.initialLoad();
    });
  }

  onSelectionChange(event: any) {
    this.teamService.changeJour(event.value);
  }

  getTeamPlayers() {
    return this.teamService
      .getTeamPlayers()
      .filter(
        (player) =>
          player.jour === this.teamService.selectedJour && player.aligned > 0
      );
  }

  getBenchTeamPlayers() {
    const alignedPlayers = this.getTeamPlayers();
    return this.teamService
      .getTeamPlayers()
      .filter(
        (player) =>
          player.jour === this.teamService.selectedJour &&
          !alignedPlayers.includes(player)
      );
  }

  getGameBackground() {
    switch (this.teamService.league) {
      case 'LOL':
        return '../../assets/backgrounds/LOL.png';
      case 'CSGO':
        return '../../assets/backgrounds/CSGO.png';
    }
    return '';
  }

  openModal(pos: number): void {
    if (
      this.teamService.selectedJour == this.teamService.currentJour &&
      this.getEditableCurrentJour()
    ) {
      const dialogRef = this.dialog.open(SelectplayerModalComponent, {
        width: '400px',
        data: {
          pos: pos,
          players: this.getBenchTeamPlayers(),
          selectedPlayer: this.getPlayerByPosition(pos),
        },
      });
    }
  }

  getPlayerByPosition(position: number) {
    return this.teamService
      .getTeamPlayers()
      .find(
        (player) =>
          player.jour === this.teamService.selectedJour &&
          player.aligned === position
      );
  }

  getLeagueTotalJours() {
    return Array.from({ length: this.teamService.totalJours }, (_, i) => i + 1);
  }

  getLeagueCurrentJour() {
    return this.teamService.currentJour;
  }

  getEditableCurrentJour() {
    return this.teamService.editableCurrentJour;
  }

  getLeagueCurrentJourArray() {
    return Array.from(
      { length: this.teamService.currentJour },
      (_, i) => this.teamService.currentJour - i
    );
  }

  getTeamService() {
    return this.teamService;
  }
}
