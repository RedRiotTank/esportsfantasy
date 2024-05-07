import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { CredentialsService } from '../credentials/credentials.service';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private teamPlayers: any[] = [];

  constructor(private appApiservice: AppapiService,
    private leagueservice: LeagueListServiceService,
    private credentialsService: CredentialsService
  ) { }

  public loadTeam(){
    this.appApiservice.getUserTeamInfo(this.credentialsService.getUserUUID(), this.leagueservice.getSelectedLeagueUUID()).subscribe(response => {
      this.teamPlayers = response;
    });
    
  }

  public getTeamPlayers(){
    return this.teamPlayers;
  }
}
