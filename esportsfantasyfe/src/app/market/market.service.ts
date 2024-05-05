import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Injectable({
  providedIn: 'root'
})
export class MarketService {

  constructor(private appApiService: AppapiService,
    private leagueListService: LeagueListServiceService
  ) { }

  private marketPlayers: any[] = [];

  public loadMarket(){
    this.marketPlayers = [];

    this.appApiService.getMarketPlayers(this.leagueListService.getSelectedLeague().leagueUUID).subscribe(
      players => {
        console.log(players);
        players.forEach(player => {
          this.appApiService.getPlayerIcon(player.uuid).subscribe(icon => {
            player.icon = icon;
            this.marketPlayers.push(player);
          });
        });  
      }
    );
    

  }

  public getMarketPlayers(){
    return this.marketPlayers;
  }
}
