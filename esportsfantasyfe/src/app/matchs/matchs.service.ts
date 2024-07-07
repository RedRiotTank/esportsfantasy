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
    this.appApiService
      .getMatchSchedule(this.leaguelistService.getSelectedRLeagueUUID())
      .subscribe((response) => {
        const keys = Object.keys(response);

        const numericKeys = keys
          .filter((key) => !isNaN(Number(key)))
          .map((key) => Number(key));
        const highestKey = Math.max(...numericKeys);
        this.jours = Array.from({ length: highestKey }, (_, i) => i + 1);

        // Ordenar los partidos por fecha
        for (const key of numericKeys) {
          response[key].sort(
            (a, b) => new Date(a.date).getTime() - new Date(b.date).getTime()
          );
        }

        this.matchs = response;
        console.log(this.matchs);
      });
  }
}
