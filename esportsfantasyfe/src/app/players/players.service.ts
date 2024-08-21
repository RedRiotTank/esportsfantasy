import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { tap } from 'rxjs';

interface Filter {
  icon: string;
  selected: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class PlayersService {
  public playerList: any[] = [];
  public teamicons: { [key: string]: string } = {};

  public posFilter: { [key: string]: Filter } = {};
  public teamFilter: { [key: string]: Filter } = {};

  constructor(
    public appApiService: AppapiService,
    public leagueListService: LeagueListServiceService
  ) {}

  loadPlayers() {
    return this.appApiService
      .getAllPlayers(this.leagueListService.getSelectedLeagueUUID())
      .pipe(
        tap((data) => {
          this.playerList = data.players;

          for (const player of this.playerList) {
            player.teamList.forEach((team: any) => {
              if (!(team in this.teamicons)) {
                this.teamicons[team] = 'working';
                this.appApiService.getTeamIcon(team).subscribe((i: any) => {
                  this.teamFilter[team] = {
                    icon: i,
                    selected: false,
                  };

                  this.teamicons[team] = i;
                });
              }

              if (!(player.role in this.posFilter)) {
                this.posFilter[player.role] = {
                  icon: player.role,
                  selected: false,
                };
              }
            });
          }
        })
      );
  }
}
