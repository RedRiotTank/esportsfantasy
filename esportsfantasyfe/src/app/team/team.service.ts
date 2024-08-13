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
  public editableCurrentJour: boolean;
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
        this.teamPlayers = response.players;

        this.teamPlayers = this.teamPlayers.map((player) => {
          const iconData = response.playerIcons.find(
            (iconObj) => iconObj.uuid === player.uuid
          );

          const teamIconData = response.teamIcons.find(
            (teamIconObj) => teamIconObj.uuid === player.teamUuid
          );

          return {
            ...player,
            ...(iconData && { icon: iconData.icon }),
            ...(teamIconData && { teamIcon: teamIconData.icon }),
          };
        });
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
          this.currentJour = response.currentJour;
          this.editableCurrentJour = response.isEditable;
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
