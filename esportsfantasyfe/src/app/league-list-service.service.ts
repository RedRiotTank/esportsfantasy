import { Injectable } from '@angular/core';
import { AppapiService } from './common/API/appapi.service';
import { CredentialsService } from './credentials/credentials.service';
import { MoneyService } from './common/money.service';

@Injectable({
  providedIn: 'root',
})
export class LeagueListServiceService {
  private leagues: any[] = [];
  private selectedLeague: number = 0;

  constructor(
    private appapiService: AppapiService,
    private credentialsService: CredentialsService,
    private moneyService: MoneyService
  ) {}

  public updateLeagueList() {
    let leagueIndex = 0;

    this.appapiService
      .getUserLeagues(this.credentialsService.getDecodedToken().sub)
      .subscribe((leagues) => {
        leagues.forEach((league) => {
          this.appapiService
            .getLeagueIcon(league.leagueUUID)
            .subscribe((icon) => {
              league.icon = icon;
            });
          league.index = leagueIndex;
          leagueIndex++;
          this.leagues.push(league);

          if (league.index == 0) {
            this.setSelectedLeague(0);
          }
        });
      });

    if (this.leagues.length > 0) {
      this.selectedLeague = 0;
    }
  }

  public getLeagues() {
    return this.leagues;
  }

  public getSelectedLeague() {
    return this.leagues[this.selectedLeague];
  }

  public getSelectedRLeagueUUID() {
    return this.leagues[this.selectedLeague].rLeagueUUID;
  }

  public getSelectedLeagueUUID() {
    //console.log("Getting league UUID: " + this.leagues[this.selectedLeague].leagueUUID);
    console.log(this.leagues[this.selectedLeague]);

    return this.leagues[this.selectedLeague].leagueUUID;
  }

  public setSelectedLeague(index: number) {
    if (index < 0 || index >= this.leagues.length) {
      this.selectedLeague = 0;
    } else {
      this.selectedLeague = index;
    }

    this.moneyService.updateMoney(this.getSelectedLeagueUUID());
  }

  public getSelectedLeagueGame() {
    return this.leagues[this.selectedLeague].leagueGame;
  }
}
