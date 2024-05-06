import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { CredentialsService } from '../credentials/credentials.service';

@Injectable({
  providedIn: 'root'
})
export class MarketService {

  constructor(private appApiService: AppapiService,
    private leagueListService: LeagueListServiceService,
    private credentialsService: CredentialsService
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
            
          });

          this.appApiService.getPlayerTeamIcon(player.uuid, this.leagueListService.getSelectedLeague().rLeagueUUID).subscribe(icon => {
            player.teamicon = icon;
            
          });

          this.marketPlayers.push(player);

        });  
      }
    );
    

  }

  public getMarketPlayers(){
    return this.marketPlayers;
  }

  public bidUp(playerUUID: string, value: number){
    return this.appApiService.bidPlayer(playerUUID, this.leagueListService.getSelectedLeagueUUID(), this.credentialsService.getUserUUID(),  value);
  }
}
