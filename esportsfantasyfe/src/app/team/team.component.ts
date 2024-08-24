import { Component, OnInit } from '@angular/core';
import { TeamService } from './team.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { SelectplayerModalComponent } from './selectplayer-modal/selectplayer-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { AppapiService } from '../common/API/appapi.service';
import { SocialAuthService } from '@abacritt/angularx-social-login';
import { UtilsService } from '../common/utils.service';

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
    public dialog: MatDialog,
    public utilsService: UtilsService
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
      this.teamService.selectedJour === this.teamService.currentJour &&
      this.getEditableCurrentJour()
    ) {
      let roleMap: { [key: number]: string } = {};

      switch (this.leaguelistService.getSelectedLeagueGame()) {
        case 'LOL':
          roleMap = {
            1: 'top',
            2: 'jungle',
            3: 'mid',
            4: 'bot',
            5: 'support',
          };
          break;
        case 'CSGO':
          //...
          break;
      }

      const role = roleMap[pos];

      const filteredPlayers = this.getBenchTeamPlayers().filter(
        (player) => player.role.toLowerCase() === role
      );

      const dialogRef = this.dialog.open(SelectplayerModalComponent, {
        width: '450px',
        data: {
          pos: pos,
          players: filteredPlayers,
          selectedPlayer: this.getPlayerByPosition(pos),
          empty: filteredPlayers.length === 0 ? true : false,
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
