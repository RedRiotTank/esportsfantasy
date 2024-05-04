import { Injectable } from '@angular/core';
import { AppapiService } from './common/API/appapi.service';
import { CredentialsService } from './credentials/credentials.service';

@Injectable({
  providedIn: 'root'
})
export class LeagueListServiceService {

  private leagues: any[] = [];

  constructor(private appapiService: AppapiService,
    private credentialsService: CredentialsService
  ) { }

  public updateLeagueList(){
    this.appapiService.getUserLeagues(this.credentialsService.getDecodedToken().sub).subscribe( leagues => {
      leagues.forEach(league => {
        this.appapiService.getLeagueIcon(league.leagueUUID).subscribe(icon => {
          league.icon = icon;
        });
        this.leagues.push(league);
      });
    });
  }

  public getLeagues(){
    return this.leagues;
  }

}
