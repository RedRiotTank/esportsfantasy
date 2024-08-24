import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Injectable({
  providedIn: 'root',
})
export class MatchsService {
  public matchs: any = {};
  public jours: number[] = [];

  constructor(
    private appApiService: AppapiService,
    private leaguelistService: LeagueListServiceService
  ) {}

  public loadMatchs() {
    this.matchs = {};
    this.jours = [];

    this.appApiService
      .getMatchSchedule(this.leaguelistService.getSelectedRLeagueUUID())
      .subscribe((response) => {
        const keys = Object.keys(response);

        const numericKeys = keys
          .filter((key) => !isNaN(Number(key)))
          .map((key) => Number(key));
        const highestKey = Math.max(...numericKeys);

        for (const key of numericKeys) {
          response[key].sort(
            (a, b) => new Date(a.date).getTime() - new Date(b.date).getTime()
          );
        }

        this.matchs = response;

        for (const key in response) {
          if (!isNaN(Number(key))) {
            this.jours.push(Number(key));
          }
        }
      });
  }
}
