import { Injectable } from '@angular/core';
import { AppapiService } from './common/API/appapi.service';
import { CredentialsService } from './credentials/credentials.service';

@Injectable({
  providedIn: 'root'
})
export class LeagueListServiceService {

  private leagues: any[] = [];
  private selectedLeague: number = 0;

  constructor(private appapiService: AppapiService,
    private credentialsService: CredentialsService
  ) { }

  public updateLeagueList(){    

    let leagueIndex = 0;

    this.appapiService.getUserLeagues(this.credentialsService.getDecodedToken().sub).subscribe( leagues => {
      leagues.forEach(league => {
        this.appapiService.getLeagueIcon(league.leagueUUID).subscribe(icon => {
          league.icon = icon;
          
        });
        league.index = leagueIndex;
        leagueIndex++;
        this.leagues.push(league);
      });
    });

    if(this.leagues.length > 0){
      this.selectedLeague = 0;
    }

  }

  public getLeagues(){
    return this.leagues;
  }

  public getSelectedLeague(){
    return this.leagues[this.selectedLeague];
  }

  public setSelectedLeague(index: number){

    console.log("Setting league to: " + index);
    

    if(index < 0 || index >= this.leagues.length){
      this.selectedLeague = 0;
    } else {
      this.selectedLeague = index;
    }
  }

}
