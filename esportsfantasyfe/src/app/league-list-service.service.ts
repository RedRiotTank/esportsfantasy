import { Injectable } from '@angular/core';
import { AppapiService } from './common/API/appapi.service';
import { CredentialsService } from './credentials/credentials.service';
import { MoneyService } from './common/money.service';
import {
  catchError,
  forkJoin,
  map,
  mergeMap,
  Observable,
  of,
  switchMap,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LeagueListServiceService {
  private leagues: any[] = [];
  private selectedLeague: number = 0;
  private updating: boolean = false;

  constructor(
    private appapiService: AppapiService,
    private credentialsService: CredentialsService,
    private moneyService: MoneyService
  ) {}

  public updateLeagueList() {
    if (this.updating) return;

    let leagueIndex = 0;
    this.leagues = [];

    this.updating = true;

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
        this.updating = false;
      });

    if (this.leagues.length > 0) {
      this.selectedLeague = 0;
    }
  }

  public updateLeagueListObs(): Observable<any> {
    if (this.updating) return of(null);
    this.updating = true;

    setTimeout(() => {
      this.leagues = [];
    });

    let leagueIndex = 0;
    return this.appapiService
      .getUserLeagues(this.credentialsService.getDecodedToken().sub)
      .pipe(
        switchMap((leagues) => {
          this.updating = false;
          return forkJoin(
            leagues.map((league) => {
              return this.appapiService.getLeagueIcon(league.leagueUUID).pipe(
                map((icon) => {
                  league.icon = icon;
                  league.index = leagueIndex;
                  leagueIndex++;
                  this.leagues.push(league);

                  if (league.index == 0) {
                    this.setSelectedLeague(0);
                  }
                })
              );
            })
          );
        })
      );
  }

  public clearLeagues() {
    this.leagues = [];
  }

  public getLeagues() {
    return this.leagues;
  }

  public getSelectedLeague() {
    return this.leagues[this.selectedLeague];
  }

  public getSelectedLeagueIndex() {
    return this.selectedLeague;
  }

  public getSelectedRLeagueUUID() {
    return this.leagues[this.selectedLeague].rLeagueUUID;
  }

  public getSelectedLeagueUUID() {
    if (this.leagues.length == 0) return null;

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
function from(leagues: any) {
  throw new Error('Function not implemented.');
}
