import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private teamPlayers: any[] = [];

  constructor(private appApiservice: AppapiService) { }

  public loadTeam(){
    
  }

  public getTeamPlayers(){
    return this.teamPlayers;
  }
}
