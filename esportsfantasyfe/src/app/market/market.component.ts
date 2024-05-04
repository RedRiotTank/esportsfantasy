import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styleUrl: './market.component.scss'
})
export class MarketComponent implements OnInit{

  marketPlayers: any[] = [];

  constructor(private appApiService: AppapiService,
    private leagueListService: LeagueListServiceService
  ) { }

  ngOnInit(): void {
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

  log(){
    console.log(this.marketPlayers);
  }



}
