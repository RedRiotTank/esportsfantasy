import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { CredentialsService } from '../credentials/credentials.service';
import { Observable, map, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TeamService {
  private teamPlayers: any[] = [];
  public totalJours: number = 1;
  public currentJour: number = 1;
  public selectedJour: number = 1;
  public league: string = '';

  constructor(
    private appApiservice: AppapiService,
    private leagueservice: LeagueListServiceService,
    private credentialsService: CredentialsService
  ) {}

  public loadTeam() {
    this.appApiservice
      .getUserTeamInfo(
        this.credentialsService.getUserUUID(),
        this.leagueservice.getSelectedLeagueUUID()
      )
      .subscribe((response) => {
        this.teamPlayers = response;
      });
  }
  public loadTotalJours() {
    this.appApiservice
      .getRLeagueTotalJours(this.leagueservice.getSelectedRLeagueUUID())
      .subscribe((response) => {
        this.totalJours = response;
      });
  }

  public loadCurrentJour(): Observable<any> {
    return this.appApiservice
      .getRLeagueCurrentJour(this.leagueservice.getSelectedRLeagueUUID())
      .pipe(
        tap((response) => {
          this.currentJour = response;
        })
      );
  }

  public getTeamPlayers() {
    return this.teamPlayers;
  }

  public setAlignment(playerUUID: string, alignment: number) {
    this.appApiservice
      .setAlignment(
        this.credentialsService.getUserUUID(),
        this.leagueservice.getSelectedLeagueUUID(),
        playerUUID,
        alignment
      )
      .subscribe((response) => {
        this.loadTeam();
      });
  }

  public changeJour(jour: number) {
    this.selectedJour = jour;
  }

  initialLoad() {
    this.selectedJour = this.currentJour;
    this.loadTeam();
    this.league = this.leagueservice.getSelectedLeagueGame();
  }
}
