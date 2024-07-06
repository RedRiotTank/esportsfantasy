import { Component, OnInit } from '@angular/core';
import { TeamService } from './team.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { SelectplayerModalComponent } from './selectplayer-modal/selectplayer-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { AppapiService } from '../common/API/appapi.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrl: './team.component.scss',
})
export class TeamComponent implements OnInit {
  private league: string;
  public jours: number[] = [];
  public matchs: any = {};
  public selectedJour: number = 1;

  constructor(
    private teamService: TeamService,
    private leaguelistService: LeagueListServiceService,
    private appApiService: AppapiService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.teamService.loadTeam();

    this.appApiService
      .getMatchSchedule(this.leaguelistService.getSelectedRLeagueUUID())
      .subscribe((response) => {
        const keys = Object.keys(response);

        const numericKeys = keys
          .filter((key) => !isNaN(Number(key)))
          .map((key) => Number(key));
        const highestKey = Math.max(...numericKeys);
        this.jours = Array.from({ length: highestKey }, (_, i) => i + 1);

        this.matchs = response;
        console.log(this.matchs);
      });

    this.league = this.leaguelistService.getSelectedLeagueGame();
  }

  onSelectionChange(event: any) {
    this.selectedJour = event.value;
  }

  getTeamPlayers() {
    //console.log(this.teamService.getTeamPlayers());

    return this.teamService.getTeamPlayers();
  }

  getAligned(alignedNumber: number) {
    return this.teamService
      .getTeamPlayers()
      .find((player) => player.aligned == alignedNumber);
  }

  getGameBackground() {
    switch (this.league) {
      case 'LOL':
        return '../../assets/backgrounds/LOL.png';
      case 'CSGO':
        return '../../assets/backgrounds/CSGO.png';
    }

    return '';
  }

  openModal(pos: number): void {
    const dialogRef = this.dialog.open(SelectplayerModalComponent, {
      width: '400px',
      data: {
        pos: pos,
        players: this.teamService.getTeamPlayers(),
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.teamService.loadTeam();
      }
    });
  }
}
